<%@ page import="com.catgen.Constants" %><%
String userId = (String)session.getAttribute("USER");
String sType =(String)session.getAttribute("TYPE");
int type=0;
try{
	type = Integer.parseInt(sType);
}catch(Exception e){
	type=0;
}
int activeLink=Constants.NONE;
Object activeLinkObject = session.getAttribute(Constants.ACTIVE_LINK);
if(activeLinkObject!=null){
	activeLink = ((Integer)activeLinkObject).intValue();
}
Connection conn = MySqlDB.getDBConnection(getServletContext());
try{%>
	<%@page import="com.catgen.factories.MasterCompanyFactory"%>
	<%@page import="java.sql.Connection"%>
	<%@page import="com.catgen.factories.MySqlDB"%>
	<%@page import="com.catgen.helper.SuperNmHelper"%><%
	
	if(userId==null || userId.length()==0 || type==0){ %>
		<a title="OpenEntry LOGIN page" class="<%=(Constants.LOGIN==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.LOGIN %>">Login</a>&nbsp;|
		<a title="Click to register a new Network Market" class="<%=(Constants.REGISTER_NM==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.REGISTER_NM %>">Register Network Market</a>&nbsp;|
		<a title="Click to register a new Referrer" class="<%=(Constants.REGISTER_REFERRER==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.REGISTER_REFERRER %>">Register Referrer</a>&nbsp;|
	<%} else{
		if(Constants.NETWORK_MARKET==type){ %>
			<a title="Network Market Information Customization page" class="<%=(Constants.NETWORK_MARKET_INFO==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.NETWORK_MARKET_INFO %>">NM Info</a>&nbsp;|
			<a title="Accept/Feature/Unfeature/Remove Vendors" class="<%=(Constants.VENDOR==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.VENDOR %>">Vendors</a>&nbsp;|
			<a title="Add/Display/Modify/Arrange/Delete Pages" class="<%=(Constants.PAGES==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.PAGES %>">Pages</a>&nbsp;|
			<a title="Feature Products of Associated Vendors" class="<%=(Constants.FEATURED_PRODUCTS==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.FEATURED_PRODUCTS %>">Featured Products</a>&nbsp;|
			<a title="Add/Remove Product Categories" class="<%=(Constants.CATEGORIES==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.CATEGORIES %>">Categories</a>&nbsp;|
			<a title="Customize Look-and-Feel of your Webpage" class="<%=(Constants.STYLE==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.STYLE %>">Style</a>&nbsp;|<%
			if(SuperNmHelper.isSuperNm(conn,userId)){%>
				<a title="Add Other Network Markets" class="<%=(Constants.ADD_NM==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.ADD_NM %>">Add NM</a>&nbsp;|
			<%}%>
			<a title="Send mail to vendors" class="<%=(Constants.MAIL_MANAGER==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.MAIL_MANAGER %>">Mail Mgr</a>&nbsp;|
		<%}else if(Constants.VENDOR==type){%>
			<a title="Your Catalog Spreadsheet" class="hoverClass" href="<%=Constants.GOOGLE_SPREADSHEET_URL_FORMAT.replaceAll(Constants.KEY_VARIABLE,MasterCompanyFactory.getSSkeyByCompanyCode(conn,userId)) %>" target="_blank">Catalog Spreadsheet</a>&nbsp;|
			<a title="Send ADD REQUEST to various Network Markets" class="<%=(Constants.REQUEST_ADD==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.REQUEST_ADD %>">Add Request</a>&nbsp;|
			<a title="Point to your Catalog Spreadsheet and Refresh changes" class="<%=(Constants.REFRESH==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.REFRESH %>">Refresh Data</a>&nbsp;|
		<%} else if(Constants.SUPER_ADMIN==type){%>
			<a title="Update Domain URL" class="<%=(Constants.DOMAIN_MANAGER==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.DOMAIN_MANAGER%>">Domain Mgr</a>&nbsp;|
			<span style="color:#777">Info[</span>
			<a title="Information on available Network Markets" class="<%=(Constants.INFO_NM==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.INFO_NM%>">NM</a>/
			<a title="Information on available Vendors" class="<%=(Constants.INFO_VENDOR==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.INFO_VENDOR%>">Vendor</a>]&nbsp;|
			<a title="Generate UserID/Password for Network Market or Vendor maintaining Spreadsheets" class="<%=(Constants.GENERATE_USERID==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.GENERATE_USERID%>">Move2UI</a>&nbsp;|
			<a title="Switch to Network Market or Vendor session/panel" class="<%=(Constants.LOGIN_AS==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.LOGIN_AS%>">Login As</a>&nbsp;|
			<a title="Upgrade a Network Market to Super Network Market and Vice versa" class="<%=(Constants.UPGRADE_TO_SUPERNM==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.UPGRADE_TO_SUPERNM%>">Super NM</a>&nbsp;|
			<a title="Set charges for Network Markets" class="<%=(Constants.CHARGES==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.CHARGES%>">Charges</a>&nbsp;|
			<%--<a title="Funds Manager" class="<%=(Constants.FUNDS==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.FUNDS%>">Funds</a>&nbsp;| --%>
			<a title="Generate a New Password(Random String) for a Network Market or Vendor Account" class="<%=(Constants.RESET_PASSWORD==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.RESET_PASSWORD %>">Reset Pwd</a>&nbsp;| 
			<%--<a title="Initiate Image check" class="<%=(Constants.IMAGE_CHECK==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.IMAGE_CHECK %>">Image Check</a>&nbsp;|--%><%
		}%>
		<a title="Change your Account Password" class="<%=(Constants.CHANGE_PASSWORD==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.CHANGE_PASSWORD %>">Password</a>&nbsp;|
		<a title="Display Referrals Report" class="<%=(Constants.REFERRALS==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.REFERRALS %>">Referrals</a>&nbsp;|
		<%--<a title="Display Referrals Billing Report" class="<%=(Constants.REFERRAL_BILLING==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.REFERRAL_BILLING %>">Billing Rpt</a>&nbsp;|--%>
		<a title="Logout from OpenEntry Account Management panel" class="<%=(Constants.LOGOUT==activeLink)?"selectedLink":"hoverClass" %>" href="DataBeanPopulator.jsp?link=<%=Constants.LOGOUT %>">LogOut</a>&nbsp;|
		<span class="helpLink">Help</span>&nbsp;<img title="Click for Help[Popup should be enabled]" src="images/common/help.png" style="cursor:pointer" onclick="getHelpScreen('<%=activeLink %>')"><%
	}
}finally{
	conn.close();
}%>