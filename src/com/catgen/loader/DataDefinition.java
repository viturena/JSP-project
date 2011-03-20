package com.catgen.loader;

public class DataDefinition 
{
	public static final int INT = 1;
	public static final int DOUBLE = 2;
	public static final int DATE_TIME = 3;
	public static final int STRING = 4;
	
	public static final int LOADER_TRIM = 1;
	public static final int DEFAULT_IF_FIRST_AND_ONLY = 2;
	public static final int USE_DEFAULT_IF_EMPTY = 4;
	public static final int ALWAYS_INCLUDE = 8;
	public static final int YESNO_AS_INT = 16;
	
	public String Name;
	public int Type;
	public int Flags;
	
	public String DefaultValue;
	
	public IExtraDataLoader ExtraLoader;

	public DataDefinition(String Name)
	{
		this.Name = Name;
		this.Type = 0;
		this.Flags = 0;
		this.ExtraLoader = null;
	}

	public DataDefinition(String Name, int Type, int flags)
	{
		this.Name = Name;
		this.Type = Type;
		this.Flags = flags;
		this.ExtraLoader = null;
	}

	public DataDefinition(String Name, int Type, int flags, IExtraDataLoader extraLoader)
	{
		this.Name = Name;
		this.Type = Type;
		this.Flags = flags;
		this.ExtraLoader = extraLoader;
	}
	
	
}
