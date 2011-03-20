package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.catgen.KeyValue;
import com.catgen.Product;
import com.catgen.Utils;

public class ProductFactory 
{
	public static void Save(Product product)
	{

	}

	public static void Load(Product product)
	{

	}

	private static void LoadProductFromResultSet(ResultSet rs, Product product) throws SQLException
	{
		// NMMS Changes [Registration and Login] : March 2010
		//product.NetworkMarketID = rs.getString("MarketID");
		product.CompanyCode = rs.getString("CompanyCode");
		product.Code = rs.getString("Code");
		product.Name = rs.getString("Name");
		product.Price = rs.getString("Price");
		product.Description = rs.getString("Description");
		product.ImageURL = rs.getString("ImageURL");
		product.URL = rs.getString("URL");
		product.Header = rs.getString("Header");
		product.Footer = rs.getString("Footer");
		product.HeaderURL = rs.getString("HeaderURL");
		product.FooterURL = rs.getString("FooterURL");
		product.Currency = rs.getString("Currency");
		product.ProductLine = rs.getString("ProductLine");
		product.Keywords = rs.getString("Keywords");
		product.Quantity = rs.getString("Quantity");
		product.Featured = rs.getString("Featured") == "1";
	}
	
	public static boolean ExistsByName(Connection conn, String marketId, String companyCode, String name) throws SQLException
	{
		boolean result = false;
		
		PreparedStatement pstmt = conn.prepareStatement( "SELECT COUNT(*) FROM Products WHERE MarketID = ? AND CompanyCode = ? AND Name = ? AND Hidden <> 1 " );
		try
		{
			pstmt.setString(1, marketId);
			pstmt.setString(2, companyCode);
			pstmt.setString(3, name);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
					result = rs.getInt(1) > 0;
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
		
		return result;
	}
	
	public static void removeFeaturedProduct(Connection conn, String marketId, String companyCode, String productCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from FeaturedProducts where marketid=? and companycode=? and productcode=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, companyCode);
			pstmt.setString(3, productCode);
		
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	// Product Count Change - begin
	
	public static int getNMProductCount(Connection conn, String marketId) throws SQLException{
		
		int count = 0;
		
		PreparedStatement pstmt = conn.prepareStatement( "select distinct companycode from nm2companymap where marketid = ? and approved=true and acceptedtill>CURRENT_TIMESTAMP" );
		
		try
		{
			pstmt.setString(1, marketId);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
					count += getVendorProductCount(conn,rs.getString("companycode"));
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
		
		return count;
	}
	
	public static int getVendorProductCount(Connection conn, String companyCode) throws SQLException
	{
		int count = 0;
		
		PreparedStatement pstmt = conn.prepareStatement( "select count(*) from Products where companycode = ?" );
		try
		{
			pstmt.setString(1, companyCode);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
					count = rs.getInt(1);
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
		
		return count;
	}
	// Product Count Change - end
	
	public static int getMaxFeaturedProductRowId(Connection conn, String marketId) throws SQLException{
		int count=0;
		PreparedStatement pstmt = conn.prepareStatement("select max(RowID) from FeaturedProducts where marketid=?");
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
	
	public static void insertFeaturedProduct(Connection conn, String marketId, String companyCode, String productCode, int rowId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("insert into FeaturedProducts(marketid, companycode, productcode, rowid) values(?,?,?,?)");
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, companyCode);
			pstmt.setString(3, productCode);
			pstmt.setInt(4, rowId);
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
		}
	}
	
	public static Product getProductByCode(Connection conn, String companyCode, String productCode) throws SQLException
	{
		Product product = null;
		
		if(productCode == null || productCode.trim().length() == 0)
			return product;		

		//PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Products WHERE CompanyCode = ? AND Code = ? AND Hidden <> 1 " );
		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Products WHERE CompanyCode = ? AND Code = ?" );
		try
		{
			pstmt.setString(1, companyCode);
			pstmt.setString(2, productCode);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
				{
					product = new Product();

					LoadProductFromResultSet(rs, product);
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

		return product;
	}

	public static Product getProductByName(Connection conn, String companyCode, String productName) throws SQLException
	{
		Product product = null;
		
		if(productName == null || productName.trim().length() == 0)
			return product;

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Products WHERE CompanyCode = ? AND Name = ? AND Hidden <> 1 " );
		try
		{
			pstmt.setString(1, companyCode);
			pstmt.setString(2, productName);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
				{
					product = new Product();

					LoadProductFromResultSet(rs, product);
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

		return product;
	}
	
	public static List<Product> getProducts(Connection conn, String companyCode) throws SQLException
	{
		ArrayList<Product> products = new ArrayList<Product>();

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Products WHERE CompanyCode = ? ORDER BY code" );
		try
		{
			pstmt.setString(1, companyCode);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Product product = new Product();

					LoadProductFromResultSet(rs, product);
					
					products.add(product);
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

		return products;
	}

//	public static List<Product> getFeaturedProducts(Connection conn, String marketId, String companyCode) throws SQLException
//	{
//		ArrayList<Product> products = new ArrayList<Product>();
//
//		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Products WHERE MarketID = ? AND CompanyCode = ? AND Featured = 'y' AND Hidden <> 1 ORDER BY Name" );
//		try
//		{
//			pstmt.setString(1, marketId);
//			pstmt.setString(2, companyCode);
//
//			ResultSet rs = pstmt.executeQuery();
//			try
//			{
//				while(rs.next())
//				{
//					Product product = new Product();
//
//					LoadProductFromResultSet(rs, product);
//					
//					products.add(product);
//				}
//			}
//			finally
//			{
//				rs.close();
//			}
//		}
//		finally
//		{
//			pstmt.close();
//		}			
//
//		return products;
//	}
	
	public static List<Product> getNetworkMarketOwnFeaturedProducts(Connection conn, String marketId) throws SQLException
	{
		ArrayList<Product> products = new ArrayList<Product>();

		//PreparedStatement pstmt = conn.prepareStatement( "SELECT Products.* FROM Products INNER JOIN FeaturedProducts ON Products.CompanyCode = FeaturedProducts.CompanyCode AND Products.Code = FeaturedProducts.ProductCode WHERE FeaturedProducts.MarketID = ? AND Products.MarketID = ? AND Products.Hidden <> 1 ORDER BY Products.Name" );
		// NMMS Changes [Registration and Login] : March 2010
		PreparedStatement pstmt = conn.prepareStatement( "SELECT Products.* FROM Products INNER JOIN FeaturedProducts ON Products.CompanyCode = FeaturedProducts.CompanyCode AND Products.Code = FeaturedProducts.ProductCode WHERE FeaturedProducts.MarketID = ? ORDER BY Products.Name" );
		try
		{
			pstmt.setString(1, marketId);
			//pstmt.setString(2, marketId);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Product product = new Product();

					LoadProductFromResultSet(rs, product);
					
					products.add(product);
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

		return products;
	}
	
	public static List<Product> getProducts(Connection conn, String marketId, String companyCode, String search) throws SQLException
	{
		ArrayList<Product> products = new ArrayList<Product>();

		if(search.indexOf("%") <0)
			search = "%" + search + "%";
			
		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Products WHERE MarketID = ? AND CompanyCode = ? AND Hidden <> 1 AND (Name LIKE ? OR Description LIKE ? OR Keywords LIKE ?) ORDER BY Name" );
		try
		{
			pstmt.setString(1, marketId);
			pstmt.setString(2, companyCode);
			pstmt.setString(3, search);
			pstmt.setString(4, search);
			pstmt.setString(5, search);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Product product = new Product();

					LoadProductFromResultSet(rs, product);
					
					products.add(product);
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

		return products;
	}

	public static List<Product> getProductsForProductLine(Connection conn, String marketId, String companyCode, String productLine) throws SQLException
	{
		ArrayList<Product> products = new ArrayList<Product>();

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Products WHERE MarketID = ? AND CompanyCode = ? AND ProductLine = ? AND Hidden <> 1 ORDER BY Name" );
		try
		{
			pstmt.setString(1, marketId);
			pstmt.setString(2, companyCode);
			pstmt.setString(3, productLine);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Product product = new Product();

					LoadProductFromResultSet(rs, product);
					
					products.add(product);
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

		return products;
	}
	
	public static List<Product> getProductsByCompanyCodeAndParameters(Connection conn, String companyCode, List<KeyValue> parameters) throws SQLException
	{
		ArrayList<Product> products = new ArrayList<Product>();
		
		String sqlWhere = "";
		
		for(KeyValue parameter: parameters)
		{
			if("keyword".equalsIgnoreCase(parameter.Key))
				sqlWhere += " AND (p.Name LIKE ? OR p.Description LIKE ? OR p.Keywords LIKE ?)";
			else if("category".equalsIgnoreCase(parameter.Key))
				sqlWhere += " AND (p.Name LIKE ? OR p.Description LIKE ? OR p.Keywords LIKE ?)";
			else if("country".equalsIgnoreCase(parameter.Key))
				sqlWhere += " AND c.country = ?";
			else if("pricerange".equalsIgnoreCase(parameter.Key))
				sqlWhere += " AND ? < p.Price AND p.Price < ?";
			else if("priceGreaterThan".equalsIgnoreCase(parameter.Key))
				sqlWhere += " AND p.Price >= ?";
			else if("priceLessThan".equalsIgnoreCase(parameter.Key))
				sqlWhere += " AND p.Price <= ?";
			else
				sqlWhere += " AND " + parameter.Key.replace('\'', ' ') + " = ?"; 
		} 

		PreparedStatement pstmt = conn.prepareStatement( "SELECT p.* FROM Products p, Companies c WHERE p.companyCode = c.companyCode and c.companycode = ? " + sqlWhere);
		try
		{
			pstmt.setString(1, companyCode);
			
			int i = 2; 
			for(KeyValue parameter: parameters)
			{
				if("keyword".equalsIgnoreCase(parameter.Key))
				{
					pstmt.setString(i++, "%" + parameter.Value + "%");
					pstmt.setString(i++, "%" + parameter.Value + "%");
					pstmt.setString(i++, "%" + parameter.Value + "%");
				}
				else if("category".equalsIgnoreCase(parameter.Key))
				{
					pstmt.setString(i++, "%" + parameter.Value + "%");
					pstmt.setString(i++, "%" + parameter.Value + "%");
					pstmt.setString(i++, "%" + parameter.Value + "%");
				}
				else if("country".equalsIgnoreCase(parameter.Key))
				{
					pstmt.setString(i++, parameter.Value);
				}
				else if("pricerange".equalsIgnoreCase(parameter.Key))
				{
					pstmt.setDouble(i++, Utils.StrToDoubleDef(parameter.Value, 0) );
					pstmt.setDouble(i++, Utils.StrToDoubleDef(parameter.ToValue, 0));
				}
				else if("priceGreaterThan".equalsIgnoreCase(parameter.Key))
				{
					pstmt.setDouble(i++, Utils.StrToDoubleDef(parameter.Value, 0) );
				}
				else if("priceLessThan".equalsIgnoreCase(parameter.Key))
				{
					pstmt.setDouble(i++, Utils.StrToDoubleDef(parameter.Value, 0) );
				}
				else
					pstmt.setString(i++, parameter.Value);
			}

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Product product = new Product();

					LoadProductFromResultSet(rs, product);
					
					products.add(product);
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

		return products;
	}
	
	public static List<Product> getProductsByParameters(Connection conn, String marketId, List<KeyValue> parameters) throws SQLException
	{
		ArrayList<Product> products = new ArrayList<Product>();
		
		String sqlWhere = "";
		
		for(KeyValue parameter: parameters)
		{
			if("keyword".equalsIgnoreCase(parameter.Key))
				sqlWhere += " AND (p.Name LIKE ? OR p.Description LIKE ? OR p.Keywords LIKE ?)";
			else if("category".equalsIgnoreCase(parameter.Key))
				sqlWhere += " AND (p.Name LIKE ? OR p.Description LIKE ? OR p.Keywords LIKE ?)";
			else if("country".equalsIgnoreCase(parameter.Key))
				sqlWhere += " AND p.CompanyCode IN (SELECT cc.CompanyCode FROM Companies cc, nm2companymap mm WHERE mm.companycode=cc.companycode and mm.marketid=m.marketid and cc.country = ? and mm.approved=true and mm.acceptedtill>CURENT_TIMESTAMP)";
			else if("pricerange".equalsIgnoreCase(parameter.Key))
				sqlWhere += " AND ? < p.Price AND p.Price < ?";
			else if("priceGreaterThan".equalsIgnoreCase(parameter.Key))
				sqlWhere += " AND p.Price >= ?";
			else if("priceLessThan".equalsIgnoreCase(parameter.Key))
				sqlWhere += " AND p.Price <= ?";
			else
				sqlWhere += " AND " + parameter.Key.replace('\'', ' ') + " = ?"; 
		} 

		PreparedStatement pstmt = conn.prepareStatement( "SELECT p.* FROM Products p, nm2companymap m WHERE m.MarketID = ? and m.companycode=p.companycode and m.approved=true and m.acceptedtill>CURENT_TIMESTAMP" + sqlWhere + " ORDER BY p.Name" );
		try
		{
			pstmt.setString(1, marketId);
			
			int i = 2; 
			for(KeyValue parameter: parameters)
			{
				if("keyword".equalsIgnoreCase(parameter.Key))
				{
					pstmt.setString(i++, "%" + parameter.Value + "%");
					pstmt.setString(i++, "%" + parameter.Value + "%");
					pstmt.setString(i++, "%" + parameter.Value + "%");
				}
				else if("category".equalsIgnoreCase(parameter.Key))
				{
					pstmt.setString(i++, "%" + parameter.Value + "%");
					pstmt.setString(i++, "%" + parameter.Value + "%");
					pstmt.setString(i++, "%" + parameter.Value + "%");
				}
				else if("country".equalsIgnoreCase(parameter.Key))
				{
					pstmt.setString(i++, parameter.Value);
				}
				else if("pricerange".equalsIgnoreCase(parameter.Key))
				{
					pstmt.setDouble(i++, Utils.StrToDoubleDef(parameter.Value, 0) );
					pstmt.setDouble(i++, Utils.StrToDoubleDef(parameter.ToValue, 0));
				}
				else if("priceGreaterThan".equalsIgnoreCase(parameter.Key))
				{
					pstmt.setDouble(i++, Utils.StrToDoubleDef(parameter.Value, 0) );
				}
				else if("priceLessThan".equalsIgnoreCase(parameter.Key))
				{
					pstmt.setDouble(i++, Utils.StrToDoubleDef(parameter.Value, 0) );
				}
				else
					pstmt.setString(i++, parameter.Value);
			}

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Product product = new Product();

					LoadProductFromResultSet(rs, product);
					
					products.add(product);
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

		return products;
	}
	
	public static List<Product> getMarketProducts(Connection conn, String marketId) throws SQLException
	{
		ArrayList<Product> products = new ArrayList<Product>();

		PreparedStatement pstmt = conn.prepareStatement( "SELECT p.* FROM Products p, nm2companymap m WHERE m.companycode = p.companycode and m.MarketID = ? and m.approved=true and m.acceptedtill>CURRENT_TIMESTAMP ORDER BY p.Name" );
		//PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Products WHERE MarketID = ? AND Hidden <> 1 ORDER BY Name" );
		try
		{
			pstmt.setString(1, marketId);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Product product = new Product();

					LoadProductFromResultSet(rs, product);
					
					products.add(product);
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

		return products;
	}
	
	public static List<Product> getMarketFeaturedProducts(Connection conn, String marketId) throws SQLException
	{
		ArrayList<Product> products = new ArrayList<Product>();

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Products WHERE MarketID = ? AND Featured = 'y' AND Hidden <> 1 ORDER BY Name" );
		try
		{
			pstmt.setString(1, marketId);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Product product = new Product();

					LoadProductFromResultSet(rs, product);
					
					products.add(product);
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

		return products;
	}
	
	

	public static List<Product> getCompanyProductsWithMatchingKeyword(Connection conn, String companyCode, String search) throws SQLException{
		ArrayList<Product> products = new ArrayList<Product>();

		if(search.indexOf("%") <0)
			search = "%" + search + "%";
			
		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Products WHERE companycode=? and (Name LIKE ? OR Description LIKE ? OR Keywords LIKE ?)" );
		try
		{
			pstmt.setString(1, companyCode);
			pstmt.setString(2, search);
			pstmt.setString(3, search);
			pstmt.setString(4, search);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Product product = new Product();

					LoadProductFromResultSet(rs, product);
					
					products.add(product);
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

		return products;
	}
	
	

	public static List<Product> getMarketProducts(Connection conn, String marketId, String search) throws SQLException
	{
		ArrayList<Product> products = new ArrayList<Product>();

		if(search.indexOf("%") <0)
			search = "%" + search + "%";
			
		// NMMS Changes [Registration and Login] : March 2010
		//PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Products WHERE MarketID = ? AND (Name LIKE ? OR Description LIKE ? OR Keywords LIKE ?) ORDER BY Name" );
		PreparedStatement pstmt = conn.prepareStatement( "SELECT p.* FROM Products p, nm2companymap m WHERE m.companycode = p.companycode and m.MarketID = ? AND (p.Name LIKE ? OR p.Description LIKE ? OR p.Keywords LIKE ?) and m.approved=true and m.acceptedtill>CURRENT_TIMESTAMP ORDER BY p.Name" );
		try
		{
			pstmt.setString(1, marketId);
			pstmt.setString(2, search);
			pstmt.setString(3, search);
			pstmt.setString(4, search);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Product product = new Product();

					LoadProductFromResultSet(rs, product);
					
					products.add(product);
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

		return products;
	}

	public static List<Product> RanodmizeProducts(List<Product> products)
	{
		return RanodmizeProducts(products, products.size());
	}

	public static List<Product> RanodmizeProducts(List<Product> products, int maxInclude)
	{
		if(products != null)
		{
			Collections.shuffle(products);

			if(maxInclude == 0 || maxInclude == products.size())
				return products;
			else
			{
				ArrayList<Product> randomProducts = new ArrayList<Product>();
				for(int i = 0; i < maxInclude; i++)
				{
					randomProducts.add( products.get(i) );					
				}
				
				return randomProducts;
			}
		}
		else
			return null;
	}
	
	public static List<Product> SortEqualyByMarkets(List<Product> products)
	{
		return SortEqualyByMarkets(products, products.size());
	}

	public static List<Product> MoveCompaniesProductsUp(List<String> companyCodes, List<Product> products)
	{
		ArrayList<Product> sortedProducts = new ArrayList<Product>();
		
		if(products != null)
		{
			if(companyCodes == null || companyCodes.size() == 0)
				return products;

			Hashtable<String, String> companies = new Hashtable<String, String>();
			ArrayList<Product> otherProducts = new ArrayList<Product>();
			
			for(String code: companyCodes)
			{
				companies.put(code, "1");
			}
			
			for(Product product : products)
			{
				if(companies.get(product.CompanyCode) != null )
					sortedProducts.add(product);
				else
					otherProducts.add(product);					
			}
			
			for(Product product : otherProducts)
			{
				sortedProducts.add(product);
			}			
		}
		else
			return products;
		
		return sortedProducts;
	}
	
	public static List<Product> SortEqualyByMarkets(List<Product> products, int maxInclude)
	{
		ArrayList<Product> sortedProducts = new ArrayList<Product>();
		
		if(products != null)
		{
			ArrayList<Integer> usedProducts = new ArrayList<Integer>(products.size());
			for(int j = 0; j < products.size(); j++)
			{
				usedProducts.add(new Integer(0));
			}

			boolean found = true;

			while(found && (maxInclude == 0 || sortedProducts.size() < maxInclude))
			{
				Hashtable<String, String> companies = new Hashtable<String, String>();

				found = false;
				for(int i = 0; i < products.size(); i++)
				{
					if(usedProducts.get(i) == null || usedProducts.get(i) == 0)
					{
						Product product = products.get(i);

						if(companies.get(product.CompanyCode) != null)
							continue;

						sortedProducts.add(product);
						usedProducts.set(i, new Integer(1));
						companies.put(product.CompanyCode, "1");
						found = true;

						if(maxInclude != 0 && sortedProducts.size() >= maxInclude)
							break;
					}
				}
			}
		}

		return sortedProducts;
	}

	public static List<Product> getPagedProducts(List<Product> products, int pageSize, int pageNumber)
	{
		List<Product> resultProducts = products; 
		
		ArrayList<Product> pagedProducts = null;
		
		if(products != null && pageSize > 0)
		{
			if(pageNumber < 1)
				pageNumber = 1;
			
			int l = products.size();
			
			if(pageSize * (pageNumber - 1) < l )
			{
				pagedProducts = new ArrayList<Product>();
				
				int startIndex = pageSize * (pageNumber - 1);
				
				for(int i = 0; i < pageSize; i++ )
				{
					if(l <= startIndex + i)
						break;
					
					pagedProducts.add( products.get(startIndex + i) );
				}
				
				resultProducts = pagedProducts;				
			}
		}

		return resultProducts;
	}
	
	public static List<Product> getOrderedProducts(List<Product> products, List<String> companyCodes){
		List<Product> featuredProducts = new ArrayList<Product>();
		List<Product> nonFeaturedProducts = new ArrayList<Product>();
		List<Product> orderedFeaturedProducts = new ArrayList<Product>();
		List<Product> orderedProducts = new ArrayList<Product>();
		
		for (Product product: products){
			if (companyCodes.contains(product.CompanyCode)){
				featuredProducts.add(product);
			}else{
				nonFeaturedProducts.add(product);
			}
		}
		
		int featuredProductCount = featuredProducts.size();
		
		Hashtable<String, List<Product>> productListArrangedByCompany = new Hashtable<String, List<Product>>();
		for (Product product: featuredProducts){
			List<Product> matchingProdList = productListArrangedByCompany.get(product.CompanyCode);
			if(matchingProdList==null){
				matchingProdList = new ArrayList<Product>();
				productListArrangedByCompany.put(product.CompanyCode, matchingProdList);
			}
			matchingProdList.add(product);
			productListArrangedByCompany.put(product.CompanyCode, matchingProdList);
		}
		
		int pointer = 1;
		int iteration = 1;
		while(pointer <= featuredProductCount){
			for(String companyCode: companyCodes){
				List<Product> matchingProdList = productListArrangedByCompany.get(companyCode);
				if(matchingProdList!=null){
					if(iteration <= matchingProdList.size()){
						Product product = matchingProdList.get(iteration-1);
						orderedFeaturedProducts.add(product);
						pointer++;
					}
				}
			}
			iteration++;
		}
		
		orderedProducts.addAll(orderedFeaturedProducts);
		orderedProducts.addAll(nonFeaturedProducts);
		
		return orderedProducts;
	}
	
	public static List<Product> filterProductsWithNoImage(List<Product> products){
		Iterator<Product> iterator = products.iterator();
		List<Product> filteredProducts = new ArrayList<Product>();
		while (iterator.hasNext()) {
			try{
				Product product = iterator.next();
				if(Utils.fileExists(product.ImageURL)){
			    	filteredProducts.add(product);
			    }
			}catch(Exception e){
			}
		}
		return filteredProducts;
	}
	
	public static void removeCompany(Connection conn, String companyCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from FeaturedProducts where companycode = ?");
		
		pstmt.setString(1,companyCode);

		pstmt.executeUpdate();
		
		PreparedStatement pstmt1 = conn.prepareStatement("delete from Products where companycode = ?");
		
		pstmt1.setString(1,companyCode);

		pstmt1.executeUpdate();
	}
	
	public static void removeNM(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from FeaturedProducts where marketid = ?");
		
		pstmt.setString(1,marketId);

		pstmt.executeUpdate();
	}
	
	public static void removeProduct(Connection conn, String companyCode, String productCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from Products where companycode=? and code=?");
		try{
			pstmt.setString(1, companyCode);
			pstmt.setString(2, productCode);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
}
