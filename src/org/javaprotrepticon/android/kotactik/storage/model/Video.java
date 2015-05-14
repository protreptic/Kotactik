package org.javaprotrepticon.android.kotactik.storage.model;

import java.sql.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Video {
	
	@DatabaseField(id = true)
	private Integer id;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private User owner;
	
	@DatabaseField
	private String title;
	
	@DatabaseField
	private Integer duration;
	
	@DatabaseField(dataType = DataType.LONG_STRING)
	private String description;
	
	@DatabaseField
	private Date date;
	
	@DatabaseField
	private Integer views;
	
	@DatabaseField
	private Integer comments;
	
	@DatabaseField
	private String photo130;
	
	@DatabaseField
	private String photo320;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Integer getComments() {
		return comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public String getPhoto130() {
		return photo130;
	}

	public void setPhoto130(String photo130) {
		this.photo130 = photo130;
	}

	public String getPhoto320() {
		return photo320;
	}

	public void setPhoto320(String photo320) {
		this.photo320 = photo320;
	}
	
}
