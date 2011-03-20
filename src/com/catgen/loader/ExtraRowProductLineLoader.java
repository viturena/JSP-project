package com.catgen.loader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ExtraRowProductLineLoader implements IExtaRowLoader 
{
	public void Load(Connection conn, DataLoader dataLoader, ArrayList<SpreadsheetData> sheetData) 
	{
		if(dataLoader == null)
			return;
		
		if(sheetData != null && sheetData.size() == 1)
		{
			SpreadsheetData sd = sheetData.get(0);
			if(sd.Column == null || sd.Column.trim().length() == 0)
				return;
			
			DataDefinition h = dataLoader.header.get(sd.Column);
			if(h != null && h.Name != null && h.Name.equalsIgnoreCase("productline"))
			{
				String productLineName = sd.value;
				
				if(productLineName == null || productLineName.trim().length() == 0)
					return;
				else
					productLineName = productLineName.trim();

				String where = "";

				for(int i = 0; i < dataLoader.extraNames.size(); i++)
				{
					if(where.length() > 0)
						where  += " AND ";

					where += "" + dataLoader.extraNames.get(i) + " = '" + dataLoader.extraValues.get(i).replace( "'", "''") + "'";
				}

				if(where.length() > 0)
					where  += " AND ";
				
				where += "Name = '" + productLineName + "'";
				
				String sql = "SELECT COUNT(*) FROM ProductLines WHERE " + where;
				if(conn != null)
				{
					try
					{
						PreparedStatement pstmt = conn.prepareStatement(sql);
						try
						{
							ResultSet rs = pstmt.executeQuery();
							try
							{
								if(rs.next() && rs.getInt(1) > 0)
									return ;
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
					}
					catch (Exception e) {
						System.out.println( "exception for: " + sql );
						e.printStackTrace();
					}	
				}
				
				String names = "";
				String values = "";

				for(int i = 0; i < dataLoader.extraNames.size(); i++)
				{
					if(names.length() > 0)
						names += ", ";

					names += "" + dataLoader.extraNames.get(i);

					if(values.length() > 0)
						values += ", ";

					values += "'" + dataLoader.extraValues.get(i).replace( "'", "''") + "'";
				}
				
				if(names.length() > 0)
					names += ", ";

				names += "Code";
				
				if(values.length() > 0)
					values += ", ";

				values += "'" + productLineName.replace( "'", "''") + "'";

				names += ", Name";
				values += ", '" + productLineName.replace( "'", "''") + "'";

				names += ", RowID";
				values += ", " + sd.Row;
				
				sql = "INSERT INTO ProductLines(" + names + ") VALUES( " + values + ");";
				if(conn != null)
				{
					try
					{
						PreparedStatement pstmt = conn.prepareStatement(sql);
						pstmt.executeUpdate();
					}
					catch (Exception e) {
						System.out.println( "exception for: " + sql );
						e.printStackTrace();
					}	
				}
			}
		}
	}
}
