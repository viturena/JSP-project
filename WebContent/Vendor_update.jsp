<%@ page import="java.sql.*, com.catgen.*, com.catgen.factories.*, java.util.*, org.apache.commons.lang.*" %><%
String userId = (String)session.getAttribute("USER");
String type = (String)session.getAttribute("TYPE");
boolean isValidVendor = CompanyFactory.isValidVendor(userId, type);
if(!isValidVendor){
	session.setAttribute("errcode","505");
}else{%>
	<input type="hidden" id="module" value="<%=Constants.MEMBERS_AND_PRODUCTS%>"/>
	<input type="hidden" id="refreshVendorData" value="<%=Constants.REFRESH_VENDOR_DATA%>"/>
	<h2>Vendor Data Refresh</h2>
	<input type="button" value="Refresh Company and Products data" onclick="refreshVendorData('<%=userId %>')"/></input>
	<br/><br/>
<%}%>