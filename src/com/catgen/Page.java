package com.catgen;

import org.w3c.dom.Element;

import com.catgen.BasicDatastore;
import com.catgen.factories.PageFactory;

public class Page extends BasicDatastore {
	  public String NetworkMarketID;
	  public String Parent;
	  public String Code;
	  public String Name;
	  public String Description;
	  public String Type;
	  public String Header;
	  public String Footer;
	  public String HeaderURL;
	  public String FooterURL;
	  public String HeaderData;
	  public String FooterData;
	  public String CSS;
	  public String JavaScript;
	  public String Template;	
	  public int RowID;
	  public boolean Hidden;

	  public void Save() 
	  {
		  PageFactory.Save( this );
	  }
		
	  public void Load() 
	  {
		  PageFactory.Load( this );
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

			if(Code != null)
			{
				e = element.getOwnerDocument().createElement("Code");
				e.appendChild(element.getOwnerDocument().createTextNode(Code));
				element.appendChild(e);
			}
		}
}
