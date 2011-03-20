<%@page contentType="text/csv" %><%@page import="java.util.Date" %><%@ page
	import="java.util.*, org.apache.commons.lang.*,java.sql.*,com.catgen.*,com.catgen.factories.*,java.net.URLEncoder, java.text.*"%><%
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
		SimpleDateFormat columnDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");		
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);		
		String m = request.getParameter("referralid");
		if(m != null && m.length() > 0)
		{
			referrals = ReferralFactory.getReferralInfoByEmail(conn, m, startDate, endDate2);
		}		
		if(m != null && m.length() > 0)
		{
			String filename = m +"-";
			filename += searchDateFormat.format(startDate) +"-";
			filename += searchDateFormat.format(endDate);
			filename += ".xls";
			response.setHeader("Content-disposition","attachment;filename="+filename);
		}

		if (referrals != null && referrals.size() > 0) { 
%><%= "Date/Time" %>	<%= "Ref ID" %>	<%= "Market" %>	<%= "Company" %>	<%= "Client IP" %>	<%= "Event" %>	<%= "Extra Data" %>	<%= "Level" %>
<%
		int ii = 0;
	 String rownoname = "" ;
    	for( Referral referral : referrals ) {
		if ( !("Inquiry".equals(referral.ReferralEvent) || "Paypal".equals(referral.ReferralEvent) || "ASP".equals(referral.ReferralEvent) || "2CO".equals(referral.ReferralEvent))  )
		{			
    		ii++;
			String refextraData = referral.ExtraData.replaceAll("extra_","");
			refextraData = refextraData.replaceAll("\n", ", ");

		if(referral.ReferralDate != null) { %><%= columnDateFormat.format(referral.ReferralDate) %><% } %>	<%= StringEscapeUtils.escapeHtml(referral.Email) %>	<%= StringEscapeUtils.escapeHtml(referral.NetworkMarketID) %>	<%= StringEscapeUtils.escapeHtml(referral.CompanyCode) %>	<%= StringEscapeUtils.escapeHtml(referral.ClientIP) %>	"<%= StringEscapeUtils.escapeHtml(referral.ReferralEvent) %>"	"<%= StringEscapeUtils.escapeHtml(refextraData) %>"	<%= "" + referral.Level %>
<% 		}
		}
	} 
	} finally {
		conn.close();
	}
%>