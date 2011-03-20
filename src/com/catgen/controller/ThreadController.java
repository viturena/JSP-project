package com.catgen.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.catgen.Constants;
import com.catgen.factories.XMLFactory;
import com.catgen.helper.ImageCheckHelper;

public class ThreadController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public static String process(Connection conn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String res="";
		String strSubModule = request.getParameter("submodule");

		int subModule = 0;
		try{
			subModule = Integer.parseInt(strSubModule);
		}catch(Exception e){
			throw new NumberFormatException();
		}
		
		switch(subModule){
			case Constants.IMAGE_CHECK:
				res = initiateImageCheck(conn);
			break;
		}	
		return res;
	}
	
	public static String initiateImageCheck(Connection conn) throws SQLException {
		new ImageCheckHelper().initiateImageCheck(conn);
		return XMLFactory.getMessageElement("reply", "Img chk initiated");
	}
}
