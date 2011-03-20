package com.catgen;

import org.w3c.dom.Element;

import com.catgen.factories.ProductFactory;

public class Product extends BasicDatastore {
	// NMMS Changes [Registration and Login] : March 2010
	// public String NetworkMarketID;
	public String CompanyCode;
	public String Code;
	public String Name;
	public String Price;
	public String Description;
	public String ImageURL;
	public String URL;
	public String Header;
	public String Footer;
	public String HeaderURL;
	public String FooterURL;
	public String Currency;
	public String ProductLine;
	public String Keywords;
	public String Quantity;
	public boolean Featured;
	
	public void Save() 
	{
		ProductFactory.Save( this );
	}

	public void Load() 
	{
		ProductFactory.Load( this );
	}

	public void AddToXml(Element element)
	{
		Element e;
		
		if(Name != null)
		{
			e = element.getOwnerDocument().createElement("Name");
			e.appendChild(element.getOwnerDocument().createTextNode(Name));
			element.appendChild(e);
		}

		if(CompanyCode != null)
		{
			e = element.getOwnerDocument().createElement("CompanyCode");
			e.appendChild(element.getOwnerDocument().createTextNode(CompanyCode));
			element.appendChild(e);
		}
		
		if(Description != null)
		{
			e = element.getOwnerDocument().createElement("Description");
			e.appendChild(element.getOwnerDocument().createTextNode(Description));
			element.appendChild(e);
		}

		if(ImageURL != null)
		{
			e = element.getOwnerDocument().createElement("ImageURL");
			e.appendChild(element.getOwnerDocument().createTextNode(ImageURL));
			element.appendChild(e);
		}

		if(Code != null)
		{
			e = element.getOwnerDocument().createElement("Code");
			e.appendChild(element.getOwnerDocument().createTextNode(Code));
			element.appendChild(e);
		}

		if(Price != null)
		{
			e = element.getOwnerDocument().createElement("Price");
			e.appendChild(element.getOwnerDocument().createTextNode(Price));
			element.appendChild(e);
		}

		if(Currency != null)
		{
			e = element.getOwnerDocument().createElement("Currency");
			e.appendChild(element.getOwnerDocument().createTextNode(Currency));
			element.appendChild(e);
		}
	}
}
