package com.catgen.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.catgen.Category;
import com.catgen.Constants;
import com.catgen.factories.CategoryFactory;
import com.catgen.factories.XMLFactory;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.Connection;
import java.sql.SQLException;

public class CategoryController {
	public static String process(Connection conn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String res="";
		String strSubModule = request.getParameter("submodule");
		String marketId = request.getParameter("marketid");
		String catKey = request.getParameter("catkey");
		String catValue = request.getParameter("catvalue");
		String editedCategoryValue = request.getParameter("newvalue");
		
		int subModule = 0;
		try{
			subModule = Integer.parseInt(strSubModule);
		}catch(Exception e){
			throw new NumberFormatException();
		}
		
		switch(subModule){
		case Constants.EDIT_CATEGORY:
			res = editCategory(conn, marketId, catKey,editedCategoryValue);
			break;
		case Constants.REMOVE_CATEGORY:
			res = removeCategory(conn, marketId, catKey);
			break;
		case Constants.ADD_CATEGORY:
			res = addCategory(conn, marketId, catKey, catValue);
			break;
		}	
		return res;
	}
	
	public static String editCategory(Connection conn, String marketId, String catKey , String editedCategoryValue) throws SQLException{
		String catValue = CategoryFactory.getValueforCategoryKey(CategoryFactory.getCategories(conn, marketId),catKey);
		CategoryFactory.editCategory(conn, marketId, catKey,editedCategoryValue);
		String res = XMLFactory.getMessageElement("marketid", marketId);
		res += XMLFactory.getMessageElement("catkey", catKey);
		res += XMLFactory.getMessageElement("catvalue", catValue);
		return res;
	}
	
	public static String removeCategory(Connection conn, String marketId, String catKey) throws SQLException{
		String catValue = CategoryFactory.getValueforCategoryKey(CategoryFactory.getCategories(conn, marketId),catKey);
		CategoryFactory.removeCategory(conn, marketId, catKey);
		String res = XMLFactory.getMessageElement("marketid", marketId);
		res += XMLFactory.getMessageElement("catkey", catKey);
		res += XMLFactory.getMessageElement("catvalue", catValue);
		return res;
	}
	
	public static String addCategory(Connection conn, String marketId, String catKey, String catValue) throws SQLException{
		Category category = new Category();
		category.marketId = marketId;
		category.categoryKey = catKey;
		category.categoryValue = catValue;
		try {  
			CategoryFactory.save(conn, category);
		} catch (MySQLIntegrityConstraintViolationException e) { 
			return "<errmsg>Category already in the list.</errmsg>";
		} 
		
		String res = XMLFactory.getMessageElement("marketid", marketId);
		res += XMLFactory.getMessageElement("catkey", catKey);
		res += XMLFactory.getMessageElement("catvalue", catValue);
		return res;
	}
}
