package com.catgen.loader;

public class MasterMarketLoader extends DataLoader
{
	public MasterMarketLoader()
	{
		super();

		tableName = "MasterMarkets";

		required.add("marketid");
		required.add("marketsurl");
		required.add("marketinfourl");
		required.add("pagesurl");
		// Categories Changes: Feb 2010
		// required.add("categoriesurl");

		dbColumn.put("network market code", new DataDefinition( "marketid"));
		dbColumn.put("network market members url", new DataDefinition( "marketsurl"));
		dbColumn.put("network market info url", new DataDefinition( "marketinfourl"));
		dbColumn.put("network market pages url", new DataDefinition( "pagesurl"));
		dbColumn.put("network market featured products url", new DataDefinition( "featuredproductsurl"));
		dbColumn.put("description", new DataDefinition( "description"));
		dbColumn.put("network market domain name", new DataDefinition( "domainname"));
		// Categories Changes: Feb 2010
		dbColumn.put("categories url", new DataDefinition("categoriesurl"));
		// Template Stylesheet Change Feb2010
		dbColumn.put("stylesheet url", new DataDefinition("stylesheeturl"));
	}
}
