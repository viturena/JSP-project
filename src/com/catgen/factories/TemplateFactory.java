package com.catgen.factories;

//Template Pointer Change : March 2010
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.catgen.TemplatePointerBean;

public class TemplateFactory {
	public static TemplatePointerBean getTemplateName(Connection conn, TemplatePointerBean templatePointerBean) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM templatePointer WHERE pointerName = ?" );
		try{
			pstmt.setString(1, templatePointerBean.pointerName);
			ResultSet rs = pstmt.executeQuery();
			try{
				if(rs.next()){
					templatePointerBean.pointToTemplate = rs.getString("pointToTemplate");
				}else{
					templatePointerBean.pointToTemplate = templatePointerBean.pointerName;
				}
			}finally{
				rs.close();
			}
		}
		finally{
			pstmt.close();
		}			
		return templatePointerBean;
	}
}
