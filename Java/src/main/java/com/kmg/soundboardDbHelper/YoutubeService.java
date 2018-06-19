package com.kmg.soundboardDbHelper;

import java.io.IOException;
import java.util.HashMap;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

public class YoutubeService {
	private YouTube youtube;
	private final String API_KEY = "<YouTube API Key>";
	
	public Video getVideo(String vidId) {
		youtube = getYouTube();
		HashMap<String, String> params = new HashMap<>();
		params.put("part", "snippet");
		params.put("id", vidId);
		
		try {
			YouTube.Videos.List list = youtube.videos().list(params.get("part").toString());
			if(params.containsKey("id") && params.get("id") != "") {
				list.setId(params.get("id").toString());
				list.setKey(API_KEY);
			}
			
			VideoListResponse response = list.execute();
			
			return response.getItems().get(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean isFromChannel(String vidId, String channelId) {
		boolean isOk = false;
		youtube = getYouTube();
		HashMap<String, String> params = new HashMap<>();
		params.put("part", "snippet");
		params.put("id", vidId);
		
		try {
			YouTube.Videos.List list = youtube.videos().list(params.get("part").toString());
			if(params.containsKey("id") && params.get("id") != "") {
				list.setId(params.get("id").toString());
				list.setKey(API_KEY);
			}
			
			VideoListResponse response = list.execute();
			
			for(Video video : response.getItems()) {
				if(video.getSnippet().getChannelId().equals(channelId)) {
					isOk = true;
				}else {
					isOk = false;
					break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return isOk;
	}
	
	public String getChannelId(String exampleId) {
		youtube = getYouTube();
		HashMap<String, String> params = new HashMap<>();
		params.put("part", "snippet");
		params.put("id", exampleId);
		
		try {
			YouTube.Videos.List list = youtube.videos().list(params.get("part").toString());
			if(params.containsKey("id") && params.get("id") != "") {
				list.setId(params.get("id").toString());
				list.setKey(API_KEY);
			}
			
			VideoListResponse response = list.execute();
			
			return response.getItems().get(0).getSnippet().getChannelId();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private YouTube getYouTube() {
		return new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), 
				(request) -> {}).setApplicationName("soundboardDbHelper").build();    
	}
}
