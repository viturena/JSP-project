<%@ page import="java.util.*, org.apache.commons.lang.*,java.sql.*,com.catgen.*,com.catgen.factories.*,java.net.URLEncoder,java.text.*"%>
<input type="hidden" id="module" value="<%= Constants.SUPER_ADMIN %>">
<input type="hidden" id="deleteSelectedCompanies" value="<%= Constants.DELETE_SELECTED_COMPANIES %>">
<%

	int pointer = 0;
	String[] color={"#ffffff","#C2CCDE"};
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try {
		String key = request.getParameter("key");
		String userId = (String)session.getAttribute("USER");
		String type = (String)session.getAttribute("TYPE");
		int totalProductCount = 0;
		boolean isSuperAdmin = SuperAdminFactory.isSuperAdmin(userId, type);
		if(!isSuperAdmin){
			session.setAttribute("errcode","501");
		}else{
			List<Company> companies = CompanyFactory.getAllCompanies(conn);%>
			<table border="0" cellspacing="2" cellpadding="5">
				<tr style="background-color:#324F81; color: #fff;">
					<td width="30">Delete</td>
					<td width="30">Company Code</td>
					<td width="30">Company URL</td>
					<td width="140">Company Name</td>
					<td width="100">Email ID</td>
					<td width="30">Product Count</td>
				</tr><%
			for(Company company: companies){
				int productCount = ProductFactory.getVendorProductCount(conn,company.Code);
				totalProductCount += productCount;%>
				<tr style="background-color:<%=color[pointer]%>"><%
					String catalogUrl = company.CompanyURL;
					if(!(catalogUrl!=null && catalogUrl.length()>0)){
						catalogUrl="";
					}%>
					<td><input type="checkbox" name="companies" value="<%=company.Code%>" /></td>
					<td><a target="_blank" href="<%=catalogUrl%>"><%=company.Code %></a></td>
					<td><a target="_blank" href="<%=catalogUrl%>"><%=catalogUrl %></a></td>
					<td><%=company.Name %></td>
					<td><%=(company.ContactEmail!=null)?company.ContactEmail:"-" %></td>
					<td><%=productCount %></td>
				</tr>
			<%
			pointer=(pointer==1)?0:1;
			}%>
				<tr>
					<td align="left" colspan="6"><input type="button" value="Delete" onClick="deleteSelectedCompanies()" /></td>
				</tr>
				<tr style="background-color:#324F81; color: #fff;">
					<td align="center" colspan="6"><%=companies.size()%>&nbsp; Companies, <%=totalProductCount %>&nbsp; Products.</td>
				</tr>
			</table><%
		}
	} finally {
		conn.close();
	}
%>