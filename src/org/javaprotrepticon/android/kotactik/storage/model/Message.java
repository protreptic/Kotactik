package org.javaprotrepticon.android.kotactik.storage.model;

import java.sql.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Message {
	
	@DatabaseField(id = true)
	private Integer id;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private User user;
	
	@DatabaseField
	private Date date;
	
	@DatabaseField
	private MessageState messageState;
	
	@DatabaseField
	private MessageType messageType;
	
	@DatabaseField
	private String title;
	
	@DatabaseField
	private String body;
	
	public enum MessageState {
		NOT_READ, READ
	}
	
	public enum MessageType {
		RECEIVED, SENT
	}
	
}
