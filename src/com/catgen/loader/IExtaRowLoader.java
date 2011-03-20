package com.catgen.loader;

import java.sql.Connection;
import java.util.ArrayList;

public interface IExtaRowLoader 
{
	public void Load(Connection conn, DataLoader dataLoader,  ArrayList<SpreadsheetData> sheetData);
}
