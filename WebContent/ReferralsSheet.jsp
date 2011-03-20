<%@ page contentType="application/vnd.ms-excel" %>
<%@page import="java.sql.Connection"%>
<%@page import="com.catgen.factories.MySqlDB"%>
<%@page import="com.catgen.helper.ReferralsHelper"%>
<%@page import="java.util.List"%>
<%@page import="com.catgen.Referral"%>
<%@page import="com.catgen.Utils"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
	String type=request.getParameter("userType");
	String userId=request.getParameter("userId");
	String strStartDate=request.getParameter("startdate");
	String strEndDate=request.getParameter("enddate");
	String filename="Referrals_"+userId+"_"+strStartDate+"_to_"+strEndDate+".xls";
	filename = filename.replaceAll("/","");
	response.setHeader("Content-disposition","attachment;filename="+filename);
%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.Calendar"%><html>
<head>
<title>Referrals Report</title>
</head>
<body>
<table>
	<tr>
		<td>Date/Time</td>
	    <td>Ref ID</td>
	    <td>Market</td>
	    <td>Company</td>
	    <td>Client IP</td>
	    <td>Event</td>
	    <td>Extra Data</td>
	    <td>Level</td>
	    <td>City</td>
	    <td>Country</td>
	    <td>Referral By</td>
	</tr><%
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try{
		Date startDate = Utils.getDatefromString(null, strStartDate);
		Date endDate = Utils.getDatefromString(null, strEndDate);

		Calendar cal = Calendar.getInstance();
		cal.setTime (endDate);
		cal.add (Calendar.DATE, 1);
		endDate = cal.getTime();
		
		SimpleDateFormat columnDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		List<Referral> referrals = ReferralsHelper.getReferralsByType(conn, userId, startDate, endDate, Integer.parseInt(type));
		if(referrals!=null && referrals.size()>0){
			for(Referral referral:referrals){%>
				<tr>
					<td><% if(referral.ReferralDate != null) { %><%= columnDateFormat.format(referral.ReferralDate) %><% } %></td>
					<td><%= StringEscapeUtils.escapeHtml(referral.Email) %></td>
					<td><%= StringEscapeUtils.escapeHtml(referral.NetworkMarketID) %></td>
					<td><%= StringEscapeUtils.escapeHtml(referral.CompanyCode) %></td>
					<td><%= StringEscapeUtils.escapeHtml(referral.ClientIP) %></td>
					<td><%= StringEscapeUtils.escapeHtml(referral.ReferralEvent) %></td>
					<td><%= StringEscapeUtils.escapeHtml(referral.ExtraData) %></td>
					<td><%= referral.Level %></td>
					<td><%= (null==referral.City)?"":StringEscapeUtils.escapeHtml(referral.City) %></td>
					<td><%= (null==referral.Country)?"":StringEscapeUtils.escapeHtml(referral.Country) %></td>
					<td><%
						if(referral.Bot){%>
							Bot
						<%}else{%>
							Human						
						<%}%>
					</td>
				</tr>
			<%}
		}
	}catch(Exception e){
		e.printStackTrace();	
	}finally{
		conn.close();
	}%>
</table>
</body>
</html>