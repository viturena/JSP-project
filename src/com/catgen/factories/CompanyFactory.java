package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.catgen.Company;
import com.catgen.Constants;
import com.catgen.MasterCompany;
import com.catgen.Product;
import com.catgen.Utils;
import com.catgen.loader.CompanyInfoLoader;
import com.catgen.loader.ProductsLoader;

public class CompanyFactory {

	public static void Save(Company company)
	{

	}
	
	public static void Load(Company company)
	{

	}
	
	// NMMS Changes [Registration and Login] : March 2010 - begin
	public static void Save(Connection conn, Company company) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("insert into Companies(companycode, RowID, Name, Description, HomePageDescription, LogoImage, Country, Contact, ContactEmail, PayPalEmail, GoogleMerchantID, TwoCOSID, Header, Footer, HeaderURL, FooterURL, CSS, Currency, CurrencySymbol, InquiryURL, ThumbnailSize, TemplateWidth, Template, Font, FontSize, Color, SecondaryColor, TetriaryColor, Background, Background2, Background3, BackgroundImage, ProductImageSize, ProductURL, CompanyURL, Address, City, State, Zip, mobileNo) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		
		pstmt.setString(1,company.Code);
		pstmt.setString(2,null);
		pstmt.setString(3,company.Name);
		pstmt.setString(4,company.Description);
		pstmt.setString(5,company.HomePageDescription);
		pstmt.setString(6,company.LogoImage);
		pstmt.setString(7,company.Country);
		pstmt.setString(8,company.Contact);
		pstmt.setString(9,company.ContactEmail);
		pstmt.setString(10,company.PayPalEmail);
		pstmt.setString(11,company.GoogleMerchantID);
		pstmt.setString(12,company.TwoCOSID);
		pstmt.setString(13,company.Header);
		pstmt.setString(14,company.Footer);
		pstmt.setString(15,company.HeaderURL);
		pstmt.setString(16,company.FooterURL);
		pstmt.setString(17,company.CSS);
		pstmt.setString(18,company.Currency);
		pstmt.setString(19,company.CurrencySymbol);
		pstmt.setString(20,company.InquiryURL);
		pstmt.setString(21,company.ThumbnailSize);
		pstmt.setString(22,company.TemplateWidth);
		pstmt.setString(23,company.Template);
		pstmt.setString(24,company.Font);
		pstmt.setString(25,company.FontSize);
		pstmt.setString(26,company.Color);
		pstmt.setString(27,company.SecondaryColor);
		pstmt.setString(28,company.TetriaryColor);
		pstmt.setString(29,company.Background);
		pstmt.setString(30,company.Background2);
		pstmt.setString(31,company.Background3);
		pstmt.setString(32,company.BackgroundImage);
		pstmt.setString(33,company.ProductImageSize);
		pstmt.setString(34,company.ProductURL);
		pstmt.setString(35,company.CompanyURL);
		pstmt.setString(36,company.Address);
		pstmt.setString(37,company.City);
		pstmt.setString(38,company.State);
		pstmt.setString(39,company.Zip);
		pstmt.setString(40,company.mobileNo);

		pstmt.executeUpdate();
	}
	
	public static void requestAssociation(Connection conn, String marketId, String companyCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("insert into nm2companymap(marketid, companycode, approved) values(?,?,false)");
		
		pstmt.setString(1,marketId);
		pstmt.setString(2,companyCode);

		pstmt.executeUpdate();
	}
	
	public static void makeAssociation(Connection conn, String marketId, String companyCode) throws SQLException{
		//PreparedStatement pstmt = conn.prepareStatement("insert into nm2companymap(marketid, companycode, featured, accepted) values(?,?,false,false)");
		PreparedStatement pstmt = conn.prepareStatement("update nm2companymap set approved=true where marketid=? and companycode=?");
		
		pstmt.setString(1,marketId);
		pstmt.setString(2,companyCode);

		pstmt.executeUpdate();
	}
	
	public static void removeCompany(Connection conn, String companyCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from Companies where companycode = ?");
		
		pstmt.setString(1,companyCode);

		pstmt.executeUpdate();
	}
	
