package com.catgen;

import org.w3c.dom.Element;

import com.catgen.BasicDatastore;
import com.catgen.factories.CompanyFactory;

public class Company extends BasicDatastore  {
	// public String NetworkMarketID;
	public String Code;
	public String Name;
	public String Description;
	public String HomePageDescription;
	public String LogoImage;
	public String Address;
	public String Address2;
	public String City;
	public String State;
	public String Zip;
	public String Country;
	public String Contact;
	public String ContactEmail;
	public String PayPalEmail;
	public String GoogleMerchantID;
	public String TwoCOSID;
	public String Header;
	public String Footer;
	public String HeaderURL;
	public String FooterURL;
	public String CSS;
	public String Currency;
	public String CurrencySymbol;
	public String InquiryURL;
	public String ThumbnailSize;
	public String TemplateWidth;
	public String Template;
	public String Font;
	public String FontSize;
	public String Color;
	public String SecondaryColor;
	public String TetriaryColor;
	public String Background;
	public String Background2;
	public String Background3;
	public String BackgroundImage;
	public String ProductImageSize;
	public String ProductURL;
	public String CompanyURL;
	// NMMS Changes [Registration and Login] : March 2010
	public String mobileNo;

	public void Save() 
	{
		CompanyFactory.Save( this );
	}

	public void Load() 
	{
		CompanyFactory.Load( this );
	}

	public void AddToXml(Element element)
	{
		Element e;
		
		if(Name != null)
		{
			e = element.getOwnerDocument().createElement("CompanyName");
			e.appendChild(element.getOwnerDocument().createTextNode(Name));
			element.appendChild(e);
		}

		if(Description != null)
		{
			e = element.getOwnerDocument().createElement("Description");
			e.appendChild(element.getOwnerDocument().createTextNode(Description));
			element.appendChild(e);
		}

		if(LogoImage != null)
		{
			e = element.getOwnerDocument().createElement("LogoImage");
			e.appendChild(element.getOwnerDocument().createTextNode(LogoImage));
			element.appendChild(e);
		}

		if(Code != null)
		{
			e = element.getOwnerDocument().createElement("Code");
			e.appendChild(element.getOwnerDocument().createTextNode(Code));
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
