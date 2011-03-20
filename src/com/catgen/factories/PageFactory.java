package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.catgen.Constants;
import com.catgen.Page;

public class PageFactory {
	public static void Save(Page page)
	{
		
	}
	
	public static void Load(Page page)
	{
		
	}

	// NMMS Changes [Registration and Login] : March 2010 - begin
	public static void save(Connection conn, Page page) throws SQLException{
		PreparedStatement pstmt=conn.prepareStatement("INSERT INTO Pages(marketid, name, description, rowid) values(?, ?, ?, ?)");
		try{
			pstmt.setString(1, page.NetworkMarketID);
			pstmt.setString(2, page.Name);
			pstmt.setString(3, page.Description);
			pstmt.setInt(4, page.RowID);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void removeAll(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from Pages where marketId = ?");
		try{
			pstmt.setString(1, marketId);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static int findNextRowID(Connection conn, String marketID, int rowID) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select min(RowID) from Pages where marketID=? and RowID>?");
		ResultSet rs;
		int nextRowId=Constants.NONE;
		try{
			pstmt.setString(1, marketID);
			pstmt.setInt(2, rowID);
			rs = pstmt.executeQuery();
			if(rs.next()){
				nextRowId = rs.getInt(1);
				if(0==nextRowId)nextRowId=Constants.NONE;
			}
		}finally{
			pstmt.close();
		}
		return nextRowId;
	}
	
	public static int findPreviousRowID(Connection conn, String marketID, int rowID) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select max(RowID) from Pages where marketID=? and RowID<?");
		ResultSet rs;
		int previousRowId=Constants.NONE;
		try{
			pstmt.setString(1, marketID);
			pstmt.setInt(2, rowID);
			rs = pstmt.executeQuery();
			if(rs.next()){
				previousRowId = rs.getInt(1);
				if(0==previousRowId)previousRowId=Constants.NONE;
			}
		}finally{
			pstmt.close();
		}
		return previousRowId;
	}
	
	public static int swapRowID(Connection conn, String marketID, int rowId, int direction) throws SQLException{
		int rowId2=0;
		if(Constants.MOVE_PAGE_UP==direction){
			rowId2 = findPreviousRowID(conn, marketID, rowId);
		}else if(Constants.MOVE_PAGE_DOWN==direction){
			rowId2 = findNextRowID(conn, marketID, rowId);
		}else{
			throw new SQLException();
		}
		if(Constants.NONE==rowId2){
			throw new SQLException();
		}
		PreparedStatement pstmt1 = conn.prepareStatement("update Pages set RowID = 0 where marketID =? and RowID=?");
		pstmt1.setString(1, marketID);
		pstmt1.setInt(2, rowId);

		PreparedStatement pstmt2 = conn.prepareStatement("update Pages set RowID = ? where marketID =? and RowID=?");
		pstmt2.setInt(1, rowId);
		pstmt2.setString(2, marketID);
		pstmt2.setInt(3, rowId2);

		PreparedStatement pstmt3 = conn.prepareStatement("update Pages set RowID = ? where marketID =? and RowID=0");
		pstmt3.setInt(1, rowId2);
		pstmt3.setString(2, marketID);
		
		conn.setAutoCommit(false);
		try{
			pstmt1.execute();
			pstmt2.execute();
			pstmt3.execute();
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			throw new SQLException();
		}finally{
			conn.setAutoCommit(true);
			pstmt1.close();
			pstmt2.close();
			pstmt3.close();
		}
		return rowId2;
	}
	// NMMS Changes [Registration and Login] : March 2010 - end
	
	private static void LoadPageFromResultSet(ResultSet rs, Page page) throws SQLException
	{
		page.NetworkMarketID = rs.getString("MarketID");
		page.Name = rs.getString("Name");
		page.Code = rs.getString("Code");
		page.Description = rs.getString("Description");
		page.HeaderURL = rs.getString("HeaderURL");
		page.HeaderData = rs.getString("HeaderData");
		page.FooterURL = rs.getString("FooterURL");
		page.FooterData = rs.getString("FooterData");
		page.Type = rs.getString("Type");
		page.Header = rs.getString("Header");
		page.Footer = rs.getString("Footer");
		page.CSS = rs.getString("CSS");
		page.JavaScript = rs.getString("JavaScript");
		page.Template = rs.getString("Template");
		String sRow = rs.getString("RowID");
		int row = 0;
		try{
			row = Integer.parseInt(sRow);
		}catch(Exception e){
			row = 0;
		}
		page.RowID = row;
		page.Hidden = rs.getBoolean("hidden");
	}
	
	public static int getMaxRowID(Connection conn, String marketId) throws SQLException
	{
		int count=0;
		PreparedStatement pstmt = conn.prepareStatement("select max(RowID) from Pages where marketid=?");
		pstmt.setString(1, marketId);
		try{
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				String sCount = rs.getString(1);
				try{
					count = Integer.parseInt(sCount);
				}catch(Exception e){
				}
			}
		}finally{
			pstmt.close();
		}
		return count;
	}
	
	public static void updateDescription(Connection conn, String marketId, int rowId, String description) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update Pages set description=? where marketid=? and RowID=?");
		pstmt.setString(1, description);
		pstmt.setString(2, marketId);
		pstmt.setInt(3, rowId);
		try{
			pstmt.execute();
		}finally{
			pstmt.close();
		}
		
	}
	
