package com.catgen.loader;
//NMMS Changes [Registration and Login] : March 2010

public class MasterCompanyLoader extends DataLoader{
	
	public MasterCompanyLoader(){
		super();
		tableName = "mastercompanies";

		required.add("companycode");
		required.add("sskey");
		
		dbColumn.put("company code", new DataDefinition("companycode"));
		dbColumn.put("spreadsheet key", new DataDefinition("sskey"));
	}
}
