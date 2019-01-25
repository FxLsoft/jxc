/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import java.util.List;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 数据字典Entity
 * @author lgf
 * @version 2017-01-16
 */
public class DictType extends DataEntity<DictType> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型
	private String description;		// 描述
	private List<DictValue> dictValueList = Lists.newArrayList();		// 子表列表
	
	public DictType() {
		super();
	}

	public DictType(String id){
		super(id);
	}

	@ExcelField(title="类型", align=2, sort=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="描述", align=2, sort=2)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<DictValue> getDictValueList() {
		return dictValueList;
	}

	public void setDictValueList(List<DictValue> dictValueList) {
		this.dictValueList = dictValueList;
	}
}