package com.catgen.controller;

import java.io.IOException;
import com.catgen.*;
import com.catgen.exception.SessionInactiveException;
import com.catgen.factories.NetMarketFactory;
import com.catgen.factories.XMLFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

public class NetworkMarketInfoController {
	public static String process(Connection conn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, SessionInactiveException 
	{
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String country = request.getParameter("country");
		String zip = request.getParameter("zip");
		String logoImage = request.getParameter("logoimage");
		String header = request.getParameter("header");
		String description = request.getParameter("description");
		String footer = request.getParameter("footer");
		String template = request.getParameter("template");
		String keywords = request.getParameter("keywords");
		String metaDescription = request.getParameter("metaDescription");
		String googleAnalytics = request.getParameter("googleAnalytics");
		String GSiteVerification = request.getParameter("GSiteVerification");
		
		NetworkMarket networkMarket = (NetworkMarket)request.getSession().getAttribute("NETWORK_MARKETS");
		
		if(networkMarket==null){
			throw new SessionInactiveException();
		}
		if(GSiteVerification!=null)
			networkMarket.GSiteVerification = GSiteVerification;
		
		if(name!=null)
			networkMarket.Name = name;
		
		if(address!=null)
			networkMarket.Address = address;
		
		if(city!=null)
			networkMarket.City = city;
		
		if(state!=null)
			networkMarket.State = state;
		
		if(country!=null)
			networkMarket.Country = country;
		
		if(zip!=null)
			networkMarket.Zip = zip;
		
		if(logoImage!=null)
			networkMarket.LogoImage = logoImage;
		
		if(header!=null)
			networkMarket.Header = header;
		
		if(description!=null)
			networkMarket.Description = description;
		
		if(footer!=null)
			networkMarket.Footer = footer;
		
		if(template!=null)
			networkMarket.Template = template;
		
		if(keywords!=null)
			networkMarket.Keywords = keywords;
		
		if(metaDescription!=null)
			networkMarket.MetaDescription = metaDescription;
		
		if(googleAnalytics!=null)
			networkMarket.GoogleAnalytics = googleAnalytics;
		
		NetMarketFactory.remove(conn, networkMarket.NetworkMarketID);
		NetMarketFactory.save(conn, networkMarket);
		String res = XMLFactory.getMessageElement("message","Update Successful");
		return res;
	}
}
