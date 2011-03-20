package com.catgen.loader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser;

import com.catgen.xml.DOMErrors;

public abstract class DataLoader {

	/** Default namespaces support (true). */
	protected static final boolean DEFAULT_NAMESPACES = true;

	/** Default validation support (false). */
	protected static final boolean DEFAULT_VALIDATION = false;

	/** Default Schema validation support (false). */
	protected static final boolean DEFAULT_SCHEMA_VALIDATION = false;

	static LSParser builder;

	protected boolean IsRowData;
	protected boolean IncludeRowID;

	public Hashtable< String, DataDefinition> header;
	public ArrayList<String> extraNames;
	public ArrayList<String> extraValues;

	protected Hashtable< String, DataDefinition> dbColumn;
	protected String tableName;
	protected ArrayList<String> required;
	protected ArrayList<SpreadsheetData> columnObject;
	
	protected DataLoader extraLoader; 

	protected IExtaRowLoader extraRowLoader; 

	public DataLoader()
	{
		required = new ArrayList<String>();
		header = new Hashtable<String, DataDefinition>(); 
		dbColumn = new Hashtable<String, DataDefinition>();
		columnObject = new ArrayList<SpreadsheetData>();
		extraNames = new ArrayList<String>();
		extraValues = new ArrayList<String>();
		IsRowData = true;
		IncludeRowID = false;
	}

	public void ClearExtras()
	{
		extraNames.clear();
		extraValues.clear();
	}

	public void AddExtras(String name, String value)
	{
		extraNames.add(name);
		extraValues.add(value);
	}

	public void SaveToDB(Connection conn, ArrayList<SpreadsheetData> sheetData)
	{
		// save default value for first & only case
		if(sheetData.size() == 1)
		{
			SpreadsheetData sd = sheetData.get(0);
			if(sd.Column != null && sd.Column.trim().length() > 0)
			{				
				DataDefinition h = header.get(sd.Column);
				if(h != null && (h.Flags & DataDefinition.DEFAULT_IF_FIRST_AND_ONLY) == DataDefinition.DEFAULT_IF_FIRST_AND_ONLY)
				{
					String v = sd.value;
					if(v != null)
					{
						v = v.trim();
						String n = sd.Column.trim().toLowerCase();
						
						if(v.equalsIgnoreCase("none") 
								|| v.equalsIgnoreCase("no") 
								|| v.equalsIgnoreCase("not") 
								|| v.equalsIgnoreCase("empty") 
								|| v.equalsIgnoreCase("none " + n) 
								|| v.equalsIgnoreCase("no " + n) 
								|| v.equalsIgnoreCase("not " + n) 
								|| v.equalsIgnoreCase("empty " + n)
						)
							v = null;
					}				

					h.DefaultValue = v;
				}
			}
		}
		
		// check for always include ones
	    for (Enumeration<String> e = header.keys() ; e.hasMoreElements() ;) 
	    {
	    	String key = e.nextElement();
			DataDefinition dd = header.get(key);
			
			if((dd.Flags & DataDefinition.ALWAYS_INCLUDE) == DataDefinition.ALWAYS_INCLUDE)
			{
				boolean isIncluded = false; 
				
				for(int i = 0; i < sheetData.size(); i++)
				{
					SpreadsheetData sd = sheetData.get(i);
					if(sd.Column == null || sd.Column.trim().length() == 0)
						continue;
					
					DataDefinition h = header.get(sd.Column);
					if(h != null && h.Name.equalsIgnoreCase(dd.Name))
					{
						isIncluded = true;
						break;
					}
				}
				
				if(!isIncluded)
				{
System.out.println("q: 2 " + key);

					SpreadsheetData sd = new SpreadsheetData();
					sd.Column = key;
					sd.value = dd.DefaultValue;
					
					if(sheetData.size() > 0)
						sd.Row = sheetData.get(0).Row;
					
					sheetData.add(sd);
				}
			}
		}
		
		// extra processing
		for(int i = 0; i < sheetData.size(); i++)
		{
			SpreadsheetData sd = sheetData.get(i);
			DataDefinition h = header.get(sd.Column);
			if(h != null)
			{
				if(h.ExtraLoader != null)
				{
					String v = h.ExtraLoader.Load(sd.value);
					if(v != null && v.length() > 0)
					{						
						DataDefinition dd = dbColumn.get(h.ExtraLoader.Header());
						if(dd != null)
						{
							header.put(h.ExtraLoader.Header(), dd);
							SpreadsheetData newData = new SpreadsheetData();
							newData.Column = h.ExtraLoader.Header();
							newData.value = v;
							newData.Row = sd.Row;
							
							sheetData.add(newData);
						}						
					}
				}
				
				// check for default
				if( (h.Flags & DataDefinition.USE_DEFAULT_IF_EMPTY) == DataDefinition.USE_DEFAULT_IF_EMPTY )
				{
					if(sd.value == null || sd.value.trim().length() == 0 )
					{
						sd.value = h.DefaultValue;
						System.out.println("p: 7");
					}
				}
				
				// transform yes/no to int
				if( (h.Flags & DataDefinition.YESNO_AS_INT) == DataDefinition.YESNO_AS_INT )
				{
					if(sd.value != null)
					{
						String v = sd.value.toLowerCase();
						if(v.equals("y") || v.equals("yes") || v.equals("true") || v.equals("1"))
							sd.value = "1";
						else
							sd.value = "0";
						
						System.out.println("p: 7.1");
					}
				}
			}
		}
		
		if(Validate(sheetData))
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
			
			if(IncludeRowID && sheetData.size() > 0)
			{
				if(names.length() > 0)
					names += ", ";

				names += "RowID";

				if(values.length() > 0)
					values += ", ";

				values += "" + sheetData.get(0).Row;
			}

			for(int i = 0; i < sheetData.size(); i++)
			{
				SpreadsheetData sd = sheetData.get(i);

				if(sd.Column == null || sd.Column.trim().length() == 0)
					continue;
				
				DataDefinition h = header.get(sd.Column);
				if(h != null)
				{
					if(names.length() > 0)
						names += ", ";

					names += "" + h.Name;

					if(values.length() > 0)
						values += ", ";

					if(h.Type == DataDefinition.INT)
					{
						int v = 0;
						try
						{
							v = Integer.parseInt( sd.value );	
						}
						catch(Exception e)
						{}
						
						values += "" + v;
					}
					else if(h.Type == DataDefinition.DOUBLE)
					{
						double v = 0;
						try
						{
							v = Double.parseDouble( sd.value );	
						}
						catch(Exception e)
						{}
						
						values += "" + v;
					}
					else if(h.Type == DataDefinition.DATE_TIME)
					{
						Date v = null;
						try
						{
							DateFormat df = DateFormat.getDateInstance();
							v = df.parse( sd.value );	
						}
						catch(Exception e)
						{}

						values += "" + v;
					}
					else
					{
						String v = sd.value.replace( "'", "''").replace( "\r", "");
						
						if((h.Flags & DataDefinition.LOADER_TRIM) > 0)
							v = v.trim();
						
						values += "'" + v + "'";
					}
				}
			}
			// Categories Changes: Feb 2010
			values = values.replaceAll("#BLANK#", "");
			
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
			else
				System.out.println( sql );

			AfterSave(conn, sheetData);
		}
		
