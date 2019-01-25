package com.jeeplus.common.websocket.service.onchat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.websocket.utils.Constants;
import com.jeeplus.modules.iim.entity.ChatHistory;
import com.jeeplus.modules.iim.entity.LayGroupUser;
import com.jeeplus.modules.iim.service.ChatHistoryService;
import com.jeeplus.modules.iim.service.LayGroupService;
import com.jeeplus.modules.sys.utils.UserUtils;

public class LayIMSocketHandler implements WebSocketHandler {

    private static final Logger logger;

    private static final ArrayList<WebSocketSession> users;

    static {
        users = new ArrayList<>();
        logger = LoggerFactory.getLogger(LayIMSocketHandler.class);
    }
    
    public ArrayList<String> getOnlineLoginNames(){
    	 ArrayList<String> onlineLoginNames = new ArrayList<String>();
    	 for(WebSocketSession user : users){
    		 String userName = (String) user.getAttributes().get(Constants.WEBSOCKET_LOGINNAME);
    		 if(userName!= null){
    			 onlineLoginNames.add(userName);
    		 }
    	 }
    	 return onlineLoginNames;
         
    }

    @Autowired
    private ChatHistoryService chatHistoryService;
    @Autowired
    private LayGroupService layGroupService;

    //用户上线后触发
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("connect to the websocket success......");
        users.add(session);
        this.updateOnlineStatus();
        String loginName = (String) session.getAttributes().get(Constants.WEBSOCKET_LOGINNAME);//获取刚上线用户的loginName
        if(loginName!= null){
        	//读取离线信息
			ChatHistory chat = new ChatHistory();
			chat.setReceiver(loginName);//发给刚上线的用户信息
			chat.setStatus("0");
			List<ChatHistory> list =chatHistoryService.findList(chat);
			for(ChatHistory c : list){
				
				if("friend".equals(c.getType())){//如果是个人信息
					String sender = c.getSender();
					String msg = sender+Constants._msg_+c.getReceiver()+Constants._msg_+c.getMsg()+Constants._msg_+UserUtils.getByLoginName(sender).getPhoto()+Constants._msg_+c.getType()+Constants._msg_+sender+Constants._msg_+c.getCreateDate().getTime();
					this.sendMessageToUser(loginName, msg);
				}else{//如果是群组信息
					String groupId = c.getSender().split(Constants._msg_)[0];//群组id
					String sender=c.getSender().split(Constants._msg_)[1];//发送者loginName
					String msg = groupId+Constants._msg_+c.getReceiver()+Constants._msg_+c.getMsg()+Constants._msg_+UserUtils.getByLoginName(sender).getPhoto()+Constants._msg_+c.getType()+Constants._msg_+sender+Constants._msg_+c.getCreateDate().getTime();
					this.sendMessageToUser(loginName, msg);
				}
				
				c.setStatus("1");//标记为已读
				chatHistoryService.save(c);
			}
        }
    }

    //接收js侧发送来的用户信息
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> socketMessage) throws Exception {

    	String message = socketMessage.getPayload().toString();
		if(null != message && message.contains(Constants._msg_)){//发送消息
			String []arr = message.split(Constants._msg_);
			String sender = arr[0];//信息发送者登录名(loginName)
			String receiver = arr[1];//信息接收者，如果是私聊就是用户loginName，如果是群聊就是群组id
			String msg = arr[2];
			String avatar=arr[3];
			String type=arr[4];
			String senderName= arr[5];//发送者姓名(name)
			String datatime = arr[6];
			
			
			//保存聊天记录

			
			if("group".equals(type)){//如果是群聊
				
				List<LayGroupUser> layGroupUserlist = new ArrayList();
				//群主
				LayGroupUser owner = new LayGroupUser();
				owner.setUser( layGroupService.get(receiver).getCreateBy());
				layGroupUserlist.add(owner);
				//群成员
				List<LayGroupUser> zlist = layGroupService.getUsersByGroup(layGroupService.get(receiver));
				layGroupUserlist.addAll(zlist);
				
				for(LayGroupUser lgUser:layGroupUserlist){

					ChatHistory chat = new ChatHistory();
					//群聊时信息先发送给群聊id（即receiver)，在后台转发给所有非发送者(sender)的人的话，群聊id（即receiver)就变成发送者。
					String groupId = receiver;
					//保存聊天记录
					chat.setSender(groupId+Constants._msg_+sender);//群聊时保存群聊id和发送者id
					chat.setReceiver(lgUser.getUser().getLoginName());//群中所有信息获得者人员
					chat.setMsg(msg);
					chat.setCreateDate(new Date());
					chat.setType("group");  
					message = groupId+Constants._msg_+lgUser.getUser().getLoginName()+Constants._msg_+msg+Constants._msg_+avatar+Constants._msg_+type+Constants._msg_+senderName+Constants._msg_+datatime;
					if(sender.equals(lgUser.getUser().getLoginName())){//群消息不发给自己，但是存一条记录当做聊天记录查询
							chat.setStatus("1");//发送成功，设置为已读
					}else{
						boolean isSuccess = this.sendMessageToUser(lgUser.getUser().getLoginName(), message);
						if(isSuccess){
							chat.setStatus("1");//发送成功，设置为已读
						}else{
							chat.setStatus("0");//用户不在线，设置为未读
						}
					}

					chatHistoryService.save(chat);
				}
				
			}else{//如果是私聊
				ChatHistory chat = new ChatHistory();
				chat.setSender(sender);
				chat.setReceiver(receiver);
				chat.setMsg(msg);
				chat.setType("friend");
				chat.setCreateDate(new Date());
				boolean isSuccess = this.sendMessageToUser(receiver, message);
				if(isSuccess){//如果信息发送成功
					chat.setStatus("1");//设置为已读
				}else{
					this.sendMessageToUser(sender, receiver+"_sys_对方现在离线，他将在上线后收到你的消息!");//同时向本人发送对方不在线消息
					chat.setStatus("0");//设置为未读
				}
				chatHistoryService.save(chat);
			}
			

			
		}
	
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        logger.debug("websocket connection closed......");
        users.remove(session);
        this.updateOnlineStatus();
	
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        users.remove(session);
        this.updateOnlineStatus();
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToAllUsers( String message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param loginName
     * @param message
     */
    public boolean sendMessageToUser(String loginName, String message) {
    	boolean result = false;
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(Constants.WEBSOCKET_LOGINNAME).equals(loginName)) {//允许用户多个浏览器登录，每个浏览器页面都会收到用户信息
                try {
                    if (user.isOpen()) {
                        user.sendMessage(new TextMessage(message));
                        result = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                //break;//注释掉此处意味着遍历该用户打开的所有页面并发送信息，否则只会向用户登录的第一个网页发送信息。
            }
        }
        return result;
    }
    
    public void updateOnlineStatus(){
    	AjaxJson j = new AjaxJson();
		j.put("data", this.getOnlineLoginNames());
		this.sendMessageToAllUsers("_online_all_status_"+j.getJsonStr());//通知所有用户更新在线信息
    }
}