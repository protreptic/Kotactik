package org.javaprotrepticon.android.kotactik.storage.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class User {
	
	@DatabaseField(id = true)
	private Integer id;
	
	@DatabaseField(index = true)
	private String firstName;
	
	@DatabaseField(index = true)
	private String lastName;

	@DatabaseField
	private String photo50;
	
	@DatabaseField
	private String photo100;
	
	@DatabaseField
	private String photo200;
	
	@DatabaseField
	private String photoMax;
	
	@DatabaseField
	private Integer online;
	
	@DatabaseField
	private Integer onlineMobile;
	
	@DatabaseField
	private String status;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoto50() {
		return photo50;
	}

	public void setPhoto50(String photo50) {
		this.photo50 = photo50;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhoto100() {
		return photo100;
	}

	public void setPhoto100(String photo100) {
		this.photo100 = photo100;
	}

	public Integer getOnlineMobile() {
		return onlineMobile;
	}

	public void setOnlineMobile(Integer onlineMobile) {
		this.onlineMobile = onlineMobile;
	}

	public String getPhoto200() {
		return photo200;
	}

	public void setPhoto200(String photo200) {
		this.photo200 = photo200;
	}

	public String getPhotoMax() {
		return photoMax;
	}

	public void setPhotoMax(String photoMax) {
		this.photoMax = photoMax;
	}
	
}
