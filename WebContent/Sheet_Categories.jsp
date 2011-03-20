<%@ page import="java.sql.*, com.catgen.*, com.catgen.factories.*, java.util.*, org.apache.commons.lang.*" %>
<%@page import="com.catgen.helper.CategoryHelper"%><%
Connection conn = MySqlDB.getDBConnection(getServletContext());
String marketId = (String)session.getAttribute("USER");
try{
	String userId = (String)session.getAttribute("USER");
	String type = (String)session.getAttribute("TYPE");
	boolean isValidNM = NetMarketFactory.isValidNM(userId, type);
	if(!isValidNM){
		session.setAttribute("errcode","504");
	}else{ 
		List<Category> categories = CategoryFactory.getCategories(conn, marketId);%>
		<% if(request.getParameter("submitcategory")!=null){ %>
		<% String[] StaticCategories; 
		StaticCategories = request.getParameterValues("staticCategories");
		CategoryFactory.saveMarketCategories(conn,StaticCategories,marketId);
		%> 
		<%} %>
		<%String catId = request.getParameter("catId");
		if(catId==null || catId.length()==0){
			catId = Constants.XML_CATEGORY_ROOT_KEY;
		}
		List<Category> staticCategories = CategoryFactory.getChildCategories(conn,catId); 
		if(categories!=null && categories.size()>0){%>
		<table cellspacing="1" cellpadding="10" width="800">
		
				<tr>
					<td bgcolor="#1B295F">
						<span style="color:#fff">Category Name</span>
					</td>
					<td bgcolor="#1B295F">
						<span style="color:#fff">Category Key</span>
					</td>
				</tr>
		<form name="existingCategoryForm" action="DataBeanPopulator.jsp?link=<%=Constants.CATEGORIES %>&catId=" method="POST">
			<%for(Category category: staticCategories){%>
				<tr>
					<td bgcolor="#E8F2FE">
					<INPUT TYPE=CHECKBOX NAME="staticCategories"  value="<%=category.categoryKey %>">
					<%
						if(CategoryHelper.hasSubCategory(conn,category.categoryKey)){ %>
							<a href="DataBeanPopulator.jsp?link=<%=Constants.CATEGORIES %>&catId=<%=category.categoryKey%>"><%=category.categoryValue %></a>
						<% }else{ %>
							<%=category.categoryValue %>
						<%} %>
					</td>
					<td bgcolor="#E8F2FE">
						<%=category.categoryKey %>
					</td>
				</tr>
			<%}%>
			<tr>
				<td>
					<input type="submit" name="submitcategory" value="Add Category to My list">
				</td>
			</tr>  
			</FORM>
		</table>
		
		<%} %>
		<input type="hidden" id="module" value="<%=Constants.CATEGORIES%>"></input>
		<input type="hidden" id="removecategory" value="<%=Constants.REMOVE_CATEGORY%>"></input>
		<input type="hidden" id="editcategory" value="<%=Constants.EDIT_CATEGORY %>"></input>"
		<input type="hidden" id="addcategory" value="<%=Constants.ADD_CATEGORY%>"></input>
		<h2>Existing Categories</h2>
		<table cellspacing="5" width="100%"><%
			int productCount = categories.size();
			if(productCount==0){
				%><tr><td><div id="topdiv2"></div></td></tr><%
			}
			int i=1;
			for(Category category: categories){ %>
				<tr>
					<td>
						<div id="cat_<%=category.categoryKey%>">
							<table width="100%" cellspacing="0" cellpadding="0" border="0">
								<tr>
									<td width="50">
										<input type="image" src="images/common/Remove.jpg" 
										onclick="removeCategory('<%=marketId %>','<%=category.categoryKey %>')">
									</input>
									</td>
									<td width="120"><%=category.categoryKey %></td>
									<td width="200">
									<span id="categoryValueOnly<%=category.categoryKey %>"><%=category.categoryValue %></span>
									<span id="categoryValueTextbox<%=category.categoryKey %>" style="display: none;">
									<input size="30" type="text" id="categoryValue<%=category.categoryKey %>" value="<%=category.categoryValue %>" /></span>
									</td>
									<td width="100">
									<span id="categoryEdit<%=category.categoryKey %>">
										<a href="javascript:void(0);" 
										onclick="document.getElementById('categoryEdit<%=category.categoryKey %>').style.display='none';
										document.getElementById('categoryUpdate<%=category.categoryKey %>').style.display='block';
										document.getElementById('categoryValueOnly<%=category.categoryKey %>').style.display='none';
										document.getElementById('categoryValueTextbox<%=category.categoryKey %>').style.display='block';">
										Edit</a>
									</span>
									<span id="categoryUpdate<%=category.categoryKey %>" style="display: none;">
										<a href="javascript:void(0);" 
										onclick="editCategory('<%=marketId %>','<%=category.categoryKey %>');
										document.getElementById('categoryUpdate<%=category.categoryKey %>').style.display='none';
										document.getElementById('categoryEdit<%=category.categoryKey %>').style.display='block';
										document.getElementById('categoryValueOnly<%=category.categoryKey %>').style.display='block';
										document.getElementById('categoryValueTextbox<%=category.categoryKey %>').style.display='none';">
											Update
										</a>
									</span>
										
									</td>
								</tr>
							</table>
						</div><%
						if(i==productCount){
							%><div id="topdiv2"></div><%
						}%>
					</td>
				</tr><%
				i++;
			} %>
		</table>
		<br/><br/><br/></br>
		<h2>Add a New Category</h2>
		<table cellspacing="10">
			<tr>
				<td>Search Key</td>
				<td>
					<input type="text" id="catKey" maxlength="32"></input>
				</td>
			</tr>
			<tr>
				<td>Display Value</td>
				<td><input type="text" id="catValue" maxlength="64"></input></td>	
			</tr>
			<tr>
				<td colspan="2">
					<center><input type="button" value="Add Category" onclick="addCategory('<%=marketId %>')"></input></center>
				</td>	
			</tr>
		</table>
		<%}
	} finally{
	conn.close();
}%>