package com.myron.db.jdbc.template;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myron.db.jdbc.bean.Column;
import com.myron.db.jdbc.callback.ResultSetCallback;
import com.myron.db.jdbc.factory.SqlSessionFactory;


/**
 * jdbc操作模板类
 * @author linrx1
 *
 */
public class MyronJdbcTemplate {
	private static Logger logger=LoggerFactory.getLogger(MyronJdbcTemplate.class);
	
	private SqlSessionFactory sessionFactory;
	
	public List<Column> queryColumn(String tableName){

		//查询sql语句
		String sql = "show full columns from ?"; 
		logger.debug("=> excute:{}",sql);
		logger.debug("=> param:{}",tableName);
		sql=sql.replace("?", tableName);
		return this.selectList(sql, new ResultSetCallback<Column>() {
			@Override
			public List<Column> doResultSet(ResultSet rs) throws SQLException {
				//处理结果集
				List<Column> list=new ArrayList<Column>();
				while(rs.next()){
					Column column=new Column();
					String field=rs.getString("FIELD");
					String type=rs.getString("TYPE");
					String collation=rs.getString("COLLATION");
					String isNull=rs.getString("NULL");
					String key=rs.getString("KEY");
					String extra=rs.getString("EXTRA");
					String comment=rs.getString("COMMENT");
					
					column.setField(field);
					column.setType(type);
					column.setCollation(collation);
					column.setIsNull(isNull);
					column.setKey(key);
					column.setExtra(extra);
					column.setComment(comment);
					System.out.println(column);
					list.add(column);
				}
				logger.debug("<= result:{}",list);
				return list;
			}
			
		});

	}

	/**
	 * 构建测试数据
	 * @param tableName
	 * @param list
	 * @param id
	 */
	public void buildTestDataById(String tableName, final List<Column> list, Object id) {
		String key = this.getPrimaryKey(list);
		//查询sql语句
		String sql = "select * from ? where " + key + "=" + id;
		logger.debug("=> excute:{}",sql);
		logger.debug("=> param:{}",tableName);
		sql=sql.replace("?", tableName);
		this.selectList(sql, new ResultSetCallback<Column>() {

			@Override
			public List<Column> doResultSet(ResultSet rs) throws SQLException {
				if(!rs.next()) {
					return null;
				}
				//处理结果集
				for (Column column: list) {
					Object rawValue = rs.getObject(column.getField());
					if (!"String".equals(column.getJavaType()) && "null".equalsIgnoreCase(String.valueOf(rawValue))){
						logger.info("空值");
						//rawValue = "";
					}
					System.out.println(column.getField() + "value:" + rawValue);
					column.setValue(rawValue);
				}
				return null;
			}
		});

	}

	/**
	 *  查询表的字段列表
	 * @param sql
	 * @param callback
	 * @param <T>
	 * @return
	 */
	public <T> List<T> selectList(String sql, ResultSetCallback<T> callback) {
		PreparedStatement ps;
		List<T> list = null;
		try {
			ps = (PreparedStatement) sessionFactory.getConnection().prepareStatement(sql);
			ResultSet rs=ps.executeQuery();	
			list = callback.doResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			sessionFactory.close();
		}
		return list;
	}

	/**
	 * TODO 获取表字段主键 确定是否要重整
	 * @param list
	 * @return
	 */
	private String getPrimaryKey(List<Column> list) {
		String key = "";
		for (Column column : list) {
			if ("PRI".equalsIgnoreCase(column.getKey())) {
				key = column.getField();
				break;
			}
		}
		return key;
	}

	public SqlSessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
}
