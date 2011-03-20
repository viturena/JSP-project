package com.catgen.mobile;

import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;

import com.catgen.*;

import java.util.*;
import java.io.IOException;
import java.net.URL;

import java.util.Comparator;


public class MobileHelper 
{
	public static String getMessageStringFromCode(int code)
	{
		switch(code)
		{
		case 200:
			return "Successully Updated.";
		case 404:
			return "Not Found.";
		case 406:
			return "Not Acceptable.";
		default:
			return "Unknow Result.";
		}		
	}
	
	public static String getSpreadsheetName(String key) throws IOException, ServiceException
	{
		String name = null;
		
		SpreadsheetService service = new SpreadsheetService("openentry-mobile-1");
		service.setUserCredentials("test@gmail.com", "test1");

		URL url = new URL("http://spreadsheets.google.com/feeds/spreadsheets/private/full/"  + key );

		SpreadsheetEntry spreadsheetEntry = service.getEntry(url, SpreadsheetEntry.class);
		
		name = spreadsheetEntry.getTitle().getPlainText();

		return name;
	}
	
	public static Company getCompany(String key, String worksheetID) throws IOException, ServiceException
	{
		if(key == null || worksheetID == null)
			return null;
		
		Company company = null;	
		
		
		return company;
	}
	
	
	public static List<Product> getProducts(String key, String worksheetID) throws IOException, ServiceException
	{
		if(key == null || worksheetID == null)
			return null;
			
		ArrayList<Product> products = new ArrayList<Product>(); 
		
		SpreadsheetService service = new SpreadsheetService("openentry-mobile-1");
		service.setUserCredentials("openentrym@gmail.com", "Surendra");

		URL url = new URL("http://spreadsheets.google.com/feeds/list/" + key + "/" + worksheetID + "/private/full");

		ListFeed feed = service.getFeed(url, ListFeed.class);
		for (ListEntry entry : feed.getEntries()) 
		{
			String productCode = null;
			String productName = null;
			
			for (String tag : entry.getCustomElements().getTags()) 
			{
				if("Code".equalsIgnoreCase(tag.trim()) || "Code *".equalsIgnoreCase(tag.trim()) )
				{
					productCode = entry.getCustomElements().getValue(tag);
				}
				
				if("Name".equalsIgnoreCase(tag.trim()) || "Name *".equalsIgnoreCase(tag.trim()) )
				{
					productName = entry.getCustomElements().getValue(tag);
				}		    
			}
			
			if(productCode != null && productName != null)
			{
				Product product = new Product();
				
				product.Code = productCode;
				product.Name = productName;
				
				products.add(product);
			}
		}
		
		Collections.sort(products, new ProductCodeComparator() );
		
		return products;
	}

	public static int UpdateProduct(String key, String worksheetID, String productCode, double price, int quantity) throws IOException, ServiceException
	{
		if((price == 0 && quantity == 0) || productCode == null || productCode.trim().length() == 0 )
			return 406;
		
		SpreadsheetService service = new SpreadsheetService("openentry-mobile-1");
		service.setUserCredentials("openentrym@gmail.com", "Surendra");

		URL url = new URL("http://spreadsheets.google.com/feeds/list/" + key + "/" + worksheetID + "/private/full");

		ListFeed feed = service.getFeed(url, ListFeed.class);
		for (ListEntry entry : feed.getEntries()) 
		{
			for (String tag : entry.getCustomElements().getTags()) 
			{
				if("Code".equalsIgnoreCase(tag.trim()) || "Code *".equalsIgnoreCase(tag.trim()) )
				{
					String value = entry.getCustomElements().getValue(tag);
					if(productCode.equalsIgnoreCase(value))
					{
						boolean doUpdate = false;
						if(price != 0)
						{
							try
							{							
								entry.getCustomElements().setValueLocal("Price", "" + price);
								doUpdate = true;
							}
							catch(Exception e)
							{}
						}
						
						if(quantity >= 0)
						{
							try
							{							
								entry.getCustomElements().setValueLocal("Quantity", "" + quantity);
								doUpdate = true;
							}
							catch(Exception e)
							{}
						}
						
						if(doUpdate)
						{
							entry.update();
							return 200;
						}
					}
				}		    
			}
		}

		return 404;
	}
	
	
}

class ProductCodeComparator implements Comparator<Product> {

    // Comparator interface requires defining compare method.
    public int compare(Product producta, Product productb) {
        if (producta == null || producta.Code == null)
        {
            return -1;
        } else if (productb == null || productb.Code == null) 
        {
            return 1;

        } else {
            return producta.Code.compareToIgnoreCase(productb.Code);
        }
    }
}
