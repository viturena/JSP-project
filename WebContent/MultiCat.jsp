<%@ page import="java.sql.*, com.catgen.*, com.catgen.factories.*, java.util.*"%>
<%@page import="com.catgen.helper.CategoryHelper"%><%
Connection conn = MySqlDB.getDBConnection(getServletContext());
try{
	String catId = request.getParameter("catId");
	if(catId==null || catId.length()==0){
		catId = Constants.XML_CATEGORY_ROOT_KEY;
	}
	List<Category> categories = CategoryFactory.getChildCategories(conn,catId);
	if(categories!=null && categories.size()>0){%>
		Standard Categories<br/><br/><br/><% 
		for(int i=Constants.CHARS_PER_CATEGORY;i<=catId.length();i+=Constants.CHARS_PER_CATEGORY){%>
			<a href="DataBeanPopulator.jsp?link=<%=Constants.MULTI_LEVEL_CATEGORY_LISTING %>&catId=<%=catId.substring(0,i)%>"><%=CategoryFactory.getCategoryNameByCategoryKeyFromXml(conn,catId.substring(0,i)) %></a>&nbsp;&gt;&nbsp; 
		<%} %><br/><br/>
		<table cellspacing="1" cellpadding="10" width="800">
				<tr>
					<td bgcolor="#1B295F">
						<span style="color:#fff">Category Name</span>
					</td>
					<td bgcolor="#1B295F">
						<span style="color:#fff">Category Key</span>
					</td>
				</tr>
			<%for(Category category: categories){%>
				<tr>
					<td bgcolor="#E8F2FE"><%
						if(CategoryHelper.hasSubCategory(conn,category.categoryKey)){ %>
							<a href="DataBeanPopulator.jsp?link=<%=Constants.MULTI_LEVEL_CATEGORY_LISTING %>&catId=<%=category.categoryKey%>"><%=category.categoryValue %></a>
						<% }else{ %>
							<%=category.categoryValue %>
						<%} %>
					</td>
					<td bgcolor="#E8F2FE">
						<%=category.categoryKey %>
					</td>
				</tr>
			<%}%>
		</table>
	<%}else{%>
		<div style="font-size: 16px; color:#1B295F"><b>No Categories Found</b></div>
	<%}
}finally{
	conn.close();
}%>