/*
 * Copyright (c) 2016. Wydział Elektroniki, Telekomunikacji i Informatyki, Politechnika Gdańska
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or   (at your option) any later version.
 *
 * Copy of GNU General Public License is available at http://www.gnu.org/licenses/gpl-3.0.html
 */
package br.com.thinkti.android.filechooser;

public class Option implements Comparable<Option>{
	private String name;
	private String data;
	private String path;
	private boolean folder;
	private boolean parent;
	
	public Option(String n,String d,String p, boolean folder, boolean parent)
	{
		name = n;
		data = d;
		path = p;
		this.folder = folder;
		this.parent = parent;
	}
	public String getName()
	{
		return name;
	}
	public String getData()
	{
		return data;
	}
	public String getPath()
	{
		return path;
	}
	@Override
	public int compareTo(Option o) {
		if(this.name != null)
			return this.name.toLowerCase().compareTo(o.getName().toLowerCase()); 
		else 
			throw new IllegalArgumentException();
	}
	public boolean isFolder() {
		return folder;
	}
	public boolean isParent() {
		return parent;
	}
}
