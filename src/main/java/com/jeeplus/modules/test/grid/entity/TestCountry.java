/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.grid.entity;

import com.jeeplus.modules.test.grid.entity.TestContinent;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 国家Entity
 * @author lgf
 * @version 2018-04-10
 */
public class TestCountry extends DataEntity<TestCountry> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 国名
	private String sum;		// 人口
	private TestContinent continent;		// 所属洲
	
	public TestCountry() {
		super();
	}

	public TestCountry(String id){
		super(id);
	}

	@ExcelField(title="国名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="人口", align=2, sort=2)
	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}
	
	@ExcelField(title="所属洲", fieldType=TestContinent.class, value="continent.name", align=2, sort=3)
	public TestContinent getContinent() {
		return continent;
	}

	public void setContinent(TestContinent continent) {
		this.continent = continent;
	}
	
}