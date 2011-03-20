<%@ page
	import="java.util.*, org.apache.commons.lang.*,java.sql.*,com.catgen.*,com.catgen.factories.*,java.net.URLEncoder, java.text.*, com.catgen.Constants"%>
<%
	List<Referral> referrals = null;

	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try {
		SimpleDateFormat searchDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		Calendar startCalendar = Calendar.getInstance(); 
		startCalendar.add( Calendar.DATE, -30);

		Date startDate;
		
		try
		{
			startDate = searchDateFormat.parse(request.getParameter("startdate"));
		}
		catch(Exception e)
		{
			startDate = startCalendar.getTime();
		}
		
		Date endDate;
		
		try
		{
			endDate = searchDateFormat.parse(request.getParameter("enddate"));
		}
		catch(Exception e)
		{
			endDate = Calendar.getInstance().getTime();
		}		
		
		Calendar cl = Calendar.getInstance();
		cl.setTime(endDate);
		cl.set(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.get(Calendar.DATE), 23, 59, 59);
		Date endDate2 = cl.getTime();
		
		SimpleDateFormat columnDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
		
		if("1".equals(request.getParameter("action")))
		{
			String m = request.getParameter("companyid");
			if(m != null && m.length() > 0)
			{
				referrals = ReferralFactory.getReferralInfoByCompany(conn, m, startDate, endDate2);
			}
		}
		
		
		response.setHeader("Expires", df.format(Calendar.getInstance().getTime() ));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Date"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>OpenEntry Referral Reports - from Referrers to Companies</title>
<style>
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
}
.header {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 16px;
	font-weight: bold;
	text-align: center;
	background-color: #56aaf6;
}
.row1 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	background-color: #fff;
}
.row2 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	background-color: #eeecdb;
}
</style>
<link rel="stylesheet" type="text/css" media="all" href="jscal/skins/aqua/theme.css" title="win2k-cold-1" />
<script type="text/javascript" src="jscal/calendar.js"></script>
<script type="text/javascript" src="jscal/lang/calendar-en.js"></script>
<script type="text/javascript" src="jscal/calendar-setup.js"></script>
</head>
<body>
<center><h3>OpenEntry Referral Reports - from Referrers to Companies  ( <a href="ReferralsMarket.jsp">Network Markets</a> | <a href="Referrals.jsp">Referrers</a> ) </h3></center>

