package com.catgen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MasterCompany 
{
	public String companyCode;
	public String companyURL;
	public List<String> productURL;
	public Date premiumTill;
	
	public MasterCompany(){
		this.productURL = new ArrayList<String>();
	}
}
