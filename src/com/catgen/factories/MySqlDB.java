package com.catgen.factories;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.sql.DataSource;


public class MySqlDB {
	private static DataSource datasource = null;

	public static synchronized Connection getDBConnection(ServletContext servletContext) throws SQLException 
	{
		if(datasource == null)
			datasource = (DataSource) servletContext.getAttribute("NetmarketPool");

		return datasource.getConnection();
	}
}