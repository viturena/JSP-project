package com.catgen.loader;

public class PagesLoader extends DataLoader {
	
	public PagesLoader()
	{
		super();
		
		tableName = "Pages";
		IncludeRowID = true;

		required.add("name");
		
		dbColumn.put("code", new DataDefinition( "code"));
		dbColumn.put("company code", new DataDefinition( "code"));
		
		dbColumn.put("code", new DataDefinition("code" ));
		dbColumn.put("page code",new DataDefinition("code"));
				
		dbColumn.put("name", new DataDefinition( "name"));
		dbColumn.put("page name", new DataDefinition( "name"));
		dbColumn.put("page", new DataDefinition( "name"));
				
		dbColumn.put("descr", new DataDefinition( "description"));
		dbColumn.put("description", new DataDefinition( "description"));
		dbColumn.put("page description", new DataDefinition( "description"));
				
		dbColumn.put("header", new DataDefinition( "header"));
		dbColumn.put("text", new DataDefinition( "header"));
		dbColumn.put("page text", new DataDefinition( "header"));
				
		dbColumn.put("footer", new DataDefinition( "footer"));
				
		ExternalHTTPLoader headerLoader = new ExternalHTTPLoader("headerdata");
		
		dbColumn.put("headerurl", new DataDefinition( "headerurl", DataDefinition.STRING, 0, headerLoader));
		dbColumn.put("header url", new DataDefinition( "headerurl", DataDefinition.STRING, 0, headerLoader));
		dbColumn.put("upper content url", new DataDefinition( "headerurl", DataDefinition.STRING, 0, headerLoader));

		dbColumn.put("headerdata", new DataDefinition( "headerdata"));

		ExternalHTTPLoader footerLoader = new ExternalHTTPLoader("footerdata");
		
		dbColumn.put("footerurl", new DataDefinition( "footerurl", DataDefinition.STRING, 0, footerLoader));
		dbColumn.put("footer url", new DataDefinition( "footerurl", DataDefinition.STRING, 0, footerLoader));
		dbColumn.put("lower content url", new DataDefinition( "footerurl", DataDefinition.STRING, 0, footerLoader));
		
		dbColumn.put("footerdata", new DataDefinition( "footerdata"));
				
		dbColumn.put("css", new DataDefinition( "css"));
				
		dbColumn.put("javascript", new DataDefinition( "javascript"));
				
		dbColumn.put("invisible", new DataDefinition( "invisible"));
				
		dbColumn.put("rate", new DataDefinition( "rate"));
				
		dbColumn.put("page type", new DataDefinition( "type"));
		dbColumn.put("type", new DataDefinition( "type"));
				
		dbColumn.put("page template", new DataDefinition( "template"));
		dbColumn.put("template", new DataDefinition( "template"));
	}

}