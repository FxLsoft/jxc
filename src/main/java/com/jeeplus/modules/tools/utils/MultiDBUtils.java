package com.jeeplus.modules.tools.utils;

import java.util.List;
import java.util.Map;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.tools.entity.SysDataSource;
import com.jeeplus.modules.tools.service.SysDataSourceService;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/*
  多数据源工具类
  Author:jeeplus
 */

public class MultiDBUtils {

	private JdbcTemplate jdbcTemplate;

	private static SysDataSourceService sysDataSourceService = SpringContextHolder.getBean(SysDataSourceService.class);

	private MultiDBUtils() {

	}

	/**
	 * 通过SysDataSource bean类初始化工具类
	 * @param dataSource
	 * @return
	 */
	public static MultiDBUtils init(SysDataSource dataSource) {
		MultiDBUtils multiDbUtis = new MultiDBUtils();
		if (dataSource == null) {
			return null;
		}
		multiDbUtis.setJdbcTemplate(parseDataSource(dataSource));
		return multiDbUtis;
	}

	/**
	 * 通过dbName初始化工具类
	 * @param enname
	 * @return
	 */
	public static MultiDBUtils get(String enname) {
		MultiDBUtils multiDbUtis = new MultiDBUtils();
		SysDataSource dataSource = sysDataSourceService.findUniqueByProperty("enname", enname);
		if (dataSource == null) {
			return null;
		}
		multiDbUtis.setJdbcTemplate(parseDataSource(dataSource));
		return multiDbUtis;
	}

	/**
	 * 将数据库中的存储的dataSource对象转换成BasicDataSource
	 * @param sysDataSource
	 * @return
	 */
	private static BasicDataSource parseDataSource(SysDataSource sysDataSource) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(sysDataSource.getDbDriver());
		dataSource.setUrl(StringEscapeUtils.unescapeHtml4(sysDataSource.getDbUrl()));
		dataSource.setUsername(sysDataSource.getDbUserName());
		dataSource.setPassword(sysDataSource.getDbPassword());
		return dataSource;
	}


	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(BasicDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Map<String, Object>> queryList(String sql, Object... param) {
		List<Map<String, Object>> list;
		if (ArrayUtils.isEmpty(param)) {
			list = jdbcTemplate.queryForList(sql);
		} else {
			list = jdbcTemplate.queryForList(sql, param);
		}
		return list;
	}

	public <T> List<T> queryList(String sql, Class<T> clazz, Object... param) {
		List<T> list;

		if (ArrayUtils.isEmpty(param)) {
			list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<T>(clazz));
		} else {
			list = jdbcTemplate.query(sql.toString(), new Object[] {param}, new BeanPropertyRowMapper<T>(clazz));
		}
		return list;
	}

	public int update(String sql, Object... param){
		if (ArrayUtils.isEmpty(param)) {
			return jdbcTemplate.update(sql);
		} else {
			return jdbcTemplate.update(sql, param);
		}
	}

}
