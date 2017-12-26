package com.myron.db.jdbc.callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * jdbc 操作回调操作
 * @author linrx1
 *
 * @param <T>
 */
public interface ResultSetCallback<T> {
	List<T> doResultSet(ResultSet rs) throws SQLException;

}
