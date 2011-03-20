package com.catgen.loader;
public class ProductsLoader extends DataLoader {
	
	public ProductsLoader()
	{
		super();
		IsRowData = true;
		IncludeRowID = true;
		
		tableName = "Products";

		required.add("code");
		required.add("name");
		
		dbColumn.put("code", new DataDefinition( "code", DataDefinition.STRING, DataDefinition.LOADER_TRIM ));
		dbColumn.put("product code", new DataDefinition( "code", DataDefinition.STRING, DataDefinition.LOADER_TRIM));

		dbColumn.put("name", new DataDefinition( "name", DataDefinition.STRING, DataDefinition.LOADER_TRIM ));
		dbColumn.put("product name", new DataDefinition( "name", DataDefinition.STRING, DataDefinition.LOADER_TRIM ));
		dbColumn.put("product", new DataDefinition( "name", DataDefinition.STRING, DataDefinition.LOADER_TRIM));

		dbColumn.put("price", new DataDefinition( "price", DataDefinition.DOUBLE, 0));
		dbColumn.put("product price", new DataDefinition( "price", DataDefinition.DOUBLE, 0));

		dbColumn.put("descr", new DataDefinition( "description"));
		dbColumn.put("description", new DataDefinition( "description"));
		dbColumn.put("product description", new DataDefinition( "description"));

		dbColumn.put("image", new DataDefinition( "imageurl"));
		dbColumn.put("image url", new DataDefinition( "imageurl"));
		dbColumn.put("imageurl", new DataDefinition( "imageurl"));
		dbColumn.put("product image", new DataDefinition( "imageurl"));
				

		dbColumn.put("url", new DataDefinition( "url"));
		dbColumn.put("product url", new DataDefinition( "url"));
		dbColumn.put("producturl", new DataDefinition( "url"));
		dbColumn.put("external product url", new DataDefinition( "url"));
		dbColumn.put("url for \"more >>\"", new DataDefinition( "url"));		

		dbColumn.put("header", new DataDefinition( "header"));

		dbColumn.put("footer", new DataDefinition( "footer"));

		dbColumn.put("headerurl", new DataDefinition( "headerurl"));
		dbColumn.put("header url", new DataDefinition( "headerurl"));

		dbColumn.put("footerurl", new DataDefinition( "footerurl"));
		dbColumn.put("footer url", new DataDefinition( "footerurl"));

		dbColumn.put("featured", new DataDefinition( "featured"));

		dbColumn.put("hidden", new DataDefinition( "hidden", DataDefinition.INT, DataDefinition.YESNO_AS_INT));
		dbColumn.put("invisible", new DataDefinition( "hidden", DataDefinition.INT, DataDefinition.YESNO_AS_INT));

		dbColumn.put("rate", new DataDefinition( "ordernum"));
		dbColumn.put("order", new DataDefinition( "ordernum"));

		dbColumn.put("currency", new DataDefinition( "currency"));

		dbColumn.put("product line", new DataDefinition( "productline", DataDefinition.STRING, DataDefinition.LOADER_TRIM | DataDefinition.DEFAULT_IF_FIRST_AND_ONLY | DataDefinition.USE_DEFAULT_IF_EMPTY | DataDefinition.ALWAYS_INCLUDE ));
		dbColumn.put("line", new DataDefinition( "productline", DataDefinition.STRING, DataDefinition.LOADER_TRIM | DataDefinition.DEFAULT_IF_FIRST_AND_ONLY | DataDefinition.USE_DEFAULT_IF_EMPTY | DataDefinition.ALWAYS_INCLUDE ));
		dbColumn.put("category", new DataDefinition( "productline", DataDefinition.STRING, DataDefinition.LOADER_TRIM | DataDefinition.DEFAULT_IF_FIRST_AND_ONLY | DataDefinition.USE_DEFAULT_IF_EMPTY | DataDefinition.ALWAYS_INCLUDE ));

		dbColumn.put("keywords", new DataDefinition( "keywords"));
		dbColumn.put("key words", new DataDefinition( "keywords"));

		dbColumn.put("quantity", new DataDefinition( "quantity"));
		
		extraRowLoader = new ExtraRowProductLineLoader();
	}
}