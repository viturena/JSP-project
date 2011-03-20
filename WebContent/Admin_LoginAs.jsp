<%@ page import="java.sql.*, com.catgen.*, com.catgen.factories.*, java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%><%
Connection conn = MySqlDB.getDBConnection(getServletContext());
try{
	String userId = (String)session.getAttribute("USER");
	String type = (String)session.getAttribute("TYPE");
	boolean isSuperAdmin = SuperAdminFactory.isSuperAdmin(userId, type);
	if(!isSuperAdmin){
		session.setAttribute("errcode","501");
	}else{%>
	<input type="hidden" id="module" value="<%=Constants.SUPER_ADMIN%>"/>
	<input type="hidden" id="loginAs" value="<%=Constants.LOGIN_AS%>"/>
	<input type="hidden" id="user_nm" value="<%=Constants.NETWORK_MARKET %>"/>
	<input type="hidden" id="user_vendor" value="<%=Constants.VENDOR %>"/>
	Change to user:
	<select id="userList" onchange='generateList()'>
		<option value=""></option>
		<option value="<%=Constants.NETWORK_MARKET %>">Network Market</option>
		<option value="<%=Constants.VENDOR %>">Vendor</option>
	</select><br/><br/>
	<div id="loginAsDiv_<%=Constants.NETWORK_MARKET %>" style="display:none">
		Choose Network Market
		<select id="UserList_<%=Constants.NETWORK_MARKET %>"><% 
			List<NetworkMarket> networkMarkets = NetMarketFactory.getNetmarketMembers(conn);
			for(NetworkMarket nm: networkMarkets){ %>
				<option value="<%=nm.NetworkMarketID %>"><%=nm.NetworkMarketID%>&nbsp;-&nbsp;<%=nm.Name %></option>
			<%}%>
		</select><br/><br/>
		<input type="button" value="Go &gt;&gt; " onclick="loginAsUser()"></input>
	</div>
	<div id="loginAsDiv_<%=Constants.VENDOR %>" style="display:none">
		Choose Vendor
		<select id="UserList_<%=Constants.VENDOR %>"><% 
		List<Company> companies = CompanyFactory.getAllCompanies(conn);
			for(Company company: companies){ %>
				<option value="<%=company.Code %>"><%=company.Code%> - <%=company.Name %></option>
			<%}%>
		</select><br/><br/>
		<input type="button" value="Go &gt;&gt; " onclick="loginAsUser()"></input>
	</div>
<%}
} finally{
	conn.close();
}%>