		if(extraRowLoader != null)
			extraRowLoader.Load( conn, this, sheetData );
	}

	protected void AfterSave(Connection conn, ArrayList<SpreadsheetData> sheetData)
	{
	}

	public void UpdateHeader(ArrayList<SpreadsheetData> sheetData)
	{
		header.clear();

		for(int l = 0; l < sheetData.size(); l++)
		{

			String c = sheetData.get( l ).Column;
			String v = sheetData.get( l ).value;
			if(v != null && c != null)
			{
				v = v.replaceAll("\\([^)]*\\)", "");
				v = v.replaceAll("\\s\\*.*$", "");
				v = v.replaceAll("\n", " ");
				v = v.replaceAll("\r", " ");
				v = v.trim().toLowerCase();											

				DataDefinition dd = dbColumn.get(v);
				if(dd != null)
					header.put(c, dd);
			}
		}		
	}

	public void LoadData(Connection conn, String url)
	{
		try {

			// get DOM Implementation using DOM Registry
			System.setProperty(DOMImplementationRegistry.PROPERTY,"org.apache.xerces.dom.DOMXSImplementationSourceImpl");
			DOMImplementationRegistry registry =
				DOMImplementationRegistry.newInstance();

			DOMImplementationLS impl = 
				(DOMImplementationLS)registry.getDOMImplementation("LS");

			// create DOMBuilder
			builder = impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);

			DOMConfiguration config = builder.getDomConfig();

			// create Error Handler
			DOMErrorHandler errorHandler = new DOMErrors();

			// set error handler
			config.setParameter("error-handler", errorHandler);

			// set validation feature
			//config.setParameter("validate", Boolean.FALSE);
			config.setParameter("validate",Boolean.FALSE);

			// set schema language
			config.setParameter("schema-type", "http://www.w3.org/2001/XMLSchema");
			//config.setParameter("psvi",Boolean.TRUE);
			//config.setParameter("schema-type","http://www.w3.org/TR/REC-xml");

			// set schema location
			//config.setParameter("schema-location","personal.xsd");

			// parse document
			//System.out.println("Parsing "+url+"...");
			Document doc = builder.parseURI(url);

			ProcessData(conn, doc.getDocumentElement());
			
			if(extraLoader != null)
			{
				extraLoader.ClearExtras();
				for(int i = 0; i < extraNames.size(); i++)
				{
					extraLoader.AddExtras( extraNames.get(i), extraValues.get(i) );
				}
				
				extraLoader.ProcessData(conn, doc.getDocumentElement());
			}

		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
	}
	
	public void ProcessData(Connection conn, Node node)
	{
		if(IsRowData)
			RowsData(conn, node);
		else
			ColumnsData(conn, node);		
	}

	private void RowsData(Connection conn, Node rootNode)
	{
		int rowNumber = 0;

		ArrayList<SpreadsheetData> sheetData = new ArrayList<SpreadsheetData>(); 

		NodeList nodeList = rootNode.getChildNodes();

		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if("entry".equalsIgnoreCase(node.getLocalName()))
			{
				SpreadsheetData sd = getEntryData(node);
				if(sd != null)
				{
					if(sd.Row != rowNumber)
					{
						if(rowNumber == 1)
							UpdateHeader(sheetData);
						else if(rowNumber > 1)
							SaveToDB(conn, sheetData);

						rowNumber = sd.Row;

						sheetData.clear();
					}

					sheetData.add(sd);

					//sd.print();
				}
			}
		}

		if(sheetData.size() > 0)
			SaveToDB(conn, sheetData);

	}

	private void ColumnsData(Connection conn, Node rootNode)
	{
		int rowNumber = 0;

		ArrayList<SpreadsheetData> sheetData = new ArrayList<SpreadsheetData>(); 

		NodeList nodeList = rootNode.getChildNodes();

		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if("entry".equalsIgnoreCase(node.getLocalName()))
			{
				SpreadsheetData sd = getEntryData(node);
				if(sd != null)
				{
					if(sd.Row != rowNumber)
					{
						SaveToObject(sheetData);

						rowNumber = sd.Row;

						sheetData.clear();
					}

					sheetData.add(sd);

					//sd.print();
				}
			}
		}

		if(sheetData.size() > 0)
			SaveToObject(sheetData);

		SaveObjectToDB(conn);	
	}

	private void SaveToObject(ArrayList<SpreadsheetData> sheetData)
	{
		if(sheetData.size() > 1)
		{
			SpreadsheetData data = new SpreadsheetData();

			SpreadsheetData row = sheetData.get(0);
			data.Column = "" + row.Row;

			String v = row.value;
			String c = "" + row.Row;

			if(v != null && c != null)
			{
				v = v.replaceAll("\\([^)]*\\)", "");
				v = v.replaceAll("\\s\\*.*$", "");
				v = v.replaceAll("\n", " ");
				v = v.replaceAll("\r", " ");
				v = v.trim().toLowerCase();											

				DataDefinition dd = dbColumn.get(v);
				if(dd != null)
				{
					header.put(c, dd);

					data.value = sheetData.get(1).value;

					columnObject.add(data);
				}
			}
		}
		
		sheetData.clear();
	}

	private void SaveObjectToDB(Connection conn)
	{
		SaveToDB(conn, columnObject);
		columnObject.clear();
	}

	protected static SpreadsheetData getEntryData(Node entryNode )
	{
		SpreadsheetData result = null;

		NodeList nodeList = entryNode.getChildNodes();

		String title = null;
		String content = null;

		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			if("title".equalsIgnoreCase(node.getLocalName()))
				title = node.getTextContent();

			if("content".equalsIgnoreCase(node.getLocalName()))
				content = node.getTextContent();			
		}

		if( title != null && content != null )
		{
			Pattern p = Pattern.compile("([A-Z]+)([0-9]+)");
			Matcher m = p.matcher(title);
			if(m.matches())
			{
				result = new SpreadsheetData();

				result.Column = m.group(1);
				result.Row = Integer.parseInt(m.group(2));
				result.value = content;
			}
		}

		return result;
	}

	public boolean Validate(ArrayList<SpreadsheetData> sheetData)
	{
		for(int i = 0; i < required.size(); i++)
		{
			String s = required.get(i);

			boolean hasData = false;

			for(int j = 0; j < sheetData.size(); j++)
			{
				SpreadsheetData data = sheetData.get(j);
				DataDefinition h = header.get(data.Column);
				if(h != null && s.equalsIgnoreCase(h.Name) && data.value != null && data.value.trim().length() > 0 )
				{
					hasData = true;
					break;
				}
			}

			if(!hasData)
				return false;
		}

		return true;
	}
}
