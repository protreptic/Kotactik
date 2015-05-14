package org.javaprotrepticon.android.kotactik.storage.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Counters {

	@DatabaseField(id = true)
	private Integer id;
	
	@DatabaseField
	private Integer friends;
	
	@DatabaseField
	private Integer messages;
	
	@DatabaseField
	private Integer photos;
	
	@DatabaseField
	private Integer videos;
	
	@DatabaseField
	private Integer notes;
	
	@DatabaseField
	private Integer gifts;
	
	@DatabaseField
	private Integer events;
	
	@DatabaseField
	private Integer groups;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFriends() {
		return friends;
	}

	public void setFriends(Integer friends) {
		this.friends = friends;
	}

	public Integer getMessages() {
		return messages;
	}

	public void setMessages(Integer messages) {
		this.messages = messages;
	}

	public Integer getPhotos() {
		return photos;
	}

	public void setPhotos(Integer photos) {
		this.photos = photos;
	}

	public Integer getVideos() {
		return videos;
	}

	public void setVideos(Integer videos) {
		this.videos = videos;
	}

	public Integer getNotes() {
		return notes;
	}

	public void setNotes(Integer notes) {
		this.notes = notes;
	}

	public Integer getGifts() {
		return gifts;
	}

	public void setGifts(Integer gifts) {
		this.gifts = gifts;
	}

	public Integer getEvents() {
		return events;
	}

	public void setEvents(Integer events) {
		this.events = events;
	}

	public Integer getGroups() {
		return groups;
	}

	public void setGroups(Integer groups) {
		this.groups = groups;
	}
	
}
