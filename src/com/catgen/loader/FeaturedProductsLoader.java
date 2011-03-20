package com.catgen.loader;

public class FeaturedProductsLoader extends DataLoader 
{
	public FeaturedProductsLoader()
	{
		super();
		
		tableName = "FeaturedProducts";
		IsRowData = true;
		IncludeRowID = true;

		required.add("companycode");
		required.add("productcode");
		
		dbColumn.put("company code", new DataDefinition( "companycode", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("vendor code", new DataDefinition( "companycode", DataDefinition.STRING, DataDefinition.LOADER_TRIM));

		dbColumn.put("product code", new DataDefinition( "productcode", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("product", new DataDefinition( "productcode", DataDefinition.STRING, DataDefinition.LOADER_TRIM));

		dbColumn.put("description", new DataDefinition( "description"));
	}	
}
