<%@ page import="java.sql.*, com.catgen.*, com.catgen.factories.*, java.util.*, org.apache.commons.lang.*" %><%
String userId = (String)session.getAttribute("USER");
String type = (String)session.getAttribute("TYPE");
boolean isValidNM = NetMarketFactory.isValidNM(userId, type);
if(!isValidNM){
	session.setAttribute("errcode","504");
}else{ 
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	String marketId = (String)session.getAttribute("USER");
	try{
		NetworkMarket networkMarket = NetMarketFactory.getNetworkMarketByCode(conn, marketId);
		session.setAttribute("NETWORK_MARKETS",networkMarket);%>
		<input type="hidden" id="module" value="<%=Constants.NETWORK_MARKET_INFO%>"></input>
		<script type="text/javascript">
			myEditor = false;   //Editor instance for the footer
			myEditorDesc = false;        //Editor instance for the description
			myEditorHeader = false;        //Editor instance for the Header
			myEditorLogo = false;        //Editor instance for the Logo
		</script>
		<script type="text/javascript">
			function updateNmInfo()
			{
				//if(myEditor)
				//{
				//	return updateNMInfoFooterEditor(myEditor);
				//}else{
					return updateNMInfoFooterEditor(myEditor,myEditorDesc,myEditorHeader,myEditorLogo);
					//return updateNMInfo();
				//}
			}
		</script>
		<table cellspacing="10">
			<tr>
				<td>Name</td>
				<td><input type="text" id="Name" value="<%=(networkMarket.Name!=null)?StringEscapeUtils.escapeHtml(networkMarket.Name):"" %>" size="40" maxlength="128"></input>
				<input type="hidden" id="hName" value="<%=(networkMarket.Name!=null)?StringEscapeUtils.escapeHtml(networkMarket.Name):"" %>"></input></td>
			</tr>
			<tr>
				<td>Address</td>
				<td><input type="text" id="Address" value="<%=(networkMarket.Address!=null)?StringEscapeUtils.escapeHtml(networkMarket.Address):"" %>" size="40" maxlength="128">
				<input type="hidden" id="hAddress" value="<%=(networkMarket.Address!=null)?StringEscapeUtils.escapeHtml(networkMarket.Address):"" %>"></td>
			</tr>
			<tr>
				<td>City</td>
				<td><input type="text" id="City" value="<%=(networkMarket.City!=null)?StringEscapeUtils.escapeHtml(networkMarket.City):"" %>" size="40" maxlength="128">
				<input type="hidden" id="hCity" value="<%=(networkMarket.City!=null)?StringEscapeUtils.escapeHtml(networkMarket.City):"" %>"></td>
			</tr>
			<tr>
				<td>State</td>
				<td><input type="text" id="State" value="<%=(networkMarket.State!=null)?StringEscapeUtils.escapeHtml(networkMarket.State):"" %>" size="40" maxlength="64">
				<input type="hidden" id="hState" value="<%=(networkMarket.State!=null)?StringEscapeUtils.escapeHtml(networkMarket.State):"" %>"></td>
			</tr>
			<tr>
				<td>Country</td>
				<td>
					<select id="Country">
						<option value=""> -- Select -- </option><%
						String selectedCountry = (networkMarket.Country!=null)?StringEscapeUtils.escapeHtml(networkMarket.Country):"";
						List<Country> countries = CountryFactory.fetchAllCountries(conn);
						for(Country country: countries){ %>
							<option value="<%= country.code%>" <%=(country.code.equalsIgnoreCase(selectedCountry))?"selected":"" %>><%=country.name %></option>	
						<%} %>
					</select>
					<input type="hidden" id="hCountry" value="<%=(networkMarket.Country!=null)?StringEscapeUtils.escapeHtml(networkMarket.Country):"" %>"></input>
				</td>
			</tr>
			<tr>
				<td>Zip</td>
				<td><input type="text" id="Zip" value="<%=(networkMarket.Zip!=null)?StringEscapeUtils.escapeHtml(networkMarket.Zip):"" %>" size="40" maxlength="32">
				<input type="hidden" id="hZip" value="<%=(networkMarket.Zip!=null)?StringEscapeUtils.escapeHtml(networkMarket.Zip):"" %>"></td>
			</tr>
			<tr>
				<td>Logo Image</td>
				<td><textarea id="LogoImage" rows="7" cols="60">
				<%=(networkMarket.LogoImage!=null)?StringEscapeUtils.escapeHtml(networkMarket.LogoImage):"" %></textarea>
				<a href="javascript:void(0);" 
									onclick="myEditorLogo = getRichEditorNMInfoLogo();document.getElementById('rtLogoEditor').style.visibility = 'hidden';" 
									id="rtLogoEditor">HTML Editor</a>
				<input type="hidden" id="hLogoImage" value="<%=(networkMarket.LogoImage!=null)?StringEscapeUtils.escapeHtml(networkMarket.LogoImage):"" %>"></td>
			</tr>
			<tr>
				<td>Header</td>
				<td><textarea id="Header" rows="7" cols="60">
				<%=(networkMarket.Header!=null)?StringEscapeUtils.escapeHtml(networkMarket.Header):"" %></textarea>
				<a href="javascript:void(0);" 
									onclick="myEditorHeader = getRichEditorNMInfoHeader();document.getElementById('rtHeaderEditor').style.visibility = 'hidden';" 
									id="rtHeaderEditor">HTML Editor</a>
				<input type="hidden" id="hHeader" value="<%=(networkMarket.Header!=null)?StringEscapeUtils.escapeHtml(networkMarket.Header):"" %>"></td>
			</tr>
			<tr>
				<td>Description</td>
				<td>
					<textarea id="Description" rows="7" cols="60">
						<%=(networkMarket.Description!=null)?StringEscapeUtils.escapeHtml(networkMarket.Description):"" %>
					</textarea>
					<a href="javascript:void(0);" 
									onclick="myEditorDesc = getRichEditorNMInfoDesc();document.getElementById('rtDescEditor').style.visibility = 'hidden';" 
									id="rtDescEditor">HTML Editor</a>
					<input type="hidden" id="hDescription" 
					value="<%=(networkMarket.Description!=null)?StringEscapeUtils.escapeHtml(networkMarket.Description):"" %>">
				</td>
			</tr>
			<tr>
				<td>Footer</td>
				<td><textarea id="Footer" rows="7" cols="60"><%=(networkMarket.Footer!=null)?StringEscapeUtils.escapeHtml(networkMarket.Footer):"" %></textarea>
				<a href="javascript:void(0);" 
									onclick="myEditor = getRichEditorNMInfoFooter();document.getElementById('rtEditor').style.visibility = 'hidden';" 
									id="rtEditor">HTML Editor</a>
				
				<input type="hidden" id="hFooter" value="<%=(networkMarket.Footer!=null)?StringEscapeUtils.escapeHtml(networkMarket.Footer):"" %>"></td></tr>
				<tr>
				<td>Template</td>
				<td><input type="text" id="Template" value="<%=(networkMarket.Template!=null)?StringEscapeUtils.escapeHtml(networkMarket.Template):"" %>" size="40" maxlength="64"></input>
				<input type="hidden" id="hTemplate" value="<%=(networkMarket.Template!=null)?StringEscapeUtils.escapeHtml(networkMarket.Template):"" %>"></td>
			</tr>
			<tr>
				<td>Meta Keywords</td>
				<td><textarea id="Keywords" rows="7" cols="60"><%=(networkMarket.Keywords!=null)?StringEscapeUtils.escapeHtml(networkMarket.Keywords):"" %></textarea>
				<input type="hidden" id="hKeywords" value="<%=(networkMarket.Keywords!=null)?StringEscapeUtils.escapeHtml(networkMarket.Keywords):"" %>"></td>
			</tr>
			<tr>
				<td>Meta Description</td>
				<td><textarea id="MetaDescription" rows="7" cols="60"><%=(networkMarket.MetaDescription!=null)?StringEscapeUtils.escapeHtml(networkMarket.MetaDescription):"" %></textarea>
				<input type="hidden" id="hMetaDescription" value="<%=(networkMarket.MetaDescription!=null)?StringEscapeUtils.escapeHtml(networkMarket.MetaDescription):"" %>"></td>
			</tr>
			<tr>
				<td>Google Site Verification Code</td>
				<td>
				<input type="text" id="GSiteVerification" value="<%=(networkMarket.GSiteVerification!=null)?StringEscapeUtils.escapeHtml(networkMarket.GSiteVerification):"" %>" size="50" maxlength="100">
				<input type="hidden" id="hGSiteVerification" value="<%=(networkMarket.GSiteVerification!=null)?StringEscapeUtils.escapeHtml(networkMarket.GSiteVerification):"" %>"></td>
			</tr>
			<tr>
				<td>Google Analytics Code</td>
				<td><input type="text" id="GoogleAnalytics" value="<%=(networkMarket.GoogleAnalytics!=null)?StringEscapeUtils.escapeHtml(networkMarket.GoogleAnalytics):"" %>" size="50" maxlength="100">
				<input type="hidden" id="hGoogleAnalytics" value="<%=(networkMarket.GoogleAnalytics!=null)?StringEscapeUtils.escapeHtml(networkMarket.GoogleAnalytics):"" %>"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="button" id="submitWithAjax" value="Update changes" 
				onClick="updateNmInfo();"></input></td>
			</tr>
		</table>
	<%} finally{
		conn.close();
	}
}%>