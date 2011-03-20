package com.catgen.helper;

import java.sql.Connection;
import java.sql.SQLException;

import com.catgen.factories.PageBufferFactory;

public class PageBufferHelper {
	public static boolean allPageBufferAvailable(Connection conn, String marketId, String bufferId) throws SQLException{
		return PageBufferFactory.availablePageBufferCount(conn, marketId, bufferId)==
			PageBufferFactory.getPageBufferSize(conn, marketId, bufferId);
	}
	
	public static String getPageContent(Connection conn, String marketId, String bufferId) throws SQLException{
		int size = PageBufferFactory.getPageBufferSize(conn, marketId, bufferId);
		String content = "";
		for(int i=1;i<=size;i++){
			content += PageBufferFactory.getPageBufferData(conn, marketId, bufferId, i);
		}
		return content;
	}
}
