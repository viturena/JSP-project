package com.catgen;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.lang.StringEscapeUtils;

import com.catgen.exception.StyleDataTypeFormatException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils 
{
	public static String getReferralURL(String relativePath, String referralID, String companyCode, String type, String url, String extraData) throws UnsupportedEncodingException
	{
		if(referralID != null && referralID.trim().length() > 0 && url != null && url.toLowerCase().startsWith("http") )
			return "" + relativePath + "ReferralEvent.html?refid=" + URLEncoder.encode(referralID, "UTF-8") + "&companycode=" + URLEncoder.encode(companyCode, "UTF-8") + "&type=" + URLEncoder.encode(type, "UTF-8") + "&url=" + URLEncoder.encode(url, "UTF-8") + "&extra=" + extraData;
		else
			return url;
	}
	
	public static String getCookieValue(String cookieName, HttpServletRequest request)
	{
		Cookie[] cookies = request.getCookies();

		if(cookies != null)
		{
			for(int i = 0; i < cookies.length; i++) { 
				Cookie c = cookies[i];
				if (c != null && "refid".equals(c.getName())) 
					return c.getValue();
			}
		}
		
		return null;
	}
	
	public static void SetCookieValue(String name, String value, HttpServletResponse response)
	{
		Cookie cookie = new Cookie(name, value);
		
		if(name.equals("refid"))
	        cookie.setMaxAge(30*24*60*60);
		else
			cookie.setMaxAge(-1); //7*24*60*60);
		
        cookie.setPath("/");
        
		response.addCookie(cookie);
	}
	
	public static String getSafeString(String s)
	{
		if(s == null)
			return "";
		else
			return s;
	}
	
	public static int StrToIntDef(String s, int d)
	{
		int result = d;
		try
		{
			result = Integer.parseInt(s);
		}
		catch (Exception e) {
		}
		
		return result;		
	}

	public static double StrToDoubleDef(String s, double d)
	{
		double result = d;
		try
		{
			result = Double.parseDouble(s);
		}
		catch (Exception e) {
		}
		
		return result;		
	}
	
	public static String getHtmlEscape(String s)
	{
		if(s == null)
			return "";
		else
			return StringEscapeUtils.escapeHtml( s );
	}

	public static String getHtmlEscapeWithBR(String s)
	{
		String r = s;
		if(r == null)
			r = "";
		else
			r = StringEscapeUtils.escapeHtml( s );
		
		return r.replace("\r", "").replace("\n", "<br/>");
	}
	
	public static String StripHtmlBody(String html)
	{
		String result = null;
		if(html != null)
		{
			Pattern p = Pattern.compile("^.*<body[^>]*>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = p.matcher(html);
			
			result = m.replaceFirst("");
			
			p = Pattern.compile("</body[^>]*>.*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			m = p.matcher(result);
			result = m.replaceFirst("").replace("\u00A0", " ");
		}
		
		return result;		
	}

	public static String StripGoogleSites(String html)
	{
		String result = null;
		if(html != null)
		{
			Pattern p = Pattern.compile("^.*<table[^>]*jot-content-table[^>]*>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = p.matcher(html);
			
			result = m.replaceFirst("<table>");
			
			p = Pattern.compile("<div[^>]*goog-ws-bottom[^>]*>.*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			m = p.matcher(result);
			result = m.replaceFirst("").replace("\u00A0", " ");
		}
		
		return result;		
	}

	public static String StripGoogleDocs(String html)
	{
		String result = null;
		if(html != null)
		{
			Pattern p = Pattern.compile("^.*<div[^>]*doc-contents[^>]*>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = p.matcher(html);
			
			result = m.replaceFirst("<div id=\"doc-contents\">");
			
			p = Pattern.compile("<div[^>]*google-view-footer[^>]*>.*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			m = p.matcher(result);
			result = m.replaceFirst("");
			
			result = result.replace("src=\"File?", "src=\"http://docs.google.com/File?").replace("\u00A0", " ");
		}
		
		return result;		
	}
	
	public static String getProductURL(Company company, Product product, String relativePath, String referralID) throws UnsupportedEncodingException
	{
		String productURL = "";
	
		if(product.URL != null && product.URL.length() > 0)
			productURL = product.URL.replace( "$$productcode$$", product.Code );
		else if(company.ProductURL != null && company.ProductURL.length() > 0)
			productURL = company.ProductURL.replace( "$$productcode$$", product.Code );
		else
			productURL = "" + relativePath + URLEncoder.encode(product.CompanyCode, "UTF-8") + "/Product.html?productid=" + URLEncoder.encode(product.Code, "UTF-8");

		if(referralID != null && referralID.length() > 0)
		{
			if(productURL.indexOf("?") > 0)
				productURL = productURL.replaceFirst("\\?", "?refid=" + URLEncoder.encode(referralID, "UTF-8") + "&");
			else
				productURL += "?refid=" + URLEncoder.encode(referralID, "UTF-8");
		}
		
		return productURL;
	}

	public static String getCompanyURL(Company company, String relativePath, String referralID) throws UnsupportedEncodingException
	{
		String companyURL = "";
	
		if(company.CompanyURL != null && company.CompanyURL.length() > 0)
			companyURL = company.CompanyURL;
		else
			companyURL = "" + relativePath + URLEncoder.encode(company.Code, "UTF-8" ) + "/Member.html";

		if(referralID != null && referralID.length() > 0)
		{
			if(companyURL.indexOf("?") > 0)
				companyURL = companyURL.replaceFirst("\\?", "?refid=" + URLEncoder.encode(referralID, "UTF-8") + "&");
			else
				companyURL += "?refid=" + URLEncoder.encode(referralID, "UTF-8");
		}
		
		return companyURL;
	}
	
	public static String getCurrencySymbol(Company company, Product product) 
	{
		String currency = product.Currency;
		if(currency == null || currency.trim().length() == 0)
		{
			currency = company.CurrencySymbol;
			if(currency == null || currency.trim().length() == 0)
				currency = company.Currency;
		}
		
		if(currency == null)
			currency = "";
		
		return currency;
	}
	
	public static Calendar getDateAfterGivenMonths(int months){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, months);
		return calendar;
	}
	
	public static int convert2Int(String str, String fieldName) throws StyleDataTypeFormatException{
		int intValue = -1;
		try{
			if(str!=null && str.trim().length()>0)
				intValue = Integer.parseInt(str);
		}catch(Exception e){
			throw new StyleDataTypeFormatException("Error in field "+fieldName);
		}
		return intValue;
	}
	
	public static boolean convert2Boolean(String str, String fieldName) throws StyleDataTypeFormatException{
		boolean boolValue = false;
		try{
			if(str!=null && str.trim().length()>0)
				if("y".equalsIgnoreCase(str)){
					boolValue = true;
				}
		}catch(Exception e){
			throw new StyleDataTypeFormatException("Error in field "+fieldName);
		}
		return boolValue;
	}
	
	public static String isChecked(String repeat, String repVal){
		String retVal="";
		if(Style.NONE.equalsIgnoreCase(repVal)){
			if(!(Style.REPEAT_XX.equalsIgnoreCase(repeat) || Style.REPEAT_XY.equalsIgnoreCase(repeat) || Style.REPEAT_YY.equalsIgnoreCase(repeat))){
				retVal = "checked";
			}
		}else{
			retVal = (repVal.equalsIgnoreCase(repeat))?"checked":"";
		}
		return retVal;
	}
	
	public static String categoryChecked(String val, String pos){
		String retVal="";
		if(Style.NO_CATEGORY.equalsIgnoreCase(pos)){
			if(!(Style.LEFT_CATEGORY.equalsIgnoreCase(val) || Style.RIGHT_CATEGORY.equalsIgnoreCase(val))){
				retVal = "checked";
			}
		}else{
			retVal = (pos.trim().equalsIgnoreCase(val))?"checked":"";
		}
		return retVal;
	}
	
	public static String generateRandomString(int size){
		String randomString = "";
		char ch='0';
		int num=0;
		
		for(int i=0;i<size;i++){
			int opt = (int)(3.0*Math.random())+1;
			switch(opt){
			case 1:
				num = (int)(10.0*Math.random());
				ch = '0';
				ch += num;
				break;
			case 2:
				num = (int)(26.0*Math.random());
				ch = 'a';
				ch += num;
				break;
			case 3:
				num = (int)(26.0*Math.random());
				ch = 'A';
				ch += num;
				break;
			}
			randomString += ch;
		}
		return randomString;
	}
	
	public static Date getDatefromString(String format, String date){
		Date dateObj = new Date();
		format = (format!=null && format.length()>0)?format:"dd/MM/yyyy";
		SimpleDateFormat searchDateFormat = new SimpleDateFormat(format);

		Calendar calendar = Calendar.getInstance(); 
		try
		{
			dateObj = searchDateFormat.parse(date);
		}
		catch(Exception e)
		{
			dateObj = calendar.getTime();
		}
		return dateObj;
	}
	
	public static String returnFormattedDateString(String format, Date date){
		return new SimpleDateFormat(format).format(date);
	}
	
	public static boolean fileExists(String url){
		try{
			HttpClient client = new HttpClient();
			HttpMethod method = new HeadMethod(url);
			return(client.executeMethod(method)==HttpStatus.SC_OK);
		}
		catch(Exception e){
			return false;
		}
	}

	public static String getThreeCharValue(int num){
		if(num>=0 && num<10){
			return "00"+num;
		}else if(num>=10 && num<100){
			return "0"+num;
		}else if(num>=100 && num<1000){
			return ""+num;
		}else{
			return ("##");
		}
	}
	
	public static List<String> getUniqueEmailIdListFromVendors(List<Company> companies){
		List<String> emailList = new ArrayList<String>();
		for(Company company: companies){
			if(company.ContactEmail!=null && !(emailList.contains(company.ContactEmail))){
				emailList.add(company.ContactEmail);
			}
		}
		return emailList;
	}
	
	public static String getCommaSeparatedEmailFromEmailList(List<String> emailList){
		String commaSeparatedEmail = MailMsgs.OPENENTRY_MAIL_ID;
		for(String email: emailList){
			if(email!=null && email.length()>0){
				commaSeparatedEmail += ","+email;
			}
		}
		return commaSeparatedEmail;
	}
	
	public static InternetAddress[] getInternetAddressArrayFromString(String commaSeparatedEmail) throws AddressException{
		String[] emails = commaSeparatedEmail.split(",");
		int emailCount = emails.length;
		InternetAddress[] internetAddresses = new InternetAddress[emailCount];
		for(int i=0;i<emailCount;i++){
			internetAddresses[i] = new InternetAddress(emails[i].trim());
		}
		return internetAddresses;
	}
	
	public static String getCompanyDataFeedURLBySpreadsheetKey(String key, String seq){
		return Constants.GOOGLE_SPREADSHEET_DATAFEED_FORMAT.replaceAll(Constants.KEY_VARIABLE, key).replaceAll(Constants.SEQUENCE_VARIABLE, seq);
		
	}
	
	public static String getProductDataFeedURLBySpreadsheetKey(String key, String seq){
		return Constants.GOOGLE_SPREADSHEET_DATAFEED_FORMAT.replaceAll(Constants.KEY_VARIABLE, key).replaceAll(Constants.SEQUENCE_VARIABLE, seq);
		
	}
	
	public static String getUserTypeStringFromInt(int type){
		if(Constants.NETWORK_MARKET==type){
			return Constants.USER_TYPE[0];
		}else if(Constants.VENDOR==type){
			return Constants.USER_TYPE[1];
		}else if(Constants.SUPER_ADMIN==type){
			return Constants.USER_TYPE[2];
		}else if(Constants.REFERRER==type){
			return Constants.USER_TYPE[3];
		}else{
			return "Invalid User Type";
		}
	}
	
	public static double getAmountAndCommission(double amount, int type){
		double retAmount = 0.0;
		switch(type){
		case Constants.FUND_DEDUCTION:
			retAmount = amount*Constants.PERCENT_OPENENTRY/100;
			break;
		case Constants.FUND_FINAL:
			retAmount = amount*Constants.PERCENT_OTHERS/100;
			break;
		default:
			retAmount = 0;
		}
		return retAmount;
	}
	
	public static boolean checkForValidity(Date date){
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		if(!(date.before(today))){
			return true;
		}
		return false;
	}
}
