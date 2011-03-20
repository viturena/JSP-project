package com.catgen.loader;
//Categories Changes: Feb 2010
//New file for Categories Changes
public class CategoriesLoader extends DataLoader {
	
	public CategoriesLoader()
	{
		super();
		
		tableName = "Category";
		IsRowData = true;
		IncludeRowID = false;
		
		required.add("catkey");
		required.add("catvalue");

		dbColumn.put("market id", new DataDefinition( "marketid", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("marketid", new DataDefinition( "marketid", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		
		dbColumn.put("category key", new DataDefinition( "catkey", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("category key", new DataDefinition( "catkey", DataDefinition.STRING, DataDefinition.LOADER_TRIM));

		dbColumn.put("category value", new DataDefinition( "catvalue", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("category value", new DataDefinition( "catvalue", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
	}
}
