package com.catgen.helper;

import java.sql.Connection;
import com.catgen.HelpMessages;

public class HelpHelper {
	public static String getHelpMessage(Connection conn, int screenId){
		return new HelpMessages().message[screenId];
	}
}
