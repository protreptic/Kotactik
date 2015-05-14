package org.javaprotrepticon.android.kotactik.storage.model;

import java.sql.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Photo {
	
	@DatabaseField(id = true)
	private Integer id;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Album album;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private User owner;
	
	@DatabaseField
	private String photo75;
	
	@DatabaseField
	private String photo130;
	
	@DatabaseField
	private String photo604;
	
	@DatabaseField
	private String photo807;
	
	@DatabaseField
	private String photo1280;
	
	@DatabaseField
	private String photo2560;

	@DatabaseField
	private Integer width;
	
	@DatabaseField
	private Integer height;
	
	@DatabaseField(dataType = DataType.LONG_STRING)
	private String text;
	
	@DatabaseField
	private Date date;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getPhoto130() {
		return photo130;
	}

	public void setPhoto130(String photo130) {
		this.photo130 = photo130;
	}

	public String getPhoto75() {
		return photo75;
	}

	public void setPhoto75(String photo75) {
		this.photo75 = photo75;
	}

	public String getPhoto604() {
		return photo604;
	}

	public void setPhoto604(String photo604) {
		this.photo604 = photo604;
	}

	public String getPhoto807() {
		return photo807;
	}

	public void setPhoto807(String photo807) {
		this.photo807 = photo807;
	}

	public String getPhoto1280() {
		return photo1280;
	}

	public void setPhoto1280(String photo1280) {
		this.photo1280 = photo1280;
	}

	public String getPhoto2560() {
		return photo2560;
	}

	public void setPhoto2560(String photo2560) {
		this.photo2560 = photo2560;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
