package com.catgen;

import com.catgen.TemplatePropertiesBean;
import com.catgen.factories.*;
import com.catgen.helper.CategoryHelper;
import com.catgen.helper.SuperNmHelper;

import java.sql.Connection;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Hashtable;
import java.util.Map;

public class TemplateInitializer {
	
	public static TemplatePropertiesBean initializeTemplateProperties(CatgenPageContext catgenContext, Connection conn, String id) {
		NetworkMarket networkMarket = catgenContext.NetworkMarket;
		TemplatePropertiesBean templatePropertiesBean = new TemplatePropertiesBean();
		templatePropertiesBean.connection = conn;
		if (networkMarket!=null){
			try{
				//templatePropertiesBean.networkMarketFeaturedProducts = ProductFactory.RanodmizeProducts( ProductFactory.getNetworkMarketOwnFeaturedProducts(conn, networkMarket.NetworkMarketID) );
				templatePropertiesBean.mtPages = PageFactory.getPagesOrderByRow(conn, networkMarket.NetworkMarketID);
				//templatePropertiesBean.mtCompanies = CompanyFactory.getNetmarketMembers(conn, networkMarket.NetworkMarketID);
				templatePropertiesBean.mtCompanies = SuperNmHelper.findAllContainedCompaniesBySuperMarketId(conn, networkMarket.NetworkMarketID);
				//templatePropertiesBean.countries = CompanyFactory.getCountryListByMarketId(conn, networkMarket.NetworkMarketID);
				templatePropertiesBean.countries = SuperNmHelper.getCountryListBySuperMarketId(conn, networkMarket.NetworkMarketID);
				
				//multi level category change
				//templatePropertiesBean.categories = CategoryFactory.getCategories(conn,id);
				templatePropertiesBean.categories = CategoryHelper.getXmlSubCategoryListByCategoryKey(conn, id, catgenContext.searchCategory);
				
				templatePropertiesBean.priceRanges = PriceFactory.getPriceRanges(conn);
				String bgColor = StringEscapeUtils.escapeHtml(networkMarket.Background);
				String bgImage = StringEscapeUtils.escapeHtml(networkMarket.BackgroundImage);
				templatePropertiesBean.backGround = "bgcolor=\"gray\"";
				
				if(bgImage!=null && bgImage.length()>0)
					templatePropertiesBean.backGround = "background=\""+bgImage+"\"";
				else if(bgColor!=null && bgColor.length()>0)
					templatePropertiesBean.backGround = "bgcolor=\""+bgColor+"\"";
			
				templatePropertiesBean.catgenPageContext = catgenContext;
				networkMarket.LogoImage = (networkMarket.LogoImage!=null)?networkMarket.LogoImage:"";
				networkMarket.Header = (networkMarket.Header!=null)?networkMarket.Header:"";
				networkMarket.Background2 = (networkMarket.Background2!=null && networkMarket.Background2.length()>0)?networkMarket.Background2:"white";
				templatePropertiesBean.catgenPageContext.NetworkMarket = networkMarket;
				
				List<String> trustedCompanies = MarketMemberFactory.getMarketTrustSealedMembersCodes( conn, networkMarket.NetworkMarketID );
				templatePropertiesBean.trustedCompanies = new Hashtable<String, String>();
				for(String companyCode: trustedCompanies){
					templatePropertiesBean.trustedCompanies.put(companyCode, "1");
				}
				if("Home".equalsIgnoreCase(catgenContext.PageName)){
					List<Product> products = ProductFactory.getNetworkMarketOwnFeaturedProducts(conn, networkMarket.NetworkMarketID);
					
					boolean lazy = NetMarketFactory.getNetworkMarketByCode(conn, networkMarket.NetworkMarketID).lazy;
					if(lazy){
						List<Product> allProducts = ProductFactory.RanodmizeProducts(ProductFactory.getMarketProducts(conn, networkMarket.NetworkMarketID));
						Map<String, String> map = new HashMap<String,String>();
						for(Product product:allProducts){
							if(map.get(product.CompanyCode)==null){
								products.add(product);
								map.put(product.CompanyCode, "1");
							}
						}
					}
					products = ProductFactory.RanodmizeProducts(products);
					
					// change to filter out products with no or invalid image url
					// products = ProductFactory.filterProductsWithNoImage(products);
					
					products = ProductFactory.getOrderedProducts(products, trustedCompanies);
					templatePropertiesBean.networkMarketFeaturedProducts = products;
				}
				// Style Incorporation to Template change Feb2010
				templatePropertiesBean.style = StyleFactory.getStyle(conn, networkMarket.NetworkMarketID);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return templatePropertiesBean;
	}
}
