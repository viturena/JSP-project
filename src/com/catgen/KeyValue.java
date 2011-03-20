package com.catgen;

public class KeyValue 
{
	public String Key;
	public String Value;
	public int ValueType;
	public String ToValue;
	
	public KeyValue()
	{
	}

	public KeyValue(String key, String value)
	{
		Key = key;
		Value = value;
	}
	
	public KeyValue(String key, String value, int valueType, String toValue)
	{
		this(key, value);
		
		ValueType = valueType;
		ToValue = toValue;
	}
}
