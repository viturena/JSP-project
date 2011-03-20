package com.catgen.factories;


import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser;

import com.catgen.xml.DOMErrors;

public class XMLFactory {
	
	public static String getMessageElement(String tag, String message){
		return ("<"+tag+">"+message+"</"+tag+">");
	}
	
	public static Document getXMLDocumentByURL(String url) throws IllegalAccessException, InstantiationException, ClassNotFoundException{
		LSParser builder;
		System.setProperty(DOMImplementationRegistry.PROPERTY,"org.apache.xerces.dom.DOMXSImplementationSourceImpl");
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();

		DOMImplementationLS impl = (DOMImplementationLS)registry.getDOMImplementation("LS");
		builder = impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
		DOMConfiguration config = builder.getDomConfig();
		DOMErrorHandler errorHandler = new DOMErrors();
		config.setParameter("error-handler", errorHandler);
		config.setParameter("validate",Boolean.FALSE);
		config.setParameter("schema-type", "http://www.w3.org/2001/XMLSchema");
		Document doc = builder.parseURI(url);
		doc.getDocumentElement().normalize();
		return doc;
	}
}
