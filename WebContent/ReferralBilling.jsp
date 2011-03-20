<%@ page import="java.sql.*, com.catgen.*, com.catgen.factories.*, java.util.*"%>
<%@page import="java.text.*"%>
<%@page import="java.util.Date"%>

<input type="hidden" id="module" value="<%=Constants.REFERRALS%>">
<input type="hidden" id="getReportList" value="<%=Constants.GET_REPORT_LIST %>"/>
<input type="hidden" id="creditBalanceReport" value="<%=Constants.CREDIT_BALANCE_REPORT%>">
<input type="hidden" id="creditPurchaseReport" value="<%=Constants.CREDIT_PURCHASE_REPORT%>">
<input type="hidden" id="billingSummaryReport" value="<%=Constants.BILLING_SUMMARY_REPORT%>">
<input type="hidden" id="detailedBillingReport" value="<%=Constants.DETAILED_BILLING_REPORT%>">
<input type="hidden" id="pointer" value="1"/>
<input type="hidden" id="color1" value="#ffffff"/>
<input type="hidden" id="color2" value="#C2CCDE"/>
<link rel="stylesheet" type="text/css" media="all" href="jscal/skins/aqua/theme.css" title="win2k-cold-1" />
<script type="text/javascript" src="jscal/calendar.js"></script>
<script type="text/javascript" src="jscal/lang/calendar-en.js"></script>
<script type="text/javascript" src="jscal/calendar-setup.js"></script><% 

Connection conn = MySqlDB.getDBConnection(getServletContext());
try{ 
	SimpleDateFormat searchDateFormat = new SimpleDateFormat("dd/MM/yyyy");

	Calendar startCalendar = Calendar.getInstance(); 
	startCalendar.add( Calendar.DATE, -30);
	Date startDate;
	
	try{
		startDate = searchDateFormat.parse(request.getParameter("startdate"));
	}catch(Exception e){
		startDate = startCalendar.getTime();
	}
	
	Date endDate;
	
	try{
		endDate = searchDateFormat.parse(request.getParameter("enddate"));
	}catch(Exception e){
		endDate = Calendar.getInstance().getTime();
	}		
	
	Calendar cl = Calendar.getInstance();
	cl.setTime(endDate);
	cl.set(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DATE), 23, 59, 59);
	Date endDate2 = cl.getTime();
	
	SimpleDateFormat columnDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
	
	DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
	
	String userId = (String)session.getAttribute("USER");
	String type = (String)session.getAttribute("TYPE");
	
	boolean isValidVendor = CompanyFactory.isValidVendor(userId, type);
	boolean isValidNM = NetMarketFactory.isValidNM(userId, type);
	boolean isValidSuperAdmin = SuperAdminFactory.isSuperAdmin(userId,type);
	boolean isValidReferrer = ReferrerFactory.isValidReferrer(userId, type);
	
	if(isValidNM){
		%><input type="hidden" id="userType1" name="userType1" value="<%=Constants.NETWORK_MARKET%>">
		<input type="hidden" id="userId1" name="userId1" value="<%=userId%>"><%
	}else if(isValidReferrer){
		%><input type="hidden" id="userType1" name="userType1" value="<%=Constants.REFERRER%>">
		<input type="hidden" id="userId1" name="userId1" value="<%=userId%>"><%
	}else if(isValidSuperAdmin){
	}else{
		session.setAttribute("errcode","502");
	}
	
	if(isValidNM || isValidSuperAdmin || isValidReferrer){%>
	<table style="border-width: 1px; border-style: groove; padding: 15px; border-color: red;"><%
		if(isValidSuperAdmin){ %>
		<tr>
			<td width="200">Business Type</td>
			<td width="200">
				<select id="userType1" name="userType1" onchange="getReportList()">
					<option value="">--- Select ---</option>
					<option value="<%=Constants.VENDOR%>">Vendor</option>
					<option value="<%=Constants.NETWORK_MARKET%>">Network Market</option>
					<option value="<%=Constants.REFERRER%>">Referrer</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>User ID</td>
			<td><input size="12" type="text" id="userId1" name="userId1"></td>
		</tr>
		<tr>
			<td>Report Type</td>
			<td>
				<select name="repolist" id="repolist" onchange="checkDateDiv()">
					<option value="">--- Select ---</option>
				</select>
			</td>
		</tr><%}else{%>
		<tr>
			<td width="200">Report Type</td>
			<td width="200">
				<select name="repolist" id="repolist" onchange="checkDateDiv()">
					<option value="">--- Select ---</option>
					<option value="100">Summary Billing Report</option>
					<option value="101">Detailed Billing Report</option>
				</select>
			</td>
		</tr>
		<%}%>
		<tr>
			<td colspan="2">
				<div id="optdiv" style="display:none;">
					<table border="0" cellpadding="0">
						<tr>
							<td width="200">Start Date(dd/mm/yyyy)</td>
							<td width="200"><input style="text-align: center;"  size="8" type="text" id="startdate" name="startdate" value="<% if(startDate != null) { %><%= searchDateFormat.format(startDate) %><% } %>"><img 
								src="jscal/img.gif" id="f_trigger_s" />
							</td>
						</tr>
						<tr>
							<td>End Date(dd/mm/yyyy)</td>
							<td>
								<input style="text-align: center;" size="8" type="text" id="enddate" name="enddate" value="<% if(endDate != null) { %><%= searchDateFormat.format(endDate) %><% } %>"><img 
								src="jscal/img.gif" id="f_trigger_e" />
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" value="Generate Report" style="padding:5px 40px" onclick="getReferralReport()"></input>
			</td>
		</tr>
	</table>
	<br/><br/><br/>
	<div id="referralCountDiv1"></div>
	<table id="referralTable1" border="0" cellspacing="1" cellpadding="5">
	</table><br/>
	<script type="text/javascript" src="jscal/calendar-init.js"></script>

<script type="text/javascript">
    Calendar.setup({
        inputField     :    "startdate",
        ifFormat       :    "%d/%m/%Y",
        button         :    "f_trigger_s",
        weekNumbers    :    false,
        align          :    "Tl",
        singleClick    :    true
    });
    Calendar.setup({
        inputField     :    "enddate",
        ifFormat       :    "%d/%m/%Y",
        button         :    "f_trigger_e", 
        weekNumbers    :    false,
        align          :    "Tl",
        singleClick    :    true
    });
</script><%
	}
}finally{
	conn.close();
}%>