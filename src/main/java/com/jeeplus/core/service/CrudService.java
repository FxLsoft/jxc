/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.core.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.core.persistence.Page;

/**
 * Service基类
 * @author jeeplus
 * @version 2017-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<M extends BaseMapper<T>, T extends DataEntity<T>> extends BaseService {
	
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
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		return mapper.get(entity);
	}
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		dataRuleFilter(entity);
		return mapper.findList(entity);
	}


	/**
	 * 查询所有列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findAllList(T entity) {
		dataRuleFilter(entity);
		return mapper.findAllList(entity);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(Page<T> page, T entity) {
		dataRuleFilter(entity);
		entity.setPage(page);
		page.setList(mapper.findList(entity));
		return page;
	}

	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			mapper.insert(entity);
		}else{
			entity.preUpdate();
			 mapper.update(entity);
		}
	}


	/**
	 * 删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		mapper.delete(entity);
	}
	
	
	/**
	 * 删除全部数据
	 * @param entitys
	 */
	@Transactional(readOnly = false)
	public void deleteAll(Collection<T> entitys) {
		for (T entity : entitys) {
			mapper.delete(entity);
		}
	}

	/**
	 * 删除全部数据
	 * @param entitys
	 */
	@Transactional(readOnly = false)
	public void deleteAllByLogic(Collection<T> entitys) {
		for (T entity : entitys) {
			mapper.deleteByLogic(entity);
		}
	}

	
	/**
	 * 获取单条数据
	 * @param propertyName, value
	 * @return
	 */
	public T findUniqueByProperty(String propertyName, Object value){
		return mapper.findUniqueByProperty(propertyName, value);
	}

	/**
	 * 动态sql
	 * @param sql
	 */

	public List<Map<String, Object>>  executeSelectSql(String sql){
		return mapper.execSelectSql(sql);
	}

	@Transactional(readOnly = false)
	public void executeInsertSql(String sql){
		mapper.execInsertSql(sql);
	}

	@Transactional(readOnly = false)
	public void executeUpdateSql(String sql){
		mapper.execUpdateSql(sql);
	}

	@Transactional(readOnly = false)
	public void executeDeleteSql(String sql){
		mapper.execDeleteSql(sql);
	}

}
