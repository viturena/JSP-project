
<%@page import = "com.catgen.*,com.catgen.factories.*,com.catgen.helper.*,org.apache.commons.lang.StringEscapeUtils,org.apache.catalina.util.*,java.util.List,java.util.HashMap,java.util.*,java.net.URLEncoder,java.sql.Connection" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%
CatgenPageContext catgenContext = CatgenPageContext.getInstance(pageContext);
Connection conn = MySqlDB.getDBConnection(getServletContext());
try{
	NetworkMarket networkMarket = catgenContext.NetworkMarket;
	if (networkMarket == null) {
	    response.sendRedirect("CompanyNotFound.jsp");
	}
	TemplatePropertiesBean templatePropertiesBean = TemplateInitializer.initializeTemplateProperties(catgenContext, conn, networkMarket.NetworkMarketID);
	Style style = templatePropertiesBean.style;
	catgenContext.PageSize = style.productSearchRowCount * style.productSearchColumnCount;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="keywords" content="<%=networkMarket.Keywords %>" />
<script type="text/javascript" src="/js/balloon/balloon.config.js"></script>
		 <script type="text/javascript" src="/js/balloon/balloon.js"></script>
		 <script type="text/javascript" src="/js/balloon/box.js"></script>
		 <script type="text/javascript" src="/js/balloon/yahoo-dom-event.js"></script>
		<script type="text/javascript">
 		    var plainBox         = new Box();
		    plainBox.fontColor          = 'black';                       //
		    plainBox.fontFamily         = 'Arial, sans-serif';           //
		    plainBox.fontSize           = '12pt';                        //
		    plainBox.minWidth           = 400;                           //
		    plainBox.maxWidth           = 550;                           //
		    plainBox.delayTime          = 450;                           //
		    plainBox.vOffset            = 10;                            //
		    plainBox.hOffset            = 10;                            //
		    plainBox.stem               = true;                          //
		    plainBox.images             = '/images/balloons';             //
		    plainBox.ieImage            = 'balloon_ie.png';              //
		    plainBox.balloonImage       = 'balloon.png';                 //
		    plainBox.upLeftStem         = 'up_left.png';                 //
		    plainBox.downLeftStem       = 'down_left.png';               //
		    plainBox.upRightStem        = 'up_right.png';                //
		    plainBox.downRightStem      = 'down_right.png';              //
		    plainBox.closeButton        = 'close.png';                   //
		    plainBox.closeButtonWidth   = 16;                            //
		    plainBox.allowAJAX          = true;                          //
		    plainBox.allowIframes       = true;                          //
		    plainBox.trackCursor        = true;                          //
		    plainBox.shadow             = 20;                            //
		    plainBox.padding            = 10;                            //
		    plainBox.stemHeight         = 32;                            //
		    plainBox.stemOverlap        = 3;                             //
		    plainBox.vOffset            = 1;                             //
		    plainBox.hOffset            = 1;                             //    
		    plainBox.opacity            = 1;                           //
		    plainBox.configured         = set || true; 
		
		 </script>
        <title>
            <%= StringEscapeUtils.escapeHtml(networkMarket.Name)%>
        </title>
        <style type="text/css">
	     #selectednav{
		  color:#EEEEEE;
font-family:verdana;
font-size:14px;
font-weight:bold;
		}
            .thumbnailContent {
                width: <%= style.thumbnailWidthInPixels + 10 %>px;
                height: <%= style.thumbnailHeightInPixels + 30 %>px;
                background:<%= style.thumbnailBackgroundColor%>;
                color:<%= style.thumbnailTextColor%>
            }
            .thumbnailImage {
                max-width: <%= style.thumbnailWidthInPixels %>px;
                max-height: <%= style.thumbnailHeightInPixels %>px;
                width: expression(this.width > <%= style.thumbnailWidthInPixels %>? "<%= style.thumbnailWidthInPixels %>px": "auto");
                height: expression(this.height > <%= style.thumbnailHeightInPixels %>? "<%= style.thumbnailHeightInPixels %>px": "auto");
            }
            
            .largeImage {
                max-width: 320px;
                max-height: 240px;
                width: expression(this.width > 320 ? "320px" : "auto");
                height: expression(this.height > 240 ? "240px" : "auto");
            }
            body{
                <%= StyleFactory.getBackgroundStyle(style.screenBackgroundImage, style.screenBackgroundColor, style.screenBackgroundRepeat, "#DDD")%>
                <%= StyleFactory.getFontStyle(style.pageFont, style.pageFontSize, style.pageFontColor, "#666")%>
            }

            a #homeAnchor, a:visited #homeAnchor, a:hover #homeAnchor{
                text-decoration: none;
            }
            .page{
                width: 960px;
                <%= StyleFactory.getBackgroundStyle(style.pageBackgroundImage, style.pageBackgroundColor, style.pageBackgroundRepeat, "white")%>
            }
		
            .contents{
                padding:0 20px 10px;
		  text-align:justify;
            }

	     select, input{
		  <%= StyleFactory.getFontStyle(style.pageFont, style.pageFontSize, style.pageFontColor, "#666")%>
		  color:#000;
	     }
            .NavBar{
                height: 32px;
                <%= StyleFactory.getBackgroundStyle(style.navbarBackgroundImage, style.navbarBackgroundColor, style.navbarBackgroundRepeat, "#777")%>
            }

            #NavBarText a{
                font-weight:bold;
                <%= StyleFactory.getFontStyle(style.navbarFont, style.navbarFontSize, style.navbarFontColor, "#EEE")%>
                
            }

            .thumb {
                padding: 5px;
                border:1px solid #b0b0b0;
                background: #fff;
            }

            .Title{
                padding-left: 10px;
                padding-right: 10px;
                font-weight:bold;
                height:32px;
                <%= StyleFactory.getBackgroundStyle(style.titlebarBackgroundImage, style.titlebarBackgroundColor, style.titlebarBackgroundRepeat, "#777")%>
                <%= StyleFactory.getFontStyle(style.titlebarFont, style.titlebarFontSize, style.titlebarFontColor, "#EEE")%>
                
            }

            a{
                
            }
            
            A #openentry {
                background-color:#3B5998; 
                color:#FFF;
                height:30px;
                margin-top:10px;
                font-size:16px;
            }
            A:hover #openentry {
                background-color:#5370AA;
                height: 30px;
                color:#FFF;
                height:30px;
                margin-top:10px;
                font-size:16px;
            }

            .categoryBackground{
                <%= StyleFactory.getBackgroundStyle(style.categoryBackgroundImage, style.categoryBackgroundColor, style.categoryBackgroundRepeat, "#EEE") %>
            }
            .categoryTitle{
                padding-left: 10px;
                padding-top: 10px;
                margin-bottom: 1px;
                font-weight: bold;
                <%= StyleFactory.getBackgroundStyle(style.categoryTitleBackgroundImage, style.categoryTitleBackgroundColor, style.categoryTitleBackgroundRepeat, "#AAA") %>
                <%= StyleFactory.getFontStyle(style.categoryTitleFont, style.categoryTitleFontSize, style.categoryTitleFontColor, "#666") %>
                height: 25px;
            }
            .categoryList{
                height: 25px;
                padding-left: 10px;
                padding-top: 10px;
                margin-bottom:1px;
                <%= StyleFactory.getBackgroundStyle(style.categoryListBackgroundImage, style.categoryListBackgroundColor, style.categoryListBackgroundRepeat, "#CCC") %>
                <%= StyleFactory.getFontStyle(style.categoryListFont, style.categoryListFontSize, style.categoryListFontColor, "#666") %>
            }
            .hoverList{
                height: 25px;
                padding-left: 10px;
                padding-top: 10px;
                margin-bottom:1px;
                <%= StyleFactory.getBackgroundStyle(style.categoryHoverBackgroundImage, style.categoryHoverBackgroundColor, style.categoryHoverBackgroundRepeat, "#666") %>
                <%= StyleFactory.getFontStyle(style.categoryHoverFont, style.categoryHoverFontSize, style.categoryHoverFontColor, "#CCC") %>
            }
        </style>
        <script language="javascript" type="text/javascript" src="/js/common/script.js"></script>
        <script language="javascript" type="text/javascript">
            function img2txt(img){
                img.src = "<%= style.noProductImageURL%>";
            }
        </script>

    </head>

    <body>
        <table class="page" width="842" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td align="center">
                   <a href="<%= catgenContext.RelativePath%>Home.html?singlePageTemplate=true&pagesize=<%=catgenContext.PageSize%>">
                        <div id="homeAnchor">
                            <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td align="left" valign="top">
                                        <%= networkMarket.LogoImage%>
                                    </td> 
                                    <td align="center" valign="top">
                                        <%= networkMarket.Header%>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </a>
                </td>
           </tr>
            <tr>
                <td align="left" valign="middle">
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="NavBar">
                        <tr>
                            <td align="center" valign="middle">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td align="center" id="NavBarText">
							
							<% if (catgenContext.PageName== "Home") { %>
    								<span id="selectednav"><%= style.homeText%></span>
							<% } else { %>
  							  <a href="<%= catgenContext.RelativePath%>Home.html?singlePageTemplate=true&pagesize=<%=catgenContext.PageSize%>"><%= style.homeText%></a> 
							<% } %> 
                                            
                                            <%
                                            if (templatePropertiesBean.mtPages != null && templatePropertiesBean.mtPages.size() > 0) {
                                                for (Page mtPage : templatePropertiesBean.mtPages) {%>
                                                    <a>|</a>
							<% if (catgenContext.PageName.equals(StringEscapeUtils.escapeHtml(mtPage.Name))) { %>
								<span id="selectednav"><%= StringEscapeUtils.escapeHtml(mtPage.Name)%></span>
							<% } else { %>
  							  <a href="<%= catgenContext.RelativePath%><%= URLEncoder.encode(Utils.getSafeString(mtPage.Name).trim())%>?singlePageTemplate=true&pagesize=<%=catgenContext.PageSize%>">
							<%= StringEscapeUtils.escapeHtml(mtPage.Name)%></a>
							<% } %> 

							   
							    <%
                                                }
                                            }%>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td><div style="padding:5px;">
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
                        <tr>
                        <td valign="top">
                            <%
                            if ("Home".equalsIgnoreCase(catgenContext.PageName)) {%>
                                <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
                                    <tr>
                                        <td>
                                            <div class="contents"><%=(networkMarket.Description != null) ? networkMarket.Description : ""%></div>
                                        </td>
                                    </tr>
                                    <%--<tr valign="middle" class="Title">
                                        <td>
                                            &nbsp;&nbsp;&nbsp;<% style.featuredText + " Network Markets " %>
                                        </td>
                                       
                                    </tr> --%>
                                    <tr>
                                        <td>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="right">
                                            <div style="padding-right:5px"><a href="http://nm.openentry.com/openentry/Buyers?singlePageTemplate=true&pagesize=96"><b></b></a><br /></div>
                                        </td>
                                    </tr>
                                </table>
                            <% }
                            else {%>
                                <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
                                    <tr>
                                        <td>
                                        <%
                                        if (catgenContext.PageName != null && templatePropertiesBean.mtPages.size() > 0) {
                                            for (Page pg : templatePropertiesBean.mtPages) {
                                                if (catgenContext.PageName.equalsIgnoreCase(pg.Name)) {%>
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td>
                                                            <div class="contents"><%= (pg.Description != null) ? pg.Description : ""%>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </table><%
                                                   }
                                               }
                                           }%>
                                        </td>
                                    </tr>
                                </table>
                               <%}%>
                            </td>
                        </tr>
                        </table></div>
                </td>
            </tr>
            <tr valign="middle" class="Title">
                <td align="center">
                    <%=(networkMarket.Footer != null) ? networkMarket.Footer : ""%>
                </td>
            </tr>
        </table>
        <p align="center">
            <a href="http://www.openentry.com" style="text-decoration:none;"><font color="white">Powered by OpenEntry using the latest cloud computing technology on Google and Amazon tools and servers</font></a>
        </p>
                
        <input type="hidden" name="Template Name" value="Home_<%=templatePropertiesBean.catgenPageContext.Template %>.jsp"></input>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-9677880-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
    </body>
</html>
<%
}catch(Exception e){
	e.printStackTrace();
}finally{
	conn.close();
}%>