	public static List<Company> getAcceptedCompaniesByMarketId(Connection conn, String marketId) throws SQLException{
		List<Company> acceptedCompanies = new ArrayList<Company>();
		PreparedStatement pstmt=null;
		ResultSet rs;
		try{
			pstmt = conn.prepareStatement("select c.* from Companies c, nm2companymap m where c.companycode=m.companycode and m.marketid=? and m.approved=true and m.acceptedtill>CURRENT_TIMESTAMP order by c.companycode asc");
			pstmt.setString(1, marketId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Company company = new Company();
				LoadCompanyFromResultSet(rs, company);
				acceptedCompanies.add(company);
			}		
		}finally{
			try{
				pstmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return acceptedCompanies;
	}
	
	public static List<Company> getApprovedButNotJoinedCompaniesByMarketId(Connection conn, String marketId) throws SQLException{
		List<Company> acceptedCompanies = new ArrayList<Company>();
		PreparedStatement pstmt=null;
		ResultSet rs;
		try{
			pstmt = conn.prepareStatement("select c.* from Companies c, nm2companymap m where c.companycode=m.companycode and m.marketid=? and m.approved=true and m.acceptedtill<CURRENT_TIMESTAMP order by c.companycode asc");
			pstmt.setString(1, marketId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Company company = new Company();
				LoadCompanyFromResultSet(rs, company);
				acceptedCompanies.add(company);
			}		
		}finally{
			try{
				pstmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return acceptedCompanies;
	}
	
	public static List<Company> getAllCompanies(Connection conn) throws SQLException{
		List<Company> companies = new ArrayList<Company>();
		PreparedStatement pstmt=null;
		ResultSet rs;
		try{
			pstmt = conn.prepareStatement("select * from Companies");
			rs = pstmt.executeQuery();
			while(rs.next()){
				Company company = new Company();
				LoadCompanyFromResultSet(rs, company);
				companies.add(company);
			}		
		}finally{
			try{
				pstmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return companies;
	}

	public static List<Company> getNewCompaniesByMarketId(Connection conn, String marketId) throws SQLException{
		List<Company> newCompanies = new ArrayList<Company>();
		PreparedStatement pstmt=null;
		ResultSet rs;
		try{
			pstmt = conn.prepareStatement("select c.* from Companies c, nm2companymap m where c.companycode=m.companycode and m.marketid=? and approved=false order by c.companycode asc");
			pstmt.setString(1, marketId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Company company = new Company();
				LoadCompanyFromResultSet(rs, company);
				newCompanies.add(company);
			}		
		}finally{
			try{
				pstmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return newCompanies;
	}
	
	public static boolean isCompanyFeaturedForNM(Connection conn, String marketId, String companyCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select * from nm2companymap where marketId=? and companyCode=? and approved=true and acceptedtill>CURRENT_TIMESTAMP and featuredtill>CURRENT_TIMESTAMP");
		ResultSet rs;
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, companyCode);
			rs = pstmt.executeQuery();
			while(rs.next()){
				return true;
			}
		}finally{
			pstmt.close();
		}
		return false;
	}
	
	public static Date companyFeaturedInNMTill(Connection conn, String marketId, String companyCode) throws SQLException{
		Date date = new Date();
		PreparedStatement pstmt = conn.prepareStatement("select * from nm2companymap where marketId=? and companyCode=?");
		ResultSet rs;
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, companyCode);
			rs = pstmt.executeQuery();
			if(rs.next()){
				java.sql.Timestamp t = rs.getTimestamp("featuredtill");
				if(t != null){
					date = new java.util.Date(t.getTime());
				}else{
					date = Calendar.getInstance().getTime();
				}
			}else{
				date = Calendar.getInstance().getTime();
			}
			return date;
		}finally{
			pstmt.close();
		}
	}
	
	public static Date companyAcceptedInNMTill(Connection conn, String marketId, String companyCode) throws SQLException{
		Date date = new Date();
		PreparedStatement pstmt = conn.prepareStatement("select * from nm2companymap where marketId=? and companyCode=?");
		ResultSet rs;
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, companyCode);
			rs = pstmt.executeQuery();
			if(rs.next()){
				java.sql.Timestamp t = rs.getTimestamp("acceptedtill");
				if(t != null){
					date = new java.util.Date(t.getTime());
				}else{
					date = Calendar.getInstance().getTime();
				}
			}else{
				date = Calendar.getInstance().getTime();
			}
			return date;
		}finally{
			pstmt.close();
		}
	}
	
	public static boolean isCompanyApprovedByNM(Connection conn, String marketId, String companyCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select * from nm2companymap where marketId=? and companyCode=? and approved=true");
		ResultSet rs;
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, companyCode);
			rs = pstmt.executeQuery();
			while(rs.next()){
				return true;
			}
		}finally{
			pstmt.close();
		}
		return false;
	}
	
