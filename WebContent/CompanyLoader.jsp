<%@ page import="java.util.*, org.apache.commons.lang.*,java.sql.*,com.catgen.*,com.catgen.factories.*,java.net.URLEncoder,java.text.*"%><%
	int pointer = 0;
	String[] color={"#ffffff","#C2CCDE"};
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try {
		String key = request.getParameter("key");
		String userId = (String)session.getAttribute("USER");
		String type = (String)session.getAttribute("TYPE");
		boolean isSuperAdmin = SuperAdminFactory.isSuperAdmin(userId, type);
		if(!isSuperAdmin){
			session.setAttribute("errcode","501");
		}else{
			if("1".equals(request.getParameter("action"))){	
				String m = request.getParameter("master");
				if(m != null && m.length() > 0){
					%><h1>MASTER COMPANIES IMPORT FUNCTIONALITY DISABLED</h1><br/><h3>Only individual companies can be imported.</h3><%
					//MasterCompanyFactory.updateMasterCompanies(conn);
				}else{
					for(String companyCode : request.getParameterValues("companycode") )
					{
						CompanyFactory.UpdateCompany(conn, companyCode);
					}
				}
			}%>
			<b>Company list is paginated alphabetically.</b><br/>
			<i>Click on the Starting character of company code to jump to the corresponding page.</i><br/><br/><%
			for(int i=0;i<=9;i++){%>
	    		<a href="DataBeanPopulator.jsp?link=<%=Constants.COMPANY_LOADER %>&startsWith=<%=i%>"><%=i %></a>&nbsp;|&nbsp;
	    	<%}
	    	for(int i='A';i<='Z';i++){%>
	    		<a href="DataBeanPopulator.jsp?link=<%=Constants.COMPANY_LOADER %>&startsWith=<%=(char)i%>"><%=(char)i %></a>&nbsp;|&nbsp;
	    	<%}%>
	    	<br/><br/><br/><%
	    	String startsWith = request.getParameter("startsWith");
	    	if(startsWith!=null && startsWith.length()>0){ 
				List<MasterCompany> companies;
				if(Constants.COMPANY_UPDATE_KEY.equals(key)){
					companies = MasterCompanyFactory.getMasterCompanies(conn);
				}else{
					companies = MasterCompanyFactory.getMasterCompaniesStartingWith(conn, startsWith);
				}
				DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
				response.setHeader("Expires", df.format(Calendar.getInstance().getTime() ));%>
				<form action="DataBeanPopulator.jsp?link=<%=Constants.COMPANY_LOADER%><%=(startsWith!=null && startsWith.length()>0)?"&startsWith="+startsWith:"" %>" method="post">
				<input type="hidden" name="action" value="1"/>
			    <!-- <input type="submit" name="master" value="Update Master Companies"/> --><% 
		    	java.util.Date date = new java.util.Date();%>
	    		<br/><br/>
			    <table border="0" cellspacing="2" cellpadding="3">
			    	<tr style="background-color:#324F81; color: #fff">
			    		<td width="30"></td>
			    		<td width="100" align="left"><font face="Verdana" color="#ffffff" size="2">Company Code</font></td>
			    		<td width="100" align="left"><font face="Verdana" color="#ffffff" size="2">Company Name</font></td>
			    		<td width="100" align="left"><font face="Verdana" color="#ffffff" size="2">Email ID</font></td>
			    		<td width="100" align="left"><font face="Verdana" color="#ffffff" size="2">Product Count</font></td>
			    	</tr><%
		    	 	for( MasterCompany company : companies ) {
		    	 		if(Constants.COMPANY_UPDATE_KEY.equals(key)){
			    			CompanyFactory.UpdateCompany(conn, company.companyCode);
			    		}
			    		String companyCode = company.companyCode;
			    		String url=Constants.OE_URL;
			    		String email="-";
			    		String name="-";
			    		int prodCount = 0;
			    		if(companyCode!=null){
			    			Company companyObj = CompanyFactory.getCompanyByCode(conn, companyCode);
			    			if(companyObj!=null){
			    				if(companyObj.CompanyURL!=null){
			    					url = companyObj.CompanyURL;	
			    				}
			    				if(companyObj.ContactEmail!=null){
			    					email = companyObj.ContactEmail;
			    				}
			    				if(companyObj.Name!=null){
			    					name = companyObj.Name;
			    				}
			    				prodCount = ProductFactory.getVendorProductCount(conn, companyCode);
			    			}
			    		}%>
				    	<tr style="background-color:<%=color[pointer]%>">
					        <td><input type="checkbox" name="companycode" value="<%= StringEscapeUtils.escapeHtml(company.companyCode) %>"/></td> 
					        <td><a href="<%=url%>"><%= StringEscapeUtils.escapeHtml(company.companyCode) %></a></td>
					        <td><%=name %></td>
					        <td><%=email %></td>
					        <td><%=prodCount %>&nbsp;products</td>
					    </tr>
				 	<% 	if(pointer==1){
				 			pointer=0;
					 	}else{
					 		pointer=1;
					 	}
				 	}%>
			 		<tr style="background-color:#324F81; color: #fff">
			 			<td align="center" colspan="5">Total Vendor Count: <%=companies.size()%>.</td>
			 		</tr>
			 	</table><% 
			    if (companies != null && companies.size() > 0) { %>
				    <br><input type="submit" value="Update selected"/>
			    <%}
			} %>
		    </form><%
		}
	} finally {
		conn.close();
	}
%>