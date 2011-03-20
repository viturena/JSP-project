package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.catgen.reports.Report;

public class ReportFactory 
{
	private static void LoadReportFromResultSet(ResultSet rs, Report report) throws SQLException 
	{
		report.ReportCode = rs.getString("ReportCode");
		report.ReportName = rs.getString("ReportName");
		report.ReportType = rs.getString("ReportType");
		report.TemplateURL = rs.getString("TemplateURL");
	}
	
	public static List<Report> getReports(Connection conn) throws SQLException
	{
		ArrayList<Report> reports = new ArrayList<Report>();
		
		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Reports ORDER BY OrderNum, ReportName" );
		try
		{
			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Report report = new Report();

					LoadReportFromResultSet(rs, report);
					
					reports.add(report);
				}
			}
			finally
			{
				rs.close();
			}
		}
		finally
		{
			pstmt.close();
		}			
		
		
		return reports;
	}
}
