package com.kmg.soundboardDbHelper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.youtube.model.Video;

@RestController
@RequestMapping("/sounds")
public class SoundController {
	SoundRepository soundRepository;
	YoutubeService youtubeService;
	
	@Autowired
	public SoundController(SoundRepository soundRepository) {
		this.soundRepository = soundRepository;
	}
	
	@RequestMapping("/all")
	public List<Sound> getAll(){
		return this.soundRepository.findAll();
	}
	
	@RequestMapping("/byKruszwil")
	public List<Sound> getByKruszwil(){
		youtubeService = new YoutubeService();
		List<Sound> temp = new ArrayList<>();
		for(Sound sound: this.soundRepository.findAll()) {
			if(youtubeService.isFromChannel(sound.getVidId(), "UC4uocvXN4aPFQG6paBaMb1A"))
				temp.add(sound);
		}
		
		return temp;
		
	}
	
	
	@RequestMapping("/getOrdered")
	public HashMap<Video, HashMap<String,Time>> getOrdered(){
		List<Sound> all = getByKruszwil();		
		youtubeService = new YoutubeService();
		HashMap<Video, HashMap<String,Time>> hashMap = new HashMap<>();
		
		for(int j = 0; j < all.size(); j++) {				
			HashMap<String, Time> map = new HashMap<>();
			
			for(int k = 0; k < all.size(); k++) {					
				if(all.get(k).getVidId().equals(all.get(j).getVidId())) {
					map.put(all.get(k).getText(), all.get(k).getTime());
				}
			}
			hashMap.put(youtubeService.getVideo(all.get(j).getVidId()), map);				
		}	
		
		return hashMap;
	}
	/*
	@RequestMapping("/getOrdered")
	public HashMap<String, HashMap<String,Time>> getOrdered(){
		List<Sound> all = getByKruszwil();		
		
		HashMap<String, HashMap<String,Time>> hashMap = new HashMap<>();
		
		for(int j = 0; j < all.size(); j++) {				
			HashMap<String, Time> map = new HashMap<>();
			
			for(int k = 0; k < all.size(); k++) {					
				if(all.get(k).getVidId().equals(all.get(j).getVidId())) {
					map.put(all.get(k).getText(), all.get(k).getTime());
				}
			}
			hashMap.put(all.get(j).getVidId(), map);				
		}	
		
		return hashMap;
	}
*/
}
