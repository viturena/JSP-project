<%@ page import="java.sql.*, com.catgen.*, com.catgen.factories.*, java.util.*"%>
<input type="hidden" id="module" value="<%=Constants.SUPER_ADMIN%>">
<input type="hidden" id="generateUserId" value="<%=Constants.GENERATE_USERID%>">
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
	<h2>UserID generation for NM/Vendors using Spreadsheets</h2>
	<input type="radio" name="radio_select" value="<%=Constants.NETWORK_MARKET%>" onclick="switchBusiness('<%=Constants.NETWORK_MARKET %>','div_business_')" checked/>Network Market<br/>
	<input type="radio" name="radio_select" value="<%=Constants.VENDOR%>" onclick="switchBusiness('<%=Constants.VENDOR %>','div_business_')"/>Vendor<br/><br/>
	
	<div id="div_business_<%=Constants.NETWORK_MARKET %>" style="display:inline"><%
		List<NetworkMarket> allNetworkMarkets = NetMarketFactory.getNetmarketMembers(conn);
		List<UserSessionBean> nmWithUserId = UserSessionFactory.getValidatedUserList(conn, Constants.NETWORK_MARKET);
		List<String> nmCodeList = new ArrayList<String>();
		for(UserSessionBean nmuser: nmWithUserId){
			nmCodeList.add(nmuser.userId);
		}%>
		Select Network Market:
		<select id="select_business_<%=Constants.NETWORK_MARKET %>"><%
			for(NetworkMarket netMarket: allNetworkMarkets){
				if(!(nmCodeList.contains(netMarket.NetworkMarketID))){%>
					<option value="<%=netMarket.NetworkMarketID%>"><%=netMarket.NetworkMarketID%> - <%=netMarket.Name%></option><%
				}
			}%>			
		</select>
	</div>
	
	<div id="div_business_<%=Constants.VENDOR %>" style="display:none"><%	
		List<MasterCompany> allMasterCompanies = MasterCompanyFactory.getMasterCompanies(conn);
		List<UserSessionBean> companiesWithUserId = UserSessionFactory.getValidatedUserList(conn, Constants.VENDOR);
		List<String> companyCodeList = new ArrayList<String>();
		for(UserSessionBean user: companiesWithUserId){
			companyCodeList.add(user.userId);
		}%>
		Select Vendor: 
		<select id="select_business_<%=Constants.VENDOR %>"><%
			for(MasterCompany masterCompany: allMasterCompanies){
				if(!(companyCodeList.contains(masterCompany.companyCode))){
					Company company = CompanyFactory.getCompanyByCode(conn, masterCompany.companyCode);
					String name = (company!=null)?company.Name:"";%>
					<option value="<%=masterCompany.companyCode%>"><%=masterCompany.companyCode%> - <%=name %></option><%
				}
			}%>
		</select>
	</div>
	
	<br/><br/>
	Select Scheme:
	<select name="schemes" id="schemes">
		<option value="1">Free Trial</option>
		<option value="2">Silver</option>
		<option value="3">Gold</option>
		<option value="4">Platinum</option>
	</select><br/><br/>
	
	<input type="button" value="Generate UserID for this Business" onclick="generateUserId()"><br/><br/><%
		}
	} finally{
	conn.close();
}%>