package org.javaprotrepticon.android.kotactik.storage.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Audio {
	
	@DatabaseField(id = true)
	private Integer id;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private User owner;
	
	@DatabaseField
	private String artist;
	
	@DatabaseField
	private String title;
	
	@DatabaseField
	private Integer duration;
	
	@DatabaseField
	private String url;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Lyrics lyrics;
	
	@DatabaseField
	private Integer album_id;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Genre genre; // Genre ID. See the list of audio genres. 

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

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Lyrics getLyrics() {
		return lyrics;
	}

	public void setLyrics(Lyrics lyrics) {
		this.lyrics = lyrics;
	}

	public Integer getAlbum_id() {
		return album_id;
	}

	public void setAlbum_id(Integer album_id) {
		this.album_id = album_id;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

}
