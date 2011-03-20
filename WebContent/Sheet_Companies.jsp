<%@ page import="java.sql.*, com.catgen.*, com.catgen.factories.*, java.util.*, org.apache.commons.lang.*" %>
<%@page import="com.catgen.helper.SuperNmHelper"%>
<%@page import="com.catgen.helper.VendorHelper"%><%
Connection conn = MySqlDB.getDBConnection(getServletContext());
String marketId = (String)session.getAttribute("USER");
try{

	String userId = (String)session.getAttribute("USER");
	String type = (String)session.getAttribute("TYPE");
	int i=1;
	String bg="";
	boolean isValidNM = NetMarketFactory.isValidNM(userId, type);
	if(!isValidNM){
		session.setAttribute("errcode","504");
	}else{ 	
		List<Company> acceptedVendors = CompanyFactory.getAcceptedCompaniesByMarketId(conn, marketId);
		List<Company> newVendors = CompanyFactory.getNewCompaniesByMarketId(conn, marketId);
		List<Company> approvedOnlyVendors = CompanyFactory.getApprovedButNotJoinedCompaniesByMarketId(conn, marketId);
		List<Company> nmAndSubNmVendors = SuperNmHelper.findAllContainedCompaniesBySuperMarketId(conn,marketId);
		List<Company> onlySubNmVendors = new ArrayList<Company>();
		List<Company> completeList = new ArrayList<Company>();
		completeList.addAll(nmAndSubNmVendors);
		completeList.addAll(approvedOnlyVendors);
		List<Company> nonAvailableVendors = VendorHelper.getOtherCompanies(conn, completeList);
		
		for(Company company:nmAndSubNmVendors){
			boolean contained=false;
			for(Company accepted: acceptedVendors){
				if(accepted.Code.equalsIgnoreCase(company.Code)){
					contained=true;
					break;
				}
			}
			if(!contained){
				onlySubNmVendors.add(company);
			}
		}%>
		
		
<%@page import="com.catgen.helper.NetMarketHelper"%><input type="hidden" id="module" value="<%=Constants.MEMBERS_AND_PRODUCTS%>"></input>
		<input type="hidden" id="addVendor" value="<%=Constants.ADD_VENDOR%>"></input>
		<input type="hidden" id="rmvVendor" value="<%=Constants.REMOVE_VENDOR%>"></input>
		<input type="hidden" id="featureVendor" value="<%=Constants.FEATURE_VENDOR%>"></input>
		<input type="hidden" id="unfeatureVendor" value="<%=Constants.UNFEATURE_VENDOR%>"></input>
		<input type="hidden" id="addVendorDirectly" value="<%=Constants.ADD_VENDOR_DIRECTLY%>"></input>
		<input type="hidden" id="limitexceededmessage" value="<%=Constants.FREE_NM_LIMIT_EXCEEDED_MESSAGE%>"></input>
		<h2>Add New Vendor</h2><%
		if(NetMarketFactory.getNetworkMarketByCode(conn,marketId).premium || CompanyFactory.getNetmarketMembers(conn,marketId).size()<Constants.FREE_NM_MAX_VENDOR_COUNT){%>
			<div id="div-add-vendor-directly">
				<select id="div_nonAvailableVendors"><%
					for(Company company: nonAvailableVendors){%>
						<option value="<%=company.Code %>"><%=company.Code %>&nbsp;-&nbsp;<%=company.Name %></option>
					<%}%>
				</select><br/><br/>
				<input id="add-vendor-button" type="button" style="padding:10px 20px; font-size: 16px;" value="Add selected Vendor" onclick="addVendorDirectly('<%=marketId %>')"><br/><br/>
			</div><%
		}else{%>
			<%=Constants.FREE_NM_LIMIT_EXCEEDED_MESSAGE%>	
		<%}%><br/><br/><br/>
		<h2>New Vendor Requests</h2>
		<i>Click on the button to allow vendor to join. Once allowed, the vendor will be notified, and will be able to make <br/>necessary payment to be added to the Network Market automatically.</i><br/><br/>
		<table cellspacing="0"><%
			for(Company company: newVendors){
				String url = company.CompanyURL;
				url = (url!=null)?url:"";
				if(company.Code!=null && company.Code.length()>0 && company.Name!=null && company.Name.length()>0){ %>
					<tr>
						<td colspan="3">
							<div id="new_<%=company.Code %>">
								<table border="0">
									<tr>
										<td width="40" align="left"><input type="image" src="images/common/Approve.jpg" alt="Allow vendor to join" onClick="addNewVendor('<%=marketId %>','<%=company.Code%>');"></input></td>
										<td width="200" align="left"><%=(company.Code!=null)?StringEscapeUtils.escapeHtml(company.Code):"" %></td>
										<td width="600" align="left"><%=(company.Name!=null)?StringEscapeUtils.escapeHtml(company.Name):"" %>&nbsp;[URL:<%
										if(url.length()>0){%>
											<a href="<%=url %>" target="_blank" style="color:gray;"><%=url %></a>	
										<%}else{%>
											<i>Not available</i>
										<%}%>
										]</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				<%}
			}%>
		</table><br/><br/>
		<h2>Available Vendors</h2>
		<i>Vendors listed below belong to this Network market and are available in the Network Market website</i><br/><br/>
		<table cellspacing="0">
			<tr>
				<td>
					<div id="vacant"></div>
				</td>
			</tr><%
			for(Company company: acceptedVendors){
				bg = (i==0)?"#fff":"#eee";
				String url2 = company.CompanyURL;
				url2 = (url2!=null)?url2:""; %>
				<tr style="background-color:<%=bg%>">
					<td colspan="4">						
						<div id="old_<%=company.Code %>">
							<table border="0">
								<tr>
									<td width="200"><%=(company.Code!=null)?StringEscapeUtils.escapeHtml(company.Code):"" %></td>
									<td width="600" align="left"><%=(company.Name!=null)?StringEscapeUtils.escapeHtml(company.Name):"" %>&nbsp;[URL:<%
									if(url2.length()>0){%>
										<a href="<%=url2 %>" target="_blank" style="color:gray;"><%=url2 %></a>	
									<%}else{%>
										<i>Not available</i>
									<%}%>
									]</td>
								</tr>
							</table>
						</div>
					</td>
				</tr><%
				i=(i+1)%2;
			}
			if(onlySubNmVendors!=null){ %>
				<tr>
					<td>
						<br/><h2>Sub Network Market Vendor List</h2>
						<i>The vendors listed below are available on this Network Market, but belong to the <br/>
						Network Market that are added to this Network Market. If the sub-network market is removed,<br/>
						the vendors belonging to that network market will cease to exist in this network market.</i><br/><br/>
					</td>
				</tr><%
				for(Company company: onlySubNmVendors){ 
				bg = (i==0)?"#fff":"#eee";
				String url3 = company.CompanyURL;
				url3 = (url3!=null)?url3:""; %>
					<tr style="background-color:<%=bg%>">
						<td>
							<table border="0">
								<tr>
									<td width="200"><%=(company.Code!=null)?StringEscapeUtils.escapeHtml(company.Code):"" %></td>
									<td width="600"><%=(company.Name!=null)?StringEscapeUtils.escapeHtml(company.Name):"" %>&nbsp;[URL:<%
									if(url3.length()>0){%>
										<a href="<%=url3 %>" target="_blank" style="color:gray;"><%=url3 %></a>	
									<%}else{%>
										<i>Not available</i>
									<%}%>
									]</td>
								</tr>
							</table>
						</td>
					</tr><%
					i=(i+1)%2;
				} %>
			<%} %>
		</table><br/><br/>
	<%}
} finally{
	conn.close();
}%>