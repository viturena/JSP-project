package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.catgen.MasterMarket;
import com.catgen.NetworkMarket;
import com.catgen.loader.MasterMarketLoader;

public class MasterMarketFactory 
{
	// NMMS Changes [Registration and Login] : March 2010 - begin
	public static void save(Connection conn, NetworkMarket networkMarket) throws SQLException{
		PreparedStatement pstmt=conn.prepareStatement("INSERT INTO MasterMarkets(marketid,domainname) values(?,?)");
		try{
			pstmt.setString(1, networkMarket.NetworkMarketID);
			pstmt.setString(2, networkMarket.NetworkMarketID+".openentry.com");
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	// NMMS Changes [Registration and Login] : March 2010 - end
	
	public static void remove(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt=conn.prepareStatement("delete from MasterMarkets where marketid=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void UpdateMasterMarket(Connection conn) throws SQLException
	{
		// Commented to prevent deletion of mastermarket record deletion of
		// NMs created with Network Market Management System.
//		PreparedStatement pstmt = conn.prepareStatement("DELETE FROM MasterMarkets");
//		pstmt.executeUpdate();
		
		MasterMarketLoader masterMarketLoader = new MasterMarketLoader();
		//Original Master Network Markets sheet.
		masterMarketLoader.LoadData(conn, "http://spreadsheets.google.com/feeds/cells/p2DIlEfXXvRGM1iKwllxSaw/od6/public/basic");

		//Copy Master Network Markets sheet.
		//masterMarketLoader.LoadData(conn, "http://spreadsheets.google.com/feeds/cells/tztkuQByjrEy-CfNWQmQhDg/od6/public/basic");
	}
	
	private static void LoadMasterMarketFromResultSet(ResultSet rs, MasterMarket MasterMarket) throws SQLException
	{
		MasterMarket.NetworkMarketID = rs.getString("MarketID");
		MasterMarket.Description = rs.getString("Description");
		MasterMarket.DomainName = rs.getString("DomainName");
		MasterMarket.MarketsURL = rs.getString("MarketsURL");
		MasterMarket.MarketInfoURL = rs.getString("MarketInfoURL");
		MasterMarket.PagesURL = rs.getString("PagesURL");
		MasterMarket.FeaturedProductsURL = rs.getString("FeaturedProductsURL");
		// Categories Changes: Feb 2010
		MasterMarket.CategoriesURL = rs.getString("CategoriesURL");
		// Template Stylesheet Change Feb2010
		MasterMarket.StyleSheetURL = rs.getString("StyleSheetURL");
		MasterMarket.IsSuperNm = rs.getBoolean("IsSuperNm");
	}

	public static MasterMarket getMasterMarketByDomainName(Connection conn, String domainName) throws SQLException
	{
		MasterMarket MasterMarket = null;

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM MasterMarkets WHERE DomainName = ?" );
		try
		{
			pstmt.setString(1, domainName);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
				{
					MasterMarket = new MasterMarket();

					LoadMasterMarketFromResultSet(rs, MasterMarket);
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

		return MasterMarket;
	}

	public static MasterMarket getMasterMarketByCode(Connection conn, String marketId) throws SQLException
	{
		MasterMarket MasterMarket = null;

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM MasterMarkets WHERE MarketID = ?" );
		try
		{
			pstmt.setString(1, marketId);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
				{
					MasterMarket = new MasterMarket();

					LoadMasterMarketFromResultSet(rs, MasterMarket);
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

		return MasterMarket;
	}
	
	public static List<MasterMarket> getMasterMembers(Connection conn) throws SQLException
	{
		ArrayList<MasterMarket> MasterMarkets = new ArrayList<MasterMarket>();

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM MasterMarkets ORDER BY MarketID" );
		try
		{
			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					MasterMarket MasterMarket = new MasterMarket();

					LoadMasterMarketFromResultSet(rs, MasterMarket);

					MasterMarkets.add(MasterMarket);
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


		return MasterMarkets;
	}
	
	public static void upgradeToSuperNM(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update MasterMarkets set isSuperNM=true where marketid=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void degradeFromSuperNM(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update MasterMarkets set isSuperNM=false where marketid=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void updateDomainName(Connection conn, String marketId, String domainName) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update MasterMarkets set DomainName=? where marketid=?");
		try{
			pstmt.setString(1, domainName);
			pstmt.setString(2, marketId);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
}
