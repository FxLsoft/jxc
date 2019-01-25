/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 区域Entity
 * @author jeeplus
 * @version 2016-05-15
 */
public class Area extends TreeEntity<Area> {

	private static final long serialVersionUID = 1L;
	private String code; 	// 区域编码
	private String type; 	// 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）
	
	public Area(){
		super();
		this.sort = 30;
	}

	public Area(String id){
		super(id);
	}
	
//	@JsonBackReference
//	@NotNull
	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

	@Length(min=1, max=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min=0, max=100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	@Override
	public String toString() {
		return name;
	}
}