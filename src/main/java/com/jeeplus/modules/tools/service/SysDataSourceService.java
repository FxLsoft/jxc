/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tools.service;

import java.util.List;

import com.jeeplus.modules.tools.entity.DbTable;
import com.jeeplus.modules.tools.entity.DbTableColumn;
import com.jeeplus.modules.tools.utils.MultiDBUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.tools.entity.SysDataSource;
import com.jeeplus.modules.tools.mapper.SysDataSourceMapper;

/**
 * 多数据源Service
 * @author liugf
 * @version 2017-07-27
 */
@Service
@Transactional(readOnly = true)
public class SysDataSourceService extends CrudService<SysDataSourceMapper, SysDataSource> {

	public SysDataSource get(String id) {
		return super.get(id);
	}

	public List<SysDataSource> findList(SysDataSource sysDataSource) {
		return super.findList(sysDataSource);
	}

	public Page<SysDataSource> findPage(Page<SysDataSource> page, SysDataSource sysDataSource) {
		return super.findPage(page, sysDataSource);
	}

	@Transactional(readOnly = false)
	public void save(SysDataSource sysDataSource) {
		super.save(sysDataSource);
	}

	@Transactional(readOnly = false)
	public void delete(SysDataSource sysDataSource) {
		super.delete(sysDataSource);
	}


	/**
	 * 查询表列表
	 * @return
	 */
	public List<DbTable> findTableList(SysDataSource sysDataSource) {
		if (sysDataSource.getDbDriver().equals("oracle.jdbc.driver.OracleDriver")) {
			return MultiDBUtils.init(sysDataSource).queryList("SELECT\n" +
					"\t\t\tt.TABLE_NAME AS name,\n" +
					"\t\t\tc.COMMENTS AS comments\n" +
					"\t\t\tFROM user_tables t, user_tab_comments c\n" +
					"\t\t\tWHERE t.table_name = c.table_name\n" +
					"\t\t\tORDER BY t.TABLE_NAME", DbTable.class);
		}else if (sysDataSource.getDbDriver().equals("com.mysql.jdbc.Driver")) {

				return  MultiDBUtils.init(sysDataSource).queryList("SELECT t.table_name AS name,t.TABLE_COMMENT AS comments\n" +
						"\t\t\tFROM information_schema.`TABLES` t\n" +
						"\t\t\tWHERE t.TABLE_SCHEMA = (select database())\n" +
						"\t\t\tORDER BY t.TABLE_NAME", DbTable.class);
		}else if (sysDataSource.getDbDriver().equals("org.postgresql.Driver")){
			return  MultiDBUtils.init(sysDataSource).queryList("SELECT   tablename as name,obj_description(relfilenode,'pg_class') as comments FROM   pg_tables  a, pg_class b  \n" +
					"WHERE     \n" +
					"a.tablename = b.relname  \n" +
					"and a.tablename   NOT   LIKE   'pg%'    \n" +
					"AND a.tablename NOT LIKE 'sql_%'  \n" +
					"ORDER   BY   a.tablename;  ", DbTable.class);
		}else if (sysDataSource.getDbDriver().equals("net.sourceforge.jtds.jdbc.Driver")){
			return  MultiDBUtils.init(sysDataSource).queryList("select   sysobjects.name as name ,sys.extended_properties.value as  comments   from   sysobjects\n" +
					"\t\t\tleft join sys.extended_properties on sysobjects.id=sys.extended_properties.major_id\n" +
					"\t\t\twhere   type= 'U '\n" +
					"\t\t\tand\t\t(sys.extended_properties.minor_id = '0' or sys.extended_properties.minor_id is null)\n" +
					"\t\t\torder by name", DbTable.class);
		}

		return null;

	}

