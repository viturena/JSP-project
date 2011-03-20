package com.catgen.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import com.catgen.Constants;
import com.catgen.Page;
import com.catgen.PageBuffer;
import com.catgen.Utils;
import com.catgen.factories.PageBufferFactory;
import com.catgen.factories.PageFactory;
import com.catgen.factories.XMLFactory;
import com.catgen.helper.PageBufferHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PageController {
	public synchronized static String process(Connection conn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException 
	{
		String res = "";
		String strSubModule = request.getParameter("submodule");
		String marketId = request.getParameter("marketid");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String sRowId = request.getParameter("rowid");
		String bufferId = request.getParameter("bufferid");
		
		int subModule = 0, rowId = 0, size = 0, seq = 0;
		try{
			subModule = Integer.parseInt(strSubModule);
		}catch(Exception e){
			throw new NumberFormatException();
		}
		
		try{
			size = Integer.parseInt(request.getParameter("size"));
		}catch(Exception e){
			
		}
		
		try{
			seq = Integer.parseInt(request.getParameter("seq"));
		}catch(Exception e){
			
		}
		
		switch(subModule){
		case Constants.POPULATE_PAGES:
			res = getPageXMLByName(conn, marketId);
			break;
		case Constants.ADD_PAGE:
			res = addPage(conn, marketId, name);
			break;
		case Constants.GET_PAGE_DETAILS:
			try{
				rowId = Integer.parseInt(sRowId);
			}catch(Exception e){
				throw new NumberFormatException();
			}
			res = getPageDetails(conn, marketId, rowId);
			break;
		case Constants.UPDATE_PAGE:
			try{
				rowId = Integer.parseInt(sRowId);
			}catch(Exception e){
				throw new NumberFormatException();
			}
			res = updatePage(conn, marketId, rowId, description, bufferId, size, seq);
			break;
		case Constants.HIDE_PAGE:
			try{
				rowId = Integer.parseInt(sRowId);
			}catch(Exception e){
				throw new NumberFormatException();
			}
			res = changePageHiddenStatus(conn, marketId, rowId);
			break;
		case Constants.REMOVE_PAGE:
			try{
				rowId = Integer.parseInt(sRowId);
			}catch(Exception e){
				throw new NumberFormatException();
			}
			res = removePage(conn, marketId, rowId);
			break;
		case Constants.MOVE_PAGE_UP:
			try{
				rowId = Integer.parseInt(sRowId);
			}catch(Exception e){
				throw new NumberFormatException();
			}
			res = movePage(conn, marketId, rowId, Constants.MOVE_PAGE_UP);
			break;
		case Constants.MOVE_PAGE_DOWN:
			try{
				rowId = Integer.parseInt(sRowId);
			}catch(Exception e){
				throw new NumberFormatException();
			}
			res = movePage(conn, marketId, rowId, Constants.MOVE_PAGE_DOWN);
			break;
		case Constants.GET_PAGE_BUFFER:
			res = getPageBuffer(conn, marketId, bufferId, seq);
			break;
		default:
			throw new SQLException();
		}
		return res;
	}
	
	public static String getPageXMLByName(Connection conn, String marketId) throws SQLException{
		List<Page> pages = PageFactory.getPages(conn, marketId);
		String pgCount = XMLFactory.getMessageElement("pagecount", String.valueOf(pages.size()));
		String res=XMLFactory.getMessageElement("marketid", marketId);
		
		int ctr=1;
		for(Page page: pages){
			String temp = XMLFactory.getMessageElement("name"+ctr, StringEscapeUtils.escapeXml(page.Name));
			temp += XMLFactory.getMessageElement("rowid"+ctr, String.valueOf(page.RowID));
			temp = XMLFactory.getMessageElement("page"+ctr, temp);
			res += temp;
			ctr++;
		}
		return pgCount + res;
	}
	
	public static String addPage(Connection conn, String marketId, String name) throws SQLException{
		Page page = new Page();
		page.NetworkMarketID = marketId;
		page.Name = name;
		page.Description = "";
		page.RowID = PageFactory.getMaxRowID(conn, marketId)+1;
		
		PageFactory.save(conn, page);
		String res = XMLFactory.getMessageElement("marketid", marketId);
		res += XMLFactory.getMessageElement("pagename",name);
		res += XMLFactory.getMessageElement("rowid",String.valueOf(page.RowID));
		return res;
	}
	
	public static String getPageDetails(Connection conn, String marketId, int rowId) throws SQLException, UnsupportedEncodingException{
		Page page = PageFactory.getPageByRowId(conn, marketId, rowId);
		String res = "";
		String description = page.Description;
		int size = description.length()/Constants.PAGE_SIZE+1;
		String desc="";
		String bufferId = Utils.generateRandomString(8);
		for(int ctr=0;ctr<size;ctr++){
			int begin = ctr*Constants.PAGE_SIZE;
			int end = begin+Constants.PAGE_SIZE;
			if(end>(description.length())){
				end=description.length();
			}
			desc = description.substring(begin,end);
			PageBuffer pageBuffer = new PageBuffer();
			pageBuffer.marketId = marketId;
			pageBuffer.bufferId = bufferId;
			pageBuffer.size = size;
			pageBuffer.seq = ctr+1;
			pageBuffer.data = desc;
			PageBufferFactory.save(conn, pageBuffer);
		}
		res += XMLFactory.getMessageElement("marketid", marketId);
		res += XMLFactory.getMessageElement("rowid", String.valueOf(rowId));
		res += XMLFactory.getMessageElement("bufferid", bufferId);
		res += XMLFactory.getMessageElement("size",String.valueOf(size));
		res += XMLFactory.getMessageElement("cbmodule",String.valueOf(Constants.PAGES));
		res += XMLFactory.getMessageElement("cbsubmodule",String.valueOf(Constants.GET_PAGE_BUFFER));
		res += XMLFactory.getMessageElement("hidden",(PageFactory.getPageByRowId(conn, marketId, rowId).Hidden)?"1":"0");
		return res;
	}
	
	public static String updatePage(Connection conn, String marketId, int rowId, String description, String bufferId, int size, int seq) throws SQLException{
		PageBuffer pageBuffer = new PageBuffer();
		pageBuffer.marketId = marketId;
		pageBuffer.bufferId = bufferId;
		pageBuffer.seq = seq;
		pageBuffer.size = size;
		pageBuffer.data = description;
		PageBufferFactory.save(conn, pageBuffer);
		if(PageBufferHelper.allPageBufferAvailable(conn, marketId, bufferId)){
			String content = PageBufferHelper.getPageContent(conn, marketId, bufferId);
			PageFactory.updateDescription(conn, marketId, rowId, content);
			PageBufferFactory.removePageBuffers(conn, marketId, bufferId);
		}
		return "";
	}
	
	public static String changePageHiddenStatus(Connection conn, String marketId, int rowId) throws SQLException{
		PageFactory.changePageHiddenStatus(conn, marketId, rowId);
		return XMLFactory.getMessageElement("marketid", marketId) + XMLFactory.getMessageElement("hidden", (PageFactory.getPageByRowId(conn, marketId, rowId).Hidden)?"1":"0");
	}
	
	public static String removePage(Connection conn, String marketId, int rowId) throws SQLException{
		PageFactory.removePage(conn, marketId, rowId);
		return XMLFactory.getMessageElement("marketid", marketId);
	}
	
	public static String movePage(Connection conn, String marketID, int rowId, int direction) throws SQLException{
		int rowId2 = PageFactory.swapRowID(conn, marketID, rowId, direction);
		return XMLFactory.getMessageElement("marketid", marketID) + XMLFactory.getMessageElement("rowid", String.valueOf(rowId2));
	}
	
	public static String getPageBuffer(Connection conn, String marketId, String bufferId, int seq) throws SQLException{
		String desc = PageBufferFactory.getPageBufferData(conn, marketId, bufferId, seq);
		desc = StringEscapeUtils.escapeXml(desc);
		PageBufferFactory.removePageBuffer(conn, marketId, bufferId, seq);
		String res = XMLFactory.getMessageElement("marketid", marketId);
		res += XMLFactory.getMessageElement("bufferid", bufferId);
		res += XMLFactory.getMessageElement("seq",String.valueOf(seq));
		res += XMLFactory.getMessageElement("desc",desc);
		res += XMLFactory.getMessageElement("cbmodule",String.valueOf(Constants.PAGES));
		res += XMLFactory.getMessageElement("cbsubmodule",String.valueOf(Constants.GET_PAGE_BUFFER));
		return res;
	}
}