	public static void changePageHiddenStatus(Connection conn, String marketId, int rowId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update Pages set hidden = not hidden where marketid=? and RowID=?");
		pstmt.setString(1, marketId);
		pstmt.setInt(2, rowId);
		try{
			pstmt.execute();
		}finally{
			pstmt.close();
		}
		
	}
	
	public static void removePage(Connection conn, String marketId, int rowId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from Pages where marketid=? and RowID=?");
		pstmt.setString(1, marketId);
		pstmt.setInt(2, rowId);
		try{
			pstmt.execute();
		}finally{
			pstmt.close();
		}
		
	}
	
	public static Page getPageByName(Connection conn, String marketId, String name) throws SQLException
	{
		Page page = null;

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Pages WHERE MarketID = ? AND name = ?" );
		try
		{
			pstmt.setString(1, marketId);
			pstmt.setString(2, name);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
				{
					page = new Page();

					LoadPageFromResultSet(rs, page);
				}
			}
			finally
			{
				rs.close();
			}
		}
		finally
		{
			pstmt.close();
		}			
		
		return page;		
	}
	
	public static Page getPageByRowId(Connection conn, String marketId, int rowId) throws SQLException
	{
		Page page = null;

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Pages WHERE MarketID = ? AND RowID = ?" );
		try
		{
			pstmt.setString(1, marketId);
			pstmt.setInt(2, rowId);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
				{
					page = new Page();

					LoadPageFromResultSet(rs, page);
				}
			}
			finally
			{
				rs.close();
			}
		}
		finally
		{
			pstmt.close();
		}			
		
		return page;		
	}
	
	public static List<Page> getPages(Connection conn, String marketId) throws SQLException
	{
		ArrayList<Page> pages = new ArrayList<Page>();

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Pages WHERE MarketID = ? ORDER BY RowID asc" );
		try
		{
			pstmt.setString(1, marketId);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Page page = new Page();

					LoadPageFromResultSet(rs, page);
					
					pages.add(page);
				}
			}
			finally
			{
				rs.close();
			}
		}
		finally
		{
			pstmt.close();
		}			
		
		return pages;		
	}
	
	public static List<Page> getPagesOrderByRow(Connection conn, String marketId) throws SQLException
	{
		ArrayList<Page> pages = new ArrayList<Page>();

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Pages WHERE MarketID = ? ORDER BY RowID, Name" );
		try
		{
			pstmt.setString(1, marketId);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Page page = new Page();

					LoadPageFromResultSet(rs, page);
					
					pages.add(page);
				}
			}
			finally
			{
				rs.close();
			}
		}
		finally
		{
			pstmt.close();
		}			
		
		return pages;		
	}
}
