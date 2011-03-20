package com.catgen.loader;

public class SpreadsheetData {
	public String Column;
	public int Row;

	public String value;

	public void print()
	{
		System.out.println( "column: " + Column + " row " + Row + " value " + value);	
	}
}
