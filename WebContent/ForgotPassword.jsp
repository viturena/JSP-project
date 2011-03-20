<%@page import="com.catgen.Constants"%>
<input type="hidden" id="module" value="<%=Constants.USER_SESSION %>"/>
<input type="hidden" id="forgotpassword" value="<%=Constants.FORGOT_PASSWORD %>"/>
<h3>Forgot Password</h3>
<p style="line-height: 20px;">Password can only be reset for accounts available in OpenEntry Network Market Management System.<br/>
This requires submitting the below form with business type and name, along with the Email ID with <br/>
which the account is registered.</p><br/>
<table cellspacing="12" cellpadding="0" border="0" bgcolor="#E8F2FE" width="400" align="center">
	<tr>
		<td>Registered Email ID</td>
		<td><input type="text" id="forgot_email" size="26"/></td>
	</tr>
	<tr>
		<td>Business Type</td>
		<td>
			<input type="radio" name="forgot_type" value="<%=Constants.NETWORK_MARKET %>" checked="checked"/>Network Market
			<input type="radio" name="forgot_type" value="<%=Constants.VENDOR %>"/>Catalog
		</td>
	</tr>
	<tr>
		<td>User ID</td>
		<td><input type="text" id="forgot_name" size="26"/></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="submit" value="Send new password to my Email ID" style="padding: 3px 20px;" onclick="forgotPassword()"/></td>
	</tr>
</table>
<br/><br/><br/><br/>