	public List<DbTableColumn> findTableColumnList(SysDataSource sysDataSource, String tableName){
		if (sysDataSource.getDbDriver().equals("oracle.jdbc.driver.OracleDriver")) {
			return MultiDBUtils.init(sysDataSource).queryList("\tSELECT\n" +
					"\t\t\tt.COLUMN_NAME AS name," +
					"\t\t\t(CASE WHEN t.NULLABLE = 'Y' THEN '1' ELSE '0' END) AS isNull,\n" +
					"\t\t\t(t.COLUMN_ID * 10) AS sort,\n" +
					"\t\t\tc.COMMENTS AS comments,\n" +
					"\t\t\tdecode(t.DATA_TYPE,'DATE',t.DATA_TYPE || '(' || t.DATA_LENGTH || ')',\n" +
					"\t\t\t'VARCHAR2', t.DATA_TYPE || '(' || t.DATA_LENGTH || ')',\n" +
					"\t\t\t'VARCHAR', t.DATA_TYPE || '(' || t.DATA_LENGTH || ')',\n" +
					"\t\t\t'NVARCHAR2', t.DATA_TYPE || '(' || t.DATA_LENGTH/2 || ')',\n" +
					"\t\t\t'CHAR', t.DATA_TYPE || '(' || t.DATA_LENGTH || ')',\n" +
					"\t\t\t'NUMBER',t.DATA_TYPE || (nvl2(t.DATA_PRECISION,nvl2(decode(t.DATA_SCALE,0,null,t.DATA_SCALE),\n" +
					"\t\t\t'(' || t.DATA_PRECISION || ',' || t.DATA_SCALE || ')',\n" +
					"\t\t\t'(' || t.DATA_PRECISION || ')'),'(18)')),t.DATA_TYPE) AS jdbcType\n" +
					"\t\t\tFROM user_tab_columns t, user_col_comments c\n" +
					"\t\t\tWHERE t.TABLE_NAME = c.table_name\n" +
					"\t\t\tAND t.COLUMN_NAME = c.column_name\n" +
					"\t\t\t\tAND t.TABLE_NAME = upper('"+tableName+"')\n" +
					"\t\t\tORDER BY t.COLUMN_ID", DbTableColumn.class);
		}else if (sysDataSource.getDbDriver().equals("com.mysql.jdbc.Driver")) {

			return  MultiDBUtils.init(sysDataSource).queryList("SELECT t.COLUMN_NAME AS name, (CASE WHEN t.IS_NULLABLE = 'YES' THEN '1' ELSE '0' END) AS isNull,\n" +
					"\t\t\t(t.ORDINAL_POSITION * 10) AS sort,t.COLUMN_COMMENT AS comments,t.COLUMN_TYPE AS jdbcType\n" +
					"\t\t\tFROM information_schema.`COLUMNS` t\n" +
					"\t\t\tWHERE t.TABLE_SCHEMA = (select database())\n" +
					"\t\t\t\tAND t.TABLE_NAME = upper('"+tableName+"')\n" +
					"\t\t\tORDER BY t.ORDINAL_POSITION", DbTableColumn.class);
		}else if (sysDataSource.getDbDriver().equals("org.postgresql.Driver")){

			return  MultiDBUtils.init(sysDataSource).queryList("SELECT a.attname as name, col_description(a.attrelid,a.attnum) as comments,format_type(a.atttypid,a.atttypmod) as jdbcType\n" +
					"FROM pg_class as c,pg_attribute as a where c.relname = '"+tableName+"' and a.attrelid = c.oid and a.attnum>0", DbTableColumn.class);
		}else if (sysDataSource.getDbDriver().equals("net.sourceforge.jtds.jdbc.Driver")){

			return  MultiDBUtils.init(sysDataSource).queryList("SELECT\n" +
					"\t\t\tsort   = a.colorder*10,\n" +
					"\t\t\tname     = a.name,\n" +
					"\t\t\tjdbcType       = b.name,\n" +
					"\t\t\tisNull     = case when a.isnullable=1 then '1'else '0' end,\n" +
					"\t\t\tcomments   = isnull(g.[value],'')\n" +
					"\t\t\tFROM\n" +
					"\t\t\tsyscolumns a\n" +
					"\t\t\tleft join\n" +
					"\t\t\tsystypes b\n" +
					"\t\t\ton\n" +
					"\t\t\ta.xusertype=b.xusertype\n" +
					"\t\t\tinner join\n" +
					"\t\t\tsysobjects d\n" +
					"\t\t\ton\n" +
					"\t\t\ta.id=d.id  and d.xtype='U' and  d.name != 'dtproperties'\n" +
					"\t\t\tleft join\n" +
					"\t\t\tsyscomments e\n" +
					"\t\t\ton\n" +
					"\t\t\ta.cdefault=e.id\n" +
					"\t\t\tleft join\n" +
					"\t\t\tsys.extended_properties   g\n" +
					"\t\t\ton\n" +
					"\t\t\ta.id=G.major_id and a.colid=g.minor_id\n" +
					"\t\t\tleft join\n" +
					"\t\t\tsys.extended_properties f\n" +
					"\t\t\ton\n" +
					"\t\t\td.id=f.major_id and f.minor_id=0\n" +
					"\t\t\twhere\n" +
					"\t\t\td.name= upper(+'"+tableName+"')    --如果只查询指定表,加上此红色where条件，tablename是要查询的表名；去除红色where条件查询说有的表信息\n" +
					"\t\t\torder by\n" +
					"\t\t\ta.id,a.colorder", DbTableColumn.class);
		}
		return null;
	}


}