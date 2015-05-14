package org.javaprotrepticon.android.kotactik.storage.model;

import com.j256.ormlite.field.DatabaseField;

public class Album {

	@DatabaseField(id = true)
	private Integer id;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private User owner;
	
	@DatabaseField
	private String title;
	
}
