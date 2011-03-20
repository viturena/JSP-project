package com.catgen;

import org.w3c.dom.Element;

public abstract class BasicDatastore {

	public abstract void Save();
	public abstract void Load();
	public abstract void AddToXml(Element element);
}
