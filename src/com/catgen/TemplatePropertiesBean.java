package com.catgen;

import java.util.List;
import java.util.Hashtable;
import com.catgen.Product;
import com.catgen.Page;
import com.catgen.Company;
import com.catgen.Category;
import com.catgen.PriceRange;
import java.sql.Connection;

public class TemplatePropertiesBean {
	public CatgenPageContext catgenPageContext;
	public Connection connection;
	public List<Product> networkMarketFeaturedProducts;
	public List<Page> mtPages;
	public List<Company> mtCompanies;
	public List<String> countries;
	public List<Category> categories;
	public List<PriceRange> priceRanges;
	public String backGround;	
	public Hashtable<String, String> trustedCompanies;
	// Style Incorporation to Template change Feb2010
	public Style style;
}
