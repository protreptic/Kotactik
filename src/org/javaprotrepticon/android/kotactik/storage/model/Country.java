package org.javaprotrepticon.android.kotactik.storage.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Country {
	
	@DatabaseField(id = true)
	private Integer id;
	
	@DatabaseField
	private String title;
	
}
