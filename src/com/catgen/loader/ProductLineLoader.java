package com.catgen.loader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.regex.*;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProductLineLoader extends DataLoader 
{
	private Pattern m_pattern;

	public ProductLineLoader()
	{
		super();
		
		tableName = "ProductLines";
	}
	
	public void ProcessData(Connection conn, Node node) 
	{
		m_pattern = Pattern.compile("line\\s+([0-9])+", Pattern.CASE_INSENSITIVE );
		
		int rowNumber = 0;

		ArrayList<SpreadsheetData> sheetData = new ArrayList<SpreadsheetData>(); 

		NodeList nodeList = node.getChildNodes();

		for(int i = 0; i < nodeList.getLength(); i++) {
			Node rowNode = nodeList.item(i);
			if("entry".equalsIgnoreCase(rowNode.getLocalName()))
			{
				SpreadsheetData sd = getEntryData(rowNode);
				if(sd != null)
				{
					if(sd.Row != rowNumber)
					{
						SaveProductLine(conn, sheetData);

						rowNumber = sd.Row;

						sheetData.clear();
					}

					sheetData.add(sd);
				}
			}
		}

		if(sheetData.size() > 0)
			SaveProductLine(conn, sheetData);
	}
	
	private void SaveProductLine(Connection conn, ArrayList<SpreadsheetData> sheetData)
	{
		if(sheetData.size() > 1)
		{
			SpreadsheetData row = sheetData.get(0);

			Matcher m = m_pattern.matcher(row.value);
			if(m.matches())
			{
				String productLineCode = m.group(1);
				String productLineName = sheetData.get(1).value;

				if(productLineCode != null && productLineName != null)
				{				
					String names = "";
					String values = "";

					for(int i = 0; i < extraNames.size(); i++)
					{
						if(names.length() > 0)
							names += ", ";

						names += "" + extraNames.get(i);

						if(values.length() > 0)
							values += ", ";

						values += "'" + extraValues.get(i).replace( "'", "''") + "'";
					}


					if(names.length() > 0)
						names += ", ";

					names += "Code";
					
					if(values.length() > 0)
						values += ", ";

					values += "'" + productLineCode.trim().replace( "'", "''") + "'";

					names += ", Name";
					values += ", '" + productLineName.trim().replace( "'", "''") + "'";

					names += ", RowID";
					values += ", " + row.Row;
					
					String sql = "INSERT INTO " + tableName + "(" + names + ") VALUES( " + values + ");";
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
}