	public static boolean isCompanyAcceptedByNM(Connection conn, String marketId, String companyCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select * from nm2companymap where marketId=? and companyCode=? and approved=true and acceptedtill>CURRENT_TIMESTAMP");
		ResultSet rs;
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, companyCode);
			rs = pstmt.executeQuery();
			while(rs.next()){
				return true;
			}
		}finally{
			pstmt.close();
		}
		return false;
	}

	public static boolean addCompany(Connection conn, String marketId, String companyCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update nm2companymap set acceptedtill=date_add(CURRENT_TIMESTAMP, INTERVAL 1 year) where marketid=? and companycode=? and approved=true");
		pstmt.setString(1, marketId);
		pstmt.setString(2, companyCode);
		try{
			return pstmt.execute();
		}finally{
			pstmt.close();
		}
	}

	public static boolean featureVendor(Connection conn, String marketId, String companyCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update nm2companymap set featuredtill=date_add(CURRENT_TIMESTAMP,INTERVAL 1 YEAR) where marketid=? and companycode=? and approved=true and acceptedtill>CURRENT_TIMESTAMP");
//		pstmt.setBoolean(1, feature);
		pstmt.setString(1, marketId);
		pstmt.setString(2, companyCode);
		try{
			return pstmt.execute();
		}finally{
			pstmt.close();
		}
	}

	public static boolean unfeatureVendor(Connection conn, String marketId, String companyCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update nm2companymap set featuredtill='2011-01-01' where marketid=? and companycode=? and approved=true and acceptedtill>CURRENT_TIMESTAMP");
		pstmt.setString(1, marketId);
		pstmt.setString(2, companyCode);
		try{
			return pstmt.execute();
		}finally{
			pstmt.close();
		}
	}

	public static boolean isValidVendor(String userId, String sType){
		int type=0;
		try{
			type = Integer.parseInt(sType);
		}catch(Exception e){
			type=-1;
		}
		if(userId!=null && userId.length()>0 && (Constants.VENDOR==type)){
			return true;
		}
		return false;
	}
	

	public static void removeAssociation(Connection conn, String marketId, String companyCode) throws SQLException{
		
		PreparedStatement pstmt = conn.prepareStatement("delete from nm2companymap where marketid=? and companycode=?");
		PreparedStatement pstmt1 = conn.prepareStatement("delete from FeaturedProducts where marketid=? and companycode=?");
		
		pstmt.setString(1,marketId);
		pstmt.setString(2,companyCode);
		
		pstmt1.setString(1,marketId);
		pstmt1.setString(2,companyCode);
	
		try{
			pstmt.execute();
			pstmt1.execute();
		}finally{
			pstmt.close();
		}
	}
	// NMMS Changes [Registration and Login] : March 2010 - end
	
	private static Hashtable<String, Company> m_cachedCompanies = null;
	
	public static void ExpiresCompaniesCache()
	{
		m_cachedCompanies = null;
	}
	
	// NMMS Changes [Registration and Login] : March 2010
	// public static Company getCachedCompanyByCode(Connection conn, String marketId, String companyCode) throws SQLException
	public static Company getCachedCompanyByCode(Connection conn, String companyCode) throws SQLException
	{
		if(m_cachedCompanies == null)
			m_cachedCompanies = new Hashtable<String, Company>();
			
		Company company = m_cachedCompanies.get(companyCode);
		if(company == null)
		{
			// NMMS Changes [Registration and Login] : March 2010
			//company = getCompanyByCode(conn, marketId, companyCode);
			company = getCompanyByCode(conn, companyCode);
			if(company != null)
				m_cachedCompanies.put(companyCode, company);
		}
		
		return company;
	}
	
	public static List<String> getCountryListByMarketId(Connection conn, String marketId) throws SQLException{
		if(marketId != null){
			ArrayList<String> countryList = new ArrayList<String>();
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			try{
				// NMMS Changes [Registration and Login] : March 2010 - begin
				//pstmt = conn.prepareStatement("select distinct country from Companies where marketid = ? and country is not null order by country");
				pstmt = conn.prepareStatement("select distinct C.Country from NetworkMarkets N, nm2companymap M, Companies C where N.MarketID = M.MarketID and M.companycode = C.companycode and N.MarketID = ? and M.approved=true and M.acceptedtill>CURRENT_TIMESTAMP order by C.Country asc");
				pstmt.setString(1, marketId);
				rs = pstmt.executeQuery();
				while(rs.next()){
					countryList.add(rs.getString(1));
				}
			}finally{
				rs.close();
				pstmt.close();
			}
			return countryList;
		}else{
			return null;
		}
	}

	// NMMS Changes [Registration and Login] : March 2010 - begin
	public static void UpdateCompany(Connection conn, String companyCode) throws SQLException
	{
		MasterCompany masterCompany = MasterCompanyFactory.getMasterCompanyByCode(conn, companyCode);
		if(masterCompany!=null){
		
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Companies WHERE CompanyCode = ?");
			pstmt.setString(1, companyCode);
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement("DELETE FROM Products WHERE CompanyCode = ?");
			pstmt.setString(1, companyCode);
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement("DELETE FROM ProductLines WHERE CompanyCode = ?");
			pstmt.setString(1, companyCode);
			pstmt.executeUpdate();
			
			CompanyInfoLoader companyInfoLoader = new CompanyInfoLoader();
			companyInfoLoader.ClearExtras();
			companyInfoLoader.AddExtras( "companycode", companyCode );
			companyInfoLoader.LoadData(conn, masterCompany.companyURL);
			
			for(int i=0;i<masterCompany.productURL.size();i++){
				ProductsLoader productsLoader = new ProductsLoader();
				productsLoader.ClearExtras();
				productsLoader.AddExtras( "companycode", companyCode );
				productsLoader.LoadData(conn, masterCompany.productURL.get(i));
			}
			
			boolean premium = Utils.checkForValidity(MasterCompanyFactory.getMasterCompanyByCode(conn, companyCode).premiumTill);
			if(!premium){
				List<Product> products = ProductFactory.getProducts(conn, companyCode);
				if(products!=null && products.size()>Constants.FREE_CATALOG_PRODUCT_COUNT){
					int count=1;
					for(Product product: products){
						if(count>Constants.FREE_CATALOG_PRODUCT_COUNT){
							ProductFactory.removeProduct(conn, product.CompanyCode, product.Code);
						}else{
							count++;
						}
					}
				}
			}
		}
	}
	// NMMS Changes [Registration and Login] : March 2010 - end
	
	private static void LoadCompanyFromResultSet(ResultSet rs, Company company) throws SQLException
	{
		// NMMS Changes [Registration and Login] : March 2010 - begin
		// company.NetworkMarketID = rs.getString("MarketID");
		company.Address = rs.getString("Address");
		company.City = rs.getString("City");
		company.State = rs.getString("State");
		company.Zip = rs.getString("Zip");
		company.mobileNo = rs.getString("mobileNo");
		// NMMS Changes [Registration and Login] : March 2010 - begin
		company.Code = rs.getString("CompanyCode");
		company.Name = rs.getString("Name");
		company.Description = rs.getString("Description");
		company.HomePageDescription = rs.getString("HomePageDescription");
		company.LogoImage = rs.getString("LogoImage");
		company.Country = rs.getString("Country");
		company.Contact = rs.getString("Contact");
		company.ContactEmail = rs.getString("ContactEmail");
		company.PayPalEmail = rs.getString("PayPalEmail");
		company.GoogleMerchantID = rs.getString("GoogleMerchantID");
		company.TwoCOSID = rs.getString("TwoCOSID");
		company.Header = rs.getString("Header");
		company.Footer = rs.getString("Footer");
		company.HeaderURL = rs.getString("HeaderURL");
		company.FooterURL = rs.getString("FooterURL");
		company.CSS = rs.getString("CSS");
		company.Currency = rs.getString("Currency");
		company.CurrencySymbol = rs.getString("CurrencySymbol");
		company.InquiryURL = rs.getString("InquiryURL");
		company.ThumbnailSize = rs.getString("ThumbnailSize");
		company.TemplateWidth = rs.getString("TemplateWidth");
		company.Template = rs.getString("Template");
		company.Font = rs.getString("Font");
		company.FontSize = rs.getString("FontSize");
		company.Color = rs.getString("Color");
		company.SecondaryColor = rs.getString("SecondaryColor");
		company.TetriaryColor = rs.getString("TetriaryColor");
		company.Background = rs.getString("Background");
		company.Background2 = rs.getString("Background2");
		company.Background3 = rs.getString("Background3");
		company.BackgroundImage = rs.getString("BackgroundImage");
		company.ProductImageSize = rs.getString("ProductImageSize");
		company.ProductURL = rs.getString("ProductURL");
		company.CompanyURL = rs.getString("CompanyURL");
	}

	// NMMS Changes [Registration and Login] : March 2010
	//public static Company getCompanyByCode(Connection conn, String marketId, String companyCode) throws SQLException
	public static Company getCompanyByCode(Connection conn, String companyCode) throws SQLException
	{
		Company company = null;

		// NMMS Changes [Registration and Login] : March 2010
		//PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Companies WHERE MarketID = ? AND CompanyCode = ?" );
		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Companies WHERE CompanyCode = ?" );
		try
		{
			//pstmt.setString(1, marketId);
			pstmt.setString(1, companyCode);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
				{
					company = new Company();

					LoadCompanyFromResultSet(rs, company);
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

		return company;
	}

	public static List<Company> getNetmarketMembers(Connection conn, String marketId) throws SQLException
	{
		ArrayList<Company> companies = new ArrayList<Company>();

		// NMMS Changes [Registration and Login] : March 2010
		//PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Companies WHERE MarketID = ?" );
		PreparedStatement pstmt = conn.prepareStatement( "SELECT c.* FROM Companies c, nm2companymap m WHERE c.companycode=m.companycode and m.marketid=? and m.approved=true and m.acceptedtill>CURRENT_TIMESTAMP" );
		try
		{
			pstmt.setString(1, marketId);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Company company = new Company();

					LoadCompanyFromResultSet(rs, company);
					
					companies.add(company);
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


		return companies;
	}
	
	public static int getNMVendorCount(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select count(*) from nm2companymap where marketid = ? and approved=true and acceptedtill>CURRENT_TIMESTAMP");
		try{
			pstmt.setString(1, marketId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return(rs.getInt(1));
			}
			return 0;
		}finally{
			pstmt.close();
		}
	}
	
	public static boolean VendorExistsWithEmailId(Connection conn, String companyCode, String email) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select * from Companies where companyCode=? and ContactEmail=?");
		try{
			pstmt.setString(1, companyCode);
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
	
	public static List<Company> getNmFeaturedCompanies(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select c.* from Companies c, nm2companymap m " +
				"where c.companycode=m.companycode and m.approved=true and m.acceptedtill>CURRENT_TIMESTAMP and m.featuredtill>CURRENT_TIMESTAMP and m.marketId=?");
		List<Company> companies = new ArrayList<Company>();
		try{
			pstmt.setString(1, marketId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				Company company = new Company();
				LoadCompanyFromResultSet(rs, company);
				companies.add(company);
			}
		}finally{
			pstmt.close();
		}
		return companies;
	}
	
	public static List<Company> getNmNonFeaturedCompanies(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select c.* from Companies c, nm2companymap m " +
				"where c.companycode=m.companycode and m.approved=true and m.acceptedtill>CURRENT_TIMESTAMP and m.featuredtill<CURRENT_TIMESTAMP and m.marketId=?");
		List<Company> companies = new ArrayList<Company>();
		try{
			pstmt.setString(1, marketId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				Company company = new Company();
				LoadCompanyFromResultSet(rs, company);
				companies.add(company);
			}
		}finally{
			pstmt.close();
		}
		return companies;
	}
}