<form action="ReferralsCompany.jsp" method="post">
<table width=70% align=center>
<tr>
<td>
<input type="hidden" name="action" value="1"/>
Company code:  
<input type="text" name="companyid" value="<%= StringEscapeUtils.escapeHtml( Utils.getSafeString( request.getParameter("companyid" ))) %>"/> 
</td>
<td>Start (dd/mm/yy): </td><td><input style="text-align: center;"  size="8" type="text" id="startdate" name="startdate" value="<% if(startDate != null) { %><%= searchDateFormat.format(startDate) %><% } %>"><img 
src="jscal/img.gif" id="f_trigger_s" style="cursor: pointer; border: 0px solid
red;" title="Date selector" onmouseover="this.style.background='red';"
onmouseout="this.style.background=''" /></td>
<td>End (dd/mm/yy): </td><td><input style="text-align: center;" size="8" type="text" id="enddate" name="enddate" value="<% if(endDate != null) { %><%= searchDateFormat.format(endDate) %><% } %>"><img 
src="jscal/img.gif" id="f_trigger_e" style="cursor: pointer; border: 0px solid
red;" title="Date selector" onmouseover="this.style.background='red';"
onmouseout="this.style.background=''" /></td>
<td><input type="submit" value="Show Referrals"/></td>
</table>
</form>

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
</script>

    <br/><br/>
	<%
    if (referrals != null && referrals.size() > 0)
	{    
    %>
	<center>
	Check where in the world people are browsing from by copying the Client IP into <a href="http://WhatIsMyIPAddress.com" target="_blank">http://WhatIsMyIPAddress.com</a><br/><br /><b><%= referrals.size() %></b> Referrals for <b>"<%= request.getParameter("companyid" ) %>"</b> between <b><%= searchDateFormat.format(startDate) %></b> and <b><%= searchDateFormat.format(endDate) %></b> 
	<a href="ReferralsCompany_txt.jsp?companyid=<%= request.getParameter("companyid" ) %>&startdate=<%= searchDateFormat.format(startDate) %>&enddate=<%= searchDateFormat.format(endDate) %>">Download as Tab Delimited Excel File</a></center><br />
    <table border="0" align=center width=85%>
    <tr class="header">
    <td width=150>Date/Time</td>
    <td>Ref ID</td>
    <td>Market</td>
    <td>Company</td>
    <td>Client IP</td>
    <td>Event</td>
    <td>Extra Data</td>
    <td>Level</td>
    <td>City</td>
    <td>Country</td>
    </tr>
    <%
		int ii = 0;
		String rownoname = "" ;
    	for( Referral referral : referrals )
		{
			if ( !("Inquiry".equals(referral.ReferralEvent) || "Paypal".equals(referral.ReferralEvent) || "ASP".equals(referral.ReferralEvent) || "2CO".equals(referral.ReferralEvent))  )
			{			
				
				MasterMarket networkMarket1 = MasterMarketFactory.getMasterMarketByCode(conn, referral.NetworkMarketID);
				String nmurl = networkMarket1.DomainName;
				String nmurl1 = nmurl.replaceAll("/",Constants.OE_URL+"/");
				
				Company marketMember1 = CompanyFactory.getCachedCompanyByCode(conn, referral.CompanyCode);
				
				String nnurl = "http://main.openentry.com/"+ referral.CompanyCode;				
				if (marketMember1 != null)
				{
					nnurl = marketMember1.CompanyURL;
				}
				
				if (nnurl != null && nnurl.length() > 0)
				{
					String nnurl1 = nnurl;
				}
				else
				{
					String nnurl1 = "http://"+Constants.OE_URL+"/"+ referral.NetworkMarketID +"/"+ referral.CompanyCode;
				}

				rownoname = (rownoname.equalsIgnoreCase("row1") ? "row2" : "row1");
				ii++;
    %>  
    <tr class='<%= rownoname %>'>
    <td>&nbsp;<% if(referral.ReferralDate != null) { %><%= columnDateFormat.format(referral.ReferralDate) %><% } %></td>
    <td>&nbsp;<%= StringEscapeUtils.escapeHtml(referral.Email) %></td>
    <td>&nbsp;<a href="http://<%= nmurl1 %>" target="_blank"><%= StringEscapeUtils.escapeHtml(referral.NetworkMarketID) %></a></td>
    <td>&nbsp;<a href="<%= nnurl %>" target="_blank"><%= StringEscapeUtils.escapeHtml(referral.CompanyCode) %></a></td>
    <td>&nbsp;<%= StringEscapeUtils.escapeHtml(referral.ClientIP) %></td>
    <td>&nbsp;<%= StringEscapeUtils.escapeHtml(referral.ReferralEvent) %></td>
 	<%
 	String refextraData = referral.ExtraData;
 	refextraData = (refextraData!=null)?refextraData:"";
	refextraData = refextraData.replaceAll("extra_","");
	refextraData = refextraData.replaceAll("\n", ", ");
	%>
   <td>&nbsp;<%= StringEscapeUtils.escapeHtml(refextraData) %></td>
    <td>&nbsp;<%= "" + referral.Level %></td>
    <td>&nbsp;<%= (null==referral.City)?"":StringEscapeUtils.escapeHtml(referral.City) %></td>
    <td>&nbsp;<%= (null==referral.Country)?"":StringEscapeUtils.escapeHtml(referral.Country) %></td>
    </tr>
    <% 		
			}
		}
    %>
    <tr class="header">
    <td>Total:</td>
    <td colspan="3"><%= "" + ii + " referral events" %></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    </tr>
    </table>
    <%
	} 
	else if("1".equals(request.getParameter("action")))
	{
	%>
		<span style="font-size: x-large">No referrals found</span>
	<%
	}
	%>

</body>
</html>

<%
	} finally {
		conn.close();
	}
%>