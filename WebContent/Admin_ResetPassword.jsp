<%@ page import="java.sql.*, com.catgen.*, com.catgen.factories.*, java.util.*"%>
<input type="hidden" id="module" value="<%=Constants.SUPER_ADMIN%>">
<input type="hidden" id="resetPassword" value="<%=Constants.RESET_PASSWORD%>">
<input type="hidden" id="other_<%=Constants.VENDOR%>" value="<%=Constants.NETWORK_MARKET%>"></input>
<input type="hidden" id="other_<%=Constants.NETWORK_MARKET%>" value="<%=Constants.VENDOR%>"></input>
<%
Connection conn = MySqlDB.getDBConnection(getServletContext());
try{
	String userId = (String)session.getAttribute("USER");
	String type = (String)session.getAttribute("TYPE");
	boolean isSuperAdmin = SuperAdminFactory.isSuperAdmin(userId, type);
	if(!isSuperAdmin){
		session.setAttribute("errcode","501");
	}else{%>
	<h2>Reset Password</h2>
	<input type="radio" name="radio_select2" value="<%=Constants.NETWORK_MARKET%>" onclick="switchBusiness('<%=Constants.NETWORK_MARKET %>','div_business2_')" checked/>Network Market<br/>
	<input type="radio" name="radio_select2" value="<%=Constants.VENDOR%>" onclick="switchBusiness('<%=Constants.VENDOR %>','div_business2_')"/>Vendor<br/><br/>
	
	<div id="div_business2_<%=Constants.NETWORK_MARKET %>" style="display:inline"><%
		List<UserSessionBean> nmWithUserId = UserSessionFactory.getValidatedUserList(conn, Constants.NETWORK_MARKET);%>
		Select Network Market:
		<select id="select_business2_<%=Constants.NETWORK_MARKET %>"><%
			for(UserSessionBean userSessionBean: nmWithUserId){
				NetworkMarket nm = NetMarketFactory.getNetworkMarketByCode(conn, userSessionBean.userId);
				String name = (nm!=null)?nm.Name:"";%>
				<option value="<%=userSessionBean.userId%>"><%=userSessionBean.userId%> - <%=name %></option><%
			}%>			
		</select>
	</div>
	
	<div id="div_business2_<%=Constants.VENDOR %>" style="display:none"><%	
		List<UserSessionBean> companiesWithUserId = UserSessionFactory.getValidatedUserList(conn, Constants.VENDOR);%>
		Select Vendor: 
		<select id="select_business2_<%=Constants.VENDOR %>"><%
			for(UserSessionBean userSessionBean: companiesWithUserId){
				Company company = CompanyFactory.getCompanyByCode(conn, userSessionBean.userId);
				String name = (company!=null)?company.Name:"";%>
				<option value="<%=userSessionBean.userId%>"><%=userSessionBean.userId%> - <%=name %></option><%
			}%>
		</select>
	</div>
	
	<br/><br/>
	<input type="button" value="Reset Password" onclick="resetPassword()"><br/><br/><%
		}
	} finally{
	conn.close();
}%>