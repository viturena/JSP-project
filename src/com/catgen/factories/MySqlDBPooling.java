package com.catgen.factories;

import javax.servlet.*;
import javax.sql.*;
import javax.naming.*;

public class MySqlDBPooling implements ServletContextListener
{
	public void contextInitialized(ServletContextEvent sce)
	{
		try {
			// Obtain our environment naming context
			Context envCtx = (Context) new InitialContext().lookup("java:comp/env");

			// Look up our data source
			DataSource  ds = (DataSource) envCtx.lookup("jdbc/NetmarketDB");

			sce.getServletContext().setAttribute("NetmarketPool", ds);
			
			System.out.println("MySqlDBPooling is set to " + ds.toString() );
		} 
		catch(NamingException e)
		{
			e.printStackTrace();
		}
	}
	
	public void contextDestroyed(ServletContextEvent sce)
	{
	}
}

