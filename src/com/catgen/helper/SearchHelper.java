package com.catgen.helper;

import com.catgen.*;
import com.catgen.factories.*;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

public class SearchHelper {
	public static List<Product> findProductsBySearchEntries(Connection conn, CatgenPageContext catgenPageContext){
		try{
			String country = catgenPageContext.searchCountry;
			String category = catgenPageContext.searchCategory;
			String keyword = catgenPageContext.Search;
			int priceCode = catgenPageContext.priceRangeCode;
			String marketId = catgenPageContext.NetworkMarket.NetworkMarketID;
			String companyCode = (catgenPageContext.Company!=null)?catgenPageContext.Company.Code:null;
			
			if(priceCode==0)priceCode=1;
			
			PriceRange priceRange = PriceFactory.getPriceRanges(conn).get(new Integer(priceCode-1));
			int priceG = priceRange.greaterthan;
			int priceL = priceRange.lesserthan;

			ArrayList<KeyValue> parameters = new ArrayList<KeyValue>();

			if(country != null && country.trim().length() > 0)
				parameters.add( new KeyValue("country", country) );
			
			if(keyword != null && keyword.trim().length() > 0)
				parameters.add( new KeyValue("keyword", keyword) );
			
			if(category != null && category.trim().length() > 0)
				parameters.add( new KeyValue("category", category) );

			if(priceCode>1){
				parameters.add( new KeyValue("priceGreaterThan", String.valueOf(priceG)));
				parameters.add( new KeyValue("priceLessThan", String.valueOf(priceL)));
			}
			
			List<Product> products = new ArrayList<Product>();
			if(parameters.size() > 0)
				//products = ProductFactory.SortEqualyByMarkets( ProductFactory.getProductsByParameters(conn, marketId, parameters));
				products = ProductFactory.SortEqualyByMarkets( SuperNmHelper.findProductsWithParametersBySuperMarketId(conn, marketId, parameters, companyCode));
			else if(keyword != null) 
				//products = ProductFactory.SortEqualyByMarkets( ProductFactory.getMarketProducts(conn, marketId, keyword));
				products = SuperNmHelper.findProductsWithKeywordBySuperMarketId(conn, marketId, keyword, companyCode);
				
			List<String> trustedCompanies = MarketMemberFactory.getMarketTrustSealedMembersCodes(conn, marketId);
			
			products = ProductFactory.RanodmizeProducts (products);
			products = ProductFactory.MoveCompaniesProductsUp( trustedCompanies, products );
			products = ProductFactory.getOrderedProducts(products, trustedCompanies);
			
			return products;
		}catch(Exception e){
			e.printStackTrace();
			return new ArrayList<Product>();
		}
	}
}
