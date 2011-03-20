package com.catgen.loader;
import java.sql.Connection;
import java.util.ArrayList;

public class MembersLoader extends DataLoader
{
	private ProductsLoader productsLoader;
	private CompanyInfoLoader companyInfoLoader;
	private PagesLoader pagesLoader; 

	public String MarketID;
	
	public MembersLoader()
	{
		super();
//		Commented for NMMS
//		productsLoader = new ProductsLoader();
//		companyInfoLoader = new CompanyInfoLoader();
//		pagesLoader = new PagesLoader(); 
//		tableName = "MarketMembers";
		
		tableName = "nm2companymap";

		required.add("companycode");
//		required.add("productsurl");

		dbColumn.put("code", new DataDefinition( "companycode"));
		dbColumn.put("company code", new DataDefinition( "companycode"));
		dbColumn.put("vendor code", new DataDefinition( "companycode"));

//		dbColumn.put("name", new DataDefinition( "name"));
//		dbColumn.put("company name", new DataDefinition( "name"));
//		dbColumn.put("company", new DataDefinition( "name"));
//
//		dbColumn.put("descr", new DataDefinition( "description"));
//		dbColumn.put("description", new DataDefinition( "description"));
//		dbColumn.put("company description", new DataDefinition( "description"));
//
//		dbColumn.put("logo url", new DataDefinition( "logo" ));
//		dbColumn.put("image", new DataDefinition( "logo"));
//		dbColumn.put("image url", new DataDefinition( "logo"));
//		dbColumn.put("imageurl", new DataDefinition( "logo"));
//		dbColumn.put("company image", new DataDefinition( "logo"));
//
//		dbColumn.put("url", new DataDefinition( "url"));
//		dbColumn.put("company url", new DataDefinition( "url"));
//
//		dbColumn.put("headerurl", new DataDefinition( "headerurl"));
//		dbColumn.put("header url", new DataDefinition( "headerurl"));
//		dbColumn.put("company header url", new DataDefinition( "headerurl"));
//
//		dbColumn.put("footerurl", new DataDefinition( "footerurl"));
//		dbColumn.put("footer url", new DataDefinition( "footerurl"));
//		dbColumn.put("company footer url", new DataDefinition( "footerurl" ));
//
//		dbColumn.put("address", new DataDefinition( "address"));
//		dbColumn.put("company address", new DataDefinition( "address"));
//
//		dbColumn.put("address2", new DataDefinition( "address2"));
//		dbColumn.put("company address2", new DataDefinition( "address2"));
//
//		dbColumn.put("city", new DataDefinition( "city"));
//		dbColumn.put("company city", new DataDefinition( "city"));
//
//		dbColumn.put("state", new DataDefinition( "state"));
//		dbColumn.put("company state", new DataDefinition( "state"));
//
//		dbColumn.put("country", new DataDefinition( "country"));
//		dbColumn.put("company country", new DataDefinition( "country"));
//
//		dbColumn.put("zip", new DataDefinition( "zip"));
//		dbColumn.put("country zip", new DataDefinition( "zip"));
//
//		dbColumn.put("header", new DataDefinition( "header"));
//		dbColumn.put("company header", new DataDefinition( "header"));
//
//		dbColumn.put("footer", new DataDefinition( "footer"));
//		dbColumn.put("company footer", new DataDefinition( "footer"));
//
//		dbColumn.put("rate", new DataDefinition( "rate"));
//		dbColumn.put("order", new DataDefinition( "rate"));
//
//		dbColumn.put("products url", new DataDefinition( "productsurl"));
//		dbColumn.put("product sheet url", new DataDefinition( "productsurl"));
//
//		dbColumn.put("company info url", new DataDefinition( "companyinfourl"));
//		dbColumn.put("company sheet url", new DataDefinition( "companyinfourl"));
//		
//		dbColumn.put("order", new DataDefinition( "rate"));
//		
//		dbColumn.put("cts", new DataDefinition( "trustseal", DataDefinition.INT, DataDefinition.YESNO_AS_INT ));
	}

	protected void AfterSave(Connection conn, ArrayList<SpreadsheetData> sheetData)
	{
		String companyCode = null;
		for(int i = 0; i < sheetData.size(); i++)
		{
			SpreadsheetData data = sheetData.get(i);

			DataDefinition h = header.get( data.Column );
			if(h != null && h.Name != null)
			{
				if(h.Name.equalsIgnoreCase("companycode"))
					companyCode = data.value;
			}
		}

		if(companyCode == null || MarketID == null)
			return;

		for(int i = 0; i < sheetData.size(); i++)
		{
			SpreadsheetData data = sheetData.get(i);

			DataDefinition h = header.get( data.Column );
			if(h != null && h.Name != null)
			{
				if(h.Name.equalsIgnoreCase("productsurl"))
				{
					productsLoader.ClearExtras();
					productsLoader.AddExtras( "MarketID", MarketID );
					productsLoader.AddExtras( "CompanyCode", companyCode );
					productsLoader.LoadData(conn, data.value);
				}
				else if(h.Name.equalsIgnoreCase("companyinfourl"))
				{
					companyInfoLoader.ClearExtras();
					companyInfoLoader.AddExtras( "MarketID", MarketID );
					companyInfoLoader.AddExtras( "CompanyCode", companyCode );
					companyInfoLoader.LoadData(conn, data.value);
				}
				else if(h.Name.equalsIgnoreCase("pagesurl"))
				{
					pagesLoader.ClearExtras();
					pagesLoader.AddExtras( "MarketID", MarketID );
					pagesLoader.AddExtras( "CompanyCode", companyCode );
					pagesLoader.LoadData(conn, data.value);
				}
			}
		}
	}
}
