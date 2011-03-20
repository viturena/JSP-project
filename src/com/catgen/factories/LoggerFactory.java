package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LoggerFactory {
	public static void write(Connection conn, String log)
	{
		try
		{
			String sql = "INSERT INTO logger VALUES(CURRENT_TIMESTAMP,'"+log+"');";
			if(conn != null)
			{
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
