/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.act.service;

import com.google.common.collect.Maps;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.ActEntity;
import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.act.entity.Act;
import com.jeeplus.modules.act.utils.ActUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 审批Service
 * @author jeeplus
 * @version 2017-05-16
 */
@Service
@Transactional(readOnly = true)
public abstract class ActService<M extends BaseMapper<T>, T extends ActEntity<T>> extends CrudService {

	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private IdentityService identityService;

	@Autowired
	private ActProcessService actProcessService;

	@Autowired
	private RuntimeService runtimeService;


	/**
	 * 持久层对象
	 */
	@Autowired
	protected M mapper;
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return mapper.get(id);
	}

	/**
	 * 审核新增或编辑
	 * @param tActEntity
	 */
	@Transactional(readOnly = false)
	public void save(T tActEntity) {

		// 申请发起
		if (StringUtils.isBlank(tActEntity.getId())){
			tActEntity.preInsert();
			mapper.insert(tActEntity);
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService.setAuthenticatedUserId(tActEntity.getCurrentUser().getLoginName());

			ProcessDefinition p = actProcessService.getProcessDefinition(tActEntity.getAct().getProcDefId());
			String title = tActEntity.getCurrentUser().getName()+"在"+ DateUtils.getDateTime()+"发起"+p.getName();
			actTaskService.startProcess(p.getKey(), tActEntity.getBusinessTable(), tActEntity.getId(), title);
		}
		// 重新编辑申请
		else{
			tActEntity.preUpdate();
			mapper.update(tActEntity);

			tActEntity.getAct().setComment(("yes".equals(tActEntity.getAct().getFlag())?"[重申] ":"[销毁] ")+ tActEntity.getAct().getComment());
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass", "yes".equals(tActEntity.getAct().getFlag())? true : false);
			actTaskService.complete(tActEntity.getAct().getTaskId(), tActEntity.getAct().getProcInsId(), tActEntity.getAct().getComment(), tActEntity.getContent(), vars);
		}
	}



	/**
	 * 审核新增或编辑
	 * @param act
	 */
	@Transactional(readOnly = false)
	public void startAct(Act act) {
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService.setAuthenticatedUserId(act.getCurrentUser().getLoginName());
			ProcessDefinition p = actProcessService.getProcessDefinition(act.getProcDefId());
			String title = act.getCurrentUser().getName()+"在"+ DateUtils.getDateTime()+"发起"+p.getName();
			actTaskService.startProcess(p.getKey(), act.getBusinessTable(), act.getId(), title);
	}


	/**
	 * 审核新增或编辑
	 * @param act
	 */
	@Transactional(readOnly = false)
	public void completeAct(Act act) {

			act.setComment(("yes".equals(act.getFlag())?"[重申] ":"[销毁] ")+ act.getComment());
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass", "yes".equals(act.getFlag())? true : false);
			actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(), "", vars);
	}


}
