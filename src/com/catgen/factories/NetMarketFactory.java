package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.catgen.MasterMarket;
import com.catgen.NetworkMarket;
import com.catgen.loader.FeaturedProductsLoader;
import com.catgen.loader.MembersLoader;
import com.catgen.loader.NetworkMarketInfoLoader;
import com.catgen.loader.PagesLoader;
//Categories Changes: Feb 2010
import com.catgen.loader.CategoriesLoader;
//Template Stylesheet Change Feb2010
import com.catgen.loader.StyleSheetLoader;
import com.catgen.Constants;
import com.catgen.UpdateStatusBean;

public class NetMarketFactory 
{
	public static void Save(NetworkMarket networkMarket){
		
	}
	
	// NMMS Changes [Registration and Login] : March 2010 - begin
	public static void save(Connection conn, NetworkMarket networkMarket) throws SQLException{
		try{
			String sql = "INSERT INTO NetworkMarkets(MarketID, Name, RowID, Description, HomePageDescription, logoimage, Contact, Address, City, State, Zip, Country, ContactEmail, PayPalEmail, GoogleMerchantID, TwoCOSID, Header, Footer, HeaderURL, FooterURL, CSS, Currency, InquiryURL, ThumbnailSize, TemplateWidth, Template, Font, FontSize, Color, SecondaryColor, TetriaryColor, Background, Background2, Background3, BackgroundImage, ProductImageSize, keywords, MobileNo, MetaDescription, GoogleAnalytics, GSiteVerification) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			if(conn != null)
			{
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,networkMarket.NetworkMarketID);
				pstmt.setString(2,networkMarket.Name);
				pstmt.setString(3,null);
				pstmt.setString(4,networkMarket.Description);
				pstmt.setString(5,networkMarket.HomePageDescription);
				pstmt.setString(6,networkMarket.LogoImage);
				pstmt.setString(7,networkMarket.Contact);
				pstmt.setString(8,networkMarket.Address);
				pstmt.setString(9,networkMarket.City);
				pstmt.setString(10,networkMarket.State);
				pstmt.setString(11,networkMarket.Zip);
				pstmt.setString(12,networkMarket.Country);
				pstmt.setString(13,networkMarket.ContactEmail);
				pstmt.setString(14,networkMarket.PayPalEmail);
				pstmt.setString(15,networkMarket.GoogleMerchantID);
				pstmt.setString(16,networkMarket.TwoCOSID);
				pstmt.setString(17,networkMarket.Header);
				pstmt.setString(18,networkMarket.Footer);
				pstmt.setString(19,networkMarket.HeaderURL);
				pstmt.setString(20,networkMarket.FooterURL);
				pstmt.setString(21,networkMarket.CSS);
				pstmt.setString(22,networkMarket.Currency);
				pstmt.setString(23,networkMarket.InquiryURL);
				pstmt.setString(24,networkMarket.ThumbnailSize);
				pstmt.setString(25,networkMarket.TemplateWidth);
				pstmt.setString(26,networkMarket.Template);
				pstmt.setString(27,networkMarket.Font);
				pstmt.setString(28,networkMarket.FontSize);
				pstmt.setString(29,networkMarket.Color);
				pstmt.setString(30,networkMarket.SecondaryColor);
				pstmt.setString(31,networkMarket.TetriaryColor);
				pstmt.setString(32,networkMarket.Background);
				pstmt.setString(33,networkMarket.Background2);
				pstmt.setString(34,networkMarket.Background3);
				pstmt.setString(35,networkMarket.BackgroundImage);
				pstmt.setString(36,networkMarket.ProductImageSize);
				pstmt.setString(37,networkMarket.Keywords);
				pstmt.setString(38,networkMarket.mobileNo);
				pstmt.setString(39,networkMarket.MetaDescription);
				pstmt.setString(40,networkMarket.GoogleAnalytics);
				pstmt.setString(41,networkMarket.GSiteVerification);
				pstmt.executeUpdate();
			}
		}catch(SQLException e){
			throw e;
		}
	}
	
	public static void remove(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from NetworkMarkets where marketid=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static boolean isValidNM(String userId, String sType){
		int type=0;
		try{
			type = Integer.parseInt(sType);
		}catch(Exception e){
			type=0;
		}
		if(userId!=null && userId.length()>0 && Constants.NETWORK_MARKET==type){
			return true;
		}
		return false;
	}
	
// NMMS Changes [Registration and Login] : March 2010 - end

	public static void Load(NetworkMarket NetworkMarket)
	{

	}	
	
	// Additional Update Change March 2010 - begin
	public static void UpdateNetworkMarket(Connection conn, String marketId, int updateItem) throws SQLException
	{
		NetMarketFactory.logNewUpdate(conn, marketId);
		MasterMarket masterMarket = MasterMarketFactory.getMasterMarketByCode(conn, marketId);  
		
		if(masterMarket != null)
		{
			PreparedStatement pstmt;
			
			if (updateItem==Constants.ALL || updateItem==Constants.NETWORK_MARKET_INFO){
				pstmt = conn.prepareStatement("DELETE FROM NetworkMarkets WHERE MarketID = ?");
				pstmt.setString(1, marketId);
				pstmt.executeUpdate();
				
				NetMarketFactory.setUpdateStatus(conn, masterMarket.NetworkMarketID, Constants.NETWORK_MARKET_INFO);
				NetworkMarketInfoLoader networkMarketInfoLoader = new NetworkMarketInfoLoader();
				networkMarketInfoLoader.ClearExtras();
				networkMarketInfoLoader.AddExtras("MarketID", masterMarket.NetworkMarketID);
				networkMarketInfoLoader.LoadData(conn, masterMarket.MarketInfoURL);
			}

			if (updateItem==Constants.ALL || updateItem==Constants.MEMBERS_AND_PRODUCTS){
//				pstmt = conn.prepareStatement("DELETE FROM MarketMembers WHERE MarketID = ?");
//				pstmt.setString(1, marketId);
//				pstmt.executeUpdate();		
//			
//				pstmt = conn.prepareStatement("DELETE FROM Companies WHERE MarketID = ?");
//				pstmt.setString(1, marketId);
//				pstmt.executeUpdate();
//	
//				pstmt = conn.prepareStatement("DELETE FROM Products WHERE MarketID = ?");
//				pstmt.setString(1, marketId);
//				pstmt.executeUpdate();
//				
//				pstmt = conn.prepareStatement("DELETE FROM ProductLines WHERE MarketID = ?");
//				pstmt.setString(1, marketId);
//				pstmt.executeUpdate();
			
				pstmt = conn.prepareStatement("DELETE FROM nm2companymap WHERE MarketID = ?");
				pstmt.setString(1, marketId);
				pstmt.executeUpdate();			
				
				NetMarketFactory.setUpdateStatus(conn, masterMarket.NetworkMarketID, Constants.MEMBERS_AND_PRODUCTS);
				MembersLoader membersLoader = new MembersLoader();
				membersLoader.MarketID = masterMarket.NetworkMarketID;
				membersLoader.ClearExtras();
				membersLoader.AddExtras("MarketID", masterMarket.NetworkMarketID);
				membersLoader.AddExtras("approved", "0");
				membersLoader.LoadData(conn, masterMarket.MarketsURL);
			}

			if (updateItem==Constants.ALL || updateItem==Constants.PAGES){
				pstmt = conn.prepareStatement("DELETE FROM Pages WHERE MarketID = ?");
				pstmt.setString(1, marketId);
				pstmt.executeUpdate();
				
				if(masterMarket.PagesURL != null && masterMarket.PagesURL.trim().length() > 0){
					NetMarketFactory.setUpdateStatus(conn, masterMarket.NetworkMarketID, Constants.PAGES);
					PagesLoader pagesLoader = new PagesLoader();
					pagesLoader.ClearExtras();
					pagesLoader.AddExtras("MarketID", masterMarket.NetworkMarketID);
					pagesLoader.LoadData(conn, masterMarket.PagesURL);
				}
			}

			if (updateItem==Constants.ALL || updateItem==Constants.FEATURED_PRODUCTS){
				pstmt = conn.prepareStatement("DELETE FROM FeaturedProducts WHERE MarketID = ?");
				pstmt.setString(1, marketId);
				pstmt.executeUpdate();
				
				if(masterMarket.FeaturedProductsURL != null && masterMarket.FeaturedProductsURL.trim().length() > 0){
					NetMarketFactory.setUpdateStatus(conn, masterMarket.NetworkMarketID, Constants.FEATURED_PRODUCTS);
					FeaturedProductsLoader featuredProductsLoader = new FeaturedProductsLoader();
					featuredProductsLoader.ClearExtras();
					featuredProductsLoader.AddExtras("MarketID", masterMarket.NetworkMarketID);
					featuredProductsLoader.LoadData(conn, masterMarket.FeaturedProductsURL);
				}
			}
			
			if (updateItem==Constants.ALL || updateItem==Constants.CATEGORIES){
				pstmt = conn.prepareStatement("DELETE FROM Category WHERE MarketID = ?");
				pstmt.setString(1, marketId);
				pstmt.executeUpdate();

				if(masterMarket.CategoriesURL != null && masterMarket.CategoriesURL.trim().length()>0){
					NetMarketFactory.setUpdateStatus(conn, masterMarket.NetworkMarketID, Constants.CATEGORIES);
					CategoriesLoader categoriesLoader = new CategoriesLoader();
					categoriesLoader.ClearExtras();
					categoriesLoader.AddExtras("MarketID", masterMarket.NetworkMarketID);
					categoriesLoader.LoadData(conn, masterMarket.CategoriesURL);
				}
			}

			if (updateItem==Constants.ALL || updateItem==Constants.STYLE){
				pstmt = conn.prepareStatement("DELETE FROM Style WHERE MarketID = ?");
				pstmt.setString(1, marketId);
				pstmt.executeUpdate();
			
				if(masterMarket.StyleSheetURL != null && masterMarket.StyleSheetURL.trim().length()>0){
					NetMarketFactory.setUpdateStatus(conn, masterMarket.NetworkMarketID, Constants.STYLE);
					StyleSheetLoader styleSheetLoader = new StyleSheetLoader();
					styleSheetLoader.ClearExtras();
					styleSheetLoader.AddExtras("MarketID", masterMarket.NetworkMarketID);
					styleSheetLoader.LoadData(conn, masterMarket.StyleSheetURL);
				}
			}
			NetMarketFactory.logUpdateCompletion(conn, masterMarket.NetworkMarketID);
		}
	}
	// Additional Update Change March 2010 - end

	private static void LoadNetworkMarketFromResultSet(ResultSet rs, NetworkMarket NetworkMarket) throws SQLException
	{
		NetworkMarket.NetworkMarketID = rs.getString("MarketID");
		NetworkMarket.Name = rs.getString("Name");
		NetworkMarket.Description = rs.getString("Description");
		NetworkMarket.HomePageDescription = rs.getString("HomePageDescription");
		NetworkMarket.LogoImage = rs.getString("LogoImage");
		NetworkMarket.Address = rs.getString("Address");
		NetworkMarket.City = rs.getString("City");
		NetworkMarket.State = rs.getString("State");
		NetworkMarket.Zip = rs.getString("Zip");
		NetworkMarket.Contact = rs.getString("Contact");
		NetworkMarket.ContactEmail = rs.getString("ContactEmail");
		NetworkMarket.PayPalEmail = rs.getString("PayPalEmail");
		NetworkMarket.GoogleMerchantID = rs.getString("GoogleMerchantID");
		NetworkMarket.TwoCOSID = rs.getString("TwoCOSID");
		NetworkMarket.Header = rs.getString("Header");
		NetworkMarket.Footer = rs.getString("Footer");
		NetworkMarket.HeaderURL = rs.getString("HeaderURL");
		NetworkMarket.FooterURL = rs.getString("FooterURL");
		NetworkMarket.CSS = rs.getString("CSS");
		NetworkMarket.Currency = rs.getString("Currency");
		NetworkMarket.InquiryURL = rs.getString("InquiryURL");
		NetworkMarket.ThumbnailSize = rs.getString("ThumbnailSize");
		NetworkMarket.TemplateWidth = rs.getString("TemplateWidth");
		NetworkMarket.Template = rs.getString("Template");
		NetworkMarket.Font = rs.getString("Font");
		NetworkMarket.FontSize = rs.getString("FontSize");
		NetworkMarket.Color = rs.getString("Color");
		NetworkMarket.SecondaryColor = rs.getString("SecondaryColor");
		NetworkMarket.TetriaryColor = rs.getString("TetriaryColor");
		NetworkMarket.Background = rs.getString("Background");
		NetworkMarket.Background2 = rs.getString("Background2");
		NetworkMarket.Background3 = rs.getString("Background3");
		NetworkMarket.BackgroundImage = rs.getString("BackgroundImage");
		NetworkMarket.ProductImageSize = rs.getString("ProductImageSize");
		// META Keyword addition change : March 2010
		NetworkMarket.Keywords = rs.getString("keywords");
		NetworkMarket.Country = rs.getString("country");
		NetworkMarket.MetaDescription = rs.getString("MetaDescription");
		NetworkMarket.GoogleAnalytics = rs.getString("GoogleAnalytics");
		NetworkMarket.GSiteVerification = rs.getString("GSiteVerification");
		NetworkMarket.premium = rs.getBoolean("premium");
		NetworkMarket.lazy = rs.getBoolean("lazy");
	}

	public static NetworkMarket getNetworkMarketByCode(Connection conn, String marketId) throws SQLException
	{
		NetworkMarket networkMarket = null;

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM NetworkMarkets WHERE MarketID = ?" );
		try
		{
			pstmt.setString(1, marketId);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
				{
					networkMarket = new NetworkMarket();

					LoadNetworkMarketFromResultSet(rs, networkMarket);
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

		return networkMarket;
	}

	public static List<String> getAssociatedNetworkMarketsIdList(Connection conn, String companyCode) throws SQLException
	{
		List<String> networkMarkets = new ArrayList<String>(); 

		PreparedStatement pstmt = conn.prepareStatement( "SELECT n.MarketID from NetworkMarkets n, nm2companymap m where m.companycode=? and m.marketid = n.marketid order by n.MarketID asc" );
		try
		{
			pstmt.setString(1, companyCode);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					networkMarkets.add(rs.getString("MarketID"));
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

		return networkMarkets;
	}

	public static List<NetworkMarket> getNetmarketMembers(Connection conn) throws SQLException
	{
		ArrayList<NetworkMarket> networkMarkets = new ArrayList<NetworkMarket>();

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM NetworkMarkets ORDER BY MarketID" );
		try
		{
			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					NetworkMarket NetworkMarket = new NetworkMarket();

					LoadNetworkMarketFromResultSet(rs, NetworkMarket);
					
					networkMarkets.add(NetworkMarket);
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


		return networkMarkets;
	}
	
	// NM Update Change : March 2010 - Begin
	public static void logNewUpdate(Connection conn, String marketId){
		try
		{
			String sql = "insert into UpdateStatus values(?,current_timestamp,?);";
			if(conn != null)
			{
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, marketId);
				pstmt.setInt(2, Constants.NETWORK_MARKET_INFO);
				pstmt.executeUpdate();
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void setUpdateStatus(Connection conn, String marketId, int status){
		try
		{
			String sql = "update UpdateStatus set status = ? where marketid = ?";
			if(conn != null)
			{
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, status);
				pstmt.setString(2, marketId);
				pstmt.executeUpdate();
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void logUpdateCompletion(Connection conn, String marketId){
		try
		{
			String sql = "delete from UpdateStatus where marketid=?";
			if(conn != null)
			{
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, marketId);
				pstmt.executeUpdate();
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static HashMap<String, UpdateStatusBean> getUpdateStatus(Connection conn){
		try
		{
			HashMap<String, UpdateStatusBean> updateStatusBeanList = new HashMap<String, UpdateStatusBean>();
			String sql = "select * from UpdateStatus";
			if(conn != null)
			{
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()){
					UpdateStatusBean updateStatusBean = new UpdateStatusBean();
					populateUpdateStatusBean(rs,updateStatusBean);
					updateStatusBeanList.put(updateStatusBean.marketId, updateStatusBean);
				}
			}
			return updateStatusBeanList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getUpdateStatusValue(int intStatus){
		return Constants.SHEETS[intStatus-1];
	}
	
	public static boolean NetworkMarketExistsWithEmailId(Connection conn, String marketId, String email) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select * from NetworkMarkets where marketid=? and ContactEmail=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, email);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return true;
			}
		}finally{
			pstmt.close();
		}
		return false;
	}
	
	public static void populateUpdateStatusBean(ResultSet rs, UpdateStatusBean updateStatusBean){
		try{
			updateStatusBean.marketId = rs.getString("marketId");
			updateStatusBean.timestamp = String.valueOf(rs.getTimestamp("startTimestamp"));
			updateStatusBean.status = rs.getInt("status");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	// NM Update Change : March 2010 - End
	
	public static void addNm2Nm(Connection conn, String marketId, String subMarketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("insert into nm2nmmap(containerNm, containedNm) values(?,?)");
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, subMarketId);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void removeSubNm(Connection conn, String marketId, String subMarketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from nm2nmmap where containerNm=? and containedNm=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, subMarketId);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void changePremiumStatus(Connection conn, String marketId){
		try
		{
			String sql = "update NetworkMarkets set premium = !premium where marketid = ?";
			if(conn != null)
			{
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, marketId);
				pstmt.executeUpdate();				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void changeLazyStatus(Connection conn, String marketId){
		try
		{
			String sql = "update NetworkMarkets set lazy = !lazy where marketid = ?";
			if(conn != null)
			{
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, marketId);
				pstmt.executeUpdate();				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
