package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.catgen.MarketMember;;

public class MarketMemberFactory
{
	private static void LoadMarketMemberFromResultSet(ResultSet rs, MarketMember marketMember) throws SQLException
	{
		marketMember.NetworkMarketID = rs.getString("MarketID");
		marketMember.CompanyCode = rs.getString("CompanyCode");
		marketMember.ProductsURL = rs.getString("ProductsURL");
		marketMember.CompanyInfoURL = rs.getString("CompanyInfoURL");
		marketMember.PagesURL = rs.getString("PagesURL");
		marketMember.DomainName = rs.getString("DomainName");
		marketMember.TrustSeal = rs.getInt("TrustSeal");
	}
	
	public static MarketMember getMarketMemberByCode(Connection conn, String marketId, String companyCode) throws SQLException
	{
		MarketMember marketMember = null;

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM MarketMembers WHERE MarketID = ? AND CompanyCode = ?" );
		try
		{
			pstmt.setString(1, marketId);
			pstmt.setString(2, companyCode);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
				{
					marketMember = new MarketMember();

					LoadMarketMemberFromResultSet(rs, marketMember);
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

		return marketMember;
	}
	
	public static List<String> getMarketTrustSealedMembersCodes(Connection conn, String marketId) throws SQLException
	{
		ArrayList<String> companyCodes = new ArrayList<String>();  
		
		PreparedStatement pstmt = conn.prepareStatement( "SELECT distinct CompanyCode FROM nm2companymap WHERE MarketID = ? AND approved=true and acceptedtill>CURRENT_TIMESTAMP and featuredtill>CURRENT_TIMESTAMP" );
		try
		{
			pstmt.setString(1, marketId);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					companyCodes.add(rs.getString("CompanyCode"));
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

		return companyCodes;
	}
	public static void removeCompany(Connection conn, String companyCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from MarketMembers where companycode = ?");
		
		pstmt.setString(1,companyCode);

		pstmt.executeUpdate();
		
		PreparedStatement pstmt1 = conn.prepareStatement("delete from nm2companymap where companycode = ?");
		
		pstmt1.setString(1,companyCode);

		pstmt1.executeUpdate();
	}
	
	public static void removeNM(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from MasterMarkets where marketid = ?");
		
		pstmt.setString(1,marketId);

		pstmt.executeUpdate();
		
		PreparedStatement pstmt1 = conn.prepareStatement("delete from nm2companymap where marketid = ?");
		
		pstmt1.setString(1,marketId);

		pstmt1.executeUpdate();
		
		PreparedStatement pstmt2 = conn.prepareStatement("delete from MarketMembers where marketid = ?");
		
		pstmt2.setString(1,marketId);

		pstmt2.executeUpdate();
	}
}
