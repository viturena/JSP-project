<%@ page import="com.catgen.*, java.util.*, com.catgen.factories.*, java.sql.*" %><%
Connection conn = MySqlDB.getDBConnection(getServletContext());
try{ %>
	<input type="hidden" id="module" value="<%=Constants.USER_SESSION %>"></input>
	<input type="hidden" id="checkAvailability" value="<%=Constants.CHECK_AVAILABILITY %>"></input>
	<input type="hidden" id="register" value="<%=Constants.REGISTER %>"></input>
	<input type="hidden" id="sendAuthCode" value="<%=Constants.SEND_AUTHENTICATION_CODE %>"></input>
	<input type="hidden" id="authenticationCode" value=""></input>
	<div id="dispErr" style="color:red"></div>
	<div id="registerMsg">
		<table border="0">
			<tr valign="top">
				<td colspan="2"><h2>Network Market Registration</h2></td>
			</tr>
			<tr id="regrow_userId">
				<td>Desired Market ID <span class="redtext">*</span></td>
				<td>
					<input type="text" id="reg_userId" maxlength="32" size="30"></input>
					<input type="hidden" id="help_userId" value="MarketId: Supports alphanumeric, hyphen and underscore characters only."></input>
				</td>
			</tr>
			<tr>
				<td></td>
				<td align="left"><input type="button" id="reg_checkbutton" value="Check availability" onclick="checkAvailability()"/><span id="div_chk"></span></td>
			</tr>
			<tr id="regrow_pwd">
				<td>Password <span class="redtext">*</span></td>
				<td>
					<input type="password" id="reg_pwd" maxlength="32" size="30"></input>
					<input type="hidden" id="help_pwd" value="Password is left blank or does not match"></input>
				</td>
			</tr>
			<tr id="regrow_pwdrpt">
				<td>Re-Enter Password <span class="redtext">*</span></td>
				<td>
					<input type="password" id="reg_pwdrpt" maxlength="32" size="30"></input>
					<input type="hidden" id="help_pwdrpt" value="Re-entered password is blank or does not match"></input>
				</td>
			</tr>
			<tr id="regrow_businessType">
				<td colspan="2">
					<input type="hidden" id="reg_businessType" value="<%=Constants.NETWORK_MARKET%>"></input>
					<input type="hidden" id="help_businessType" value="Business Type selection is invalid"></input>
				</td>
			</tr>
			<tr id="regrow_marketname">
				<td>Business Name <span class="redtext">*</span></td>
				<td>
					<input type="text" id="reg_marketname" maxlength="128" size="30"></input>
					<input type="hidden" id="help_marketname" value="Business Name is left blank"></input>
				</td>
			</tr>
			<tr id="regrow_address">
				<td>Address <span class="redtext">*</span></td>
				<td>
					<input type="text" id="reg_address" maxlength="128" size="30"></input>
					<input type="hidden" id="help_address" value="Address is left blank"></input>
				</td>
			</tr>
			<tr id="regrow_city">
				<td>City <span class="redtext">*</span></td>
				<td>
					<input type="text" id="reg_city" maxlength="128" size="30"></input>
					<input type="hidden" id="help_city" value="City is left blank"></input>
				</td>
			</tr>
			<tr id="regrow_state">
				<td>State</td>
				<td><input type="text" id="reg_state" maxlength="64" size="30"></input></td>
			</tr>
			<tr id="regrow_zip">
				<td>Zip</td>
				<td><input type="text" id="reg_zip" maxlength="32" size="30"></input></td>
			</tr>
			<tr id="regrow_country">
				<td>Country <span class="redtext">*</span></td>
				<td>
					<select id="reg_country">
						<option value=""> -- Select -- </option><%
						List<Country> countries = CountryFactory.fetchAllCountries(conn);
						for(Country country: countries){ %>
							<option value="<%= country.code%>"><%=country.name %></option>	
						<%} %>
					</select>
					<input type="hidden" id="help_country" value="Country selection is invalid"></input>
				</td>
			</tr>
			<tr id="regrow_landline">
				<td>Phone </td>
				<td>
					<input type="text" id="reg_landline" maxlength="20" size="30"></input>
					<input type="hidden" id="help_landline" value="Landline number is left blank or is invalid"></input>
				</td>
			</tr>
			<tr id="regrow_mobile">
				<td colspan="2">
					<input type="hidden" id="reg_mobile"></input>
					<input type="hidden" id="help_mobile" value=""></input>
				</td>
			</tr>
			<tr id="regrow_email">
				<td>Email <span class="redtext">*</span></td>
				<td>
					<input type="text" id="reg_email" maxlength="32" size="30"></input>
					<input type="hidden" id="help_email" value="Email Id is left blank or is invalid"></input>
				</td>
			</tr>
			<tr id="regrow_sendcode">
				<td></td>
				<td>
					<input type="button" id="reg_sendcode" value="Send Authentication Code to My Email ID" onclick="sendAuthenticationCode();"></input>
				</td>
			</tr>
			<tr id="regrow_authentication">
				<td>Authentication Code <span class="redtext">*</span></td>
				<td>
					<input type="text" id="reg_authentication" maxlength="8" size="8"></input>
					<input type="hidden" id="help_authentication" value="Email authentication code invalid"></input>
				</td>
			</tr>
			<tr id="regrow_scheme">
				<td colspan="2">
					<input type="hidden" id="reg_scheme" value="<%=Constants.PLATINUM_SCHEME%>"></input>
					<input type="hidden" id="help_scheme" value="Scheme selection is invalid"></input>
				</td>
			</tr>
			<tr>
				<td colspan="2"><br/><input type="checkbox" id="agreement" onchange="changeSubmitButtonStatus();"/>&nbsp;By clicking on the box I agree that I have read and understood the <a target="_blank" href="TermsOfUse.htm">Terms of Use</a> and <a href="PrivacyPolicy.htm" target="_blank">Privacy Policy</a><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;and unconditionally accept to be legally bound by these.</td>
			</tr>
			<tr height="80">
				<td colspan="2" align="center"><input id="submitButton" type="button" value="Create Network Market" disabled style="color: #999" onclick="registerUser()"></input></td>
			</tr>
		</table>
	</div><%
}finally{
	conn.close();
}
%>