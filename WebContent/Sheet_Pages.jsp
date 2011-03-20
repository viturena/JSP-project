<%@ page import="java.sql.*, com.catgen.*, com.catgen.factories.*, java.util.*, org.apache.commons.lang.*" %><%
String userId = (String)session.getAttribute("USER");
String type = (String)session.getAttribute("TYPE");
boolean isValidNM = NetMarketFactory.isValidNM(userId, type);
if(!isValidNM){
	session.setAttribute("errcode","504");
}else{ 
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	String marketId = (String)session.getAttribute("USER");
	try{
		List<Page> pages = PageFactory.getPages(conn, marketId);%>
		<script type="text/javascript">
			myEditor = false;
		</script>
		<input type="hidden" id="module" value="<%=Constants.PAGES%>"></input>
		<input type="hidden" id="generatepagelist" value="<%=Constants.POPULATE_PAGES%>"></input>
		<input type="hidden" id="addnewpage" value="<%=Constants.ADD_PAGE%>"></input>
		<input type="hidden" id="getpagedetails" value="<%=Constants.GET_PAGE_DETAILS%>"></input>
		<input type="hidden" id="updatepage" value="<%=Constants.UPDATE_PAGE%>"></input>
		<input type="hidden" id="hidepage" value="<%=Constants.HIDE_PAGE%>"></input>
		<input type="hidden" id="removepage" value="<%=Constants.REMOVE_PAGE%>"></input>
		<input type="hidden" id="movepageup" value="<%=Constants.MOVE_PAGE_UP%>"></input>
		<input type="hidden" id="movepagedown" value="<%=Constants.MOVE_PAGE_DOWN%>"></input>
		<input type="hidden" id="currentrow" value="0"></input>
		<input type="hidden" id="pagebuffersize" value="0"></input>
		<table cellspacing="0">
			<tr>
				<td align="right">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr valign="middle">
							<td><input type="text" size="20" maxlength="255" style="color:#888" value="Enter new page name" id="newPageName" onclick="this.value=''"></input></td>
							<td><input type="image" src="images/common/Add.jpg" title="Add a new page" onclick="addNewPage('<%=marketId %>')"></input></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr height="50">
				<td/>
			</tr>
			<tr valign="top">
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr valign="middle">
							<td>
								Page Name: 
								<span id="pagesDropdown">
									<select id="pagelist" onchange="getPageDetails('<%=marketId %>');document.getElementById('rtEditor').style.visibility = 'visible';">
										<option value="">Select Page</option><%
										for(Page pg: pages){%>
											<option value="<%=pg.RowID%>"><%=pg.Name %></option><%
										}%>
									</select>
									<a href="javascript:void(0);" 
									onclick="myEditor = getRichEditor();document.getElementById('rtEditor').style.visibility = 'hidden';" 
									id="rtEditor">HTML Editor</a>
								</span>
							</td>
							<%--<td align="left"><input type="text" style="font-weight: bold" id="pages_name" maxlength="255" disabled></input></td> --%>
							<td align="right">
								<input type="image" src="images/common/MoveUp.jpg" title="Move the loaded page up." onclick="movePageUp('<%=marketId %>')"></input>
								<input type="image" src="images/common/MoveDown.jpg" title="Move the loaded page down" onclick="movePageDown('<%=marketId %>')"></input>
								<input type="image" src="images/common/Save.jpg" title="Save changes" onclick="updatePage('<%=marketId %>',myEditor)"></input>
								<input type="image" src="images/common/NotHidden.jpg" id="hideit" title="Hidden status" onclick="hidePage('<%=marketId %>')"></input>
								<input type="image" src="images/common/Remove.jpg" title="Remove this page" onclick="removePage('<%=marketId %>')"></input>
							</td>
						</tr>
						<tr>
							<td colspan="2"><div id="pages_description">
							<textarea name="pagedesc" id="pagedesc" cols="160" rows="24"></textarea>
							</div></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	<% } finally{
		conn.close();
	}
}%>