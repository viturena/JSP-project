package com.catgen.loader;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;

public class ExternalHTTPLoader implements IExtraDataLoader {

	private String m_header;
	
	public ExternalHTTPLoader(String header)
	{
		this.m_header = header;
	}

	public String Load(String value) 
	{
		String result = null;
		
		try
		{
			HttpClient client = new HttpClient();
			HttpMethod method = new GetMethod(value);
			try
			{
				int statusCode = client.executeMethod(method);
				if (statusCode == HttpStatus.SC_OK)
				{
					result = method.getResponseBodyAsString();
					
					if(value.indexOf("docs.google.com") > 0)
						result = com.catgen.Utils.StripGoogleDocs(result);
					else if(value.indexOf("sites.google.com") > 0)
						result = com.catgen.Utils.StripGoogleSites(result);
					else
						result = com.catgen.Utils.StripHtmlBody(result);
				}
			}
			finally
			{
				method.releaseConnection();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return result;
	}

	public String Header() {
		return m_header;
	}
}
