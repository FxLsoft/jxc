package com.jeeplus.modules.iim.entity;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;

/**
 * 联系人
 * @author Swhite
 *
 */
public class Friend {
	
	private String id;//用户ID
	
	private String username;//用户名
	
	private String avatar; //头像
	
	private String sign; //签名
	
	private String remark;//备注
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		if(StringUtils.isBlank(avatar)){
			return SpringContextHolder.getStatic()+"/common/images/flat-avatar.png";
		}
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
