package com.kmg.soundboardDbHelper;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Sound {
	
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	
	private String url;
	
	private Time time;
	
	private String vidId;
	
	private String text;
	
	public Sound() {}
	
	public Sound(String url, Time time, String vidId, String text){
		this.url = url;
		this.time = time;
		this.vidId = vidId;
		this.text = text;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getVidId() {
		return vidId;
	}

	public void setVidId(String vidId) {
		this.vidId = vidId;
	}
	
	public String getText() {
		return text;
	}
	
	
}
