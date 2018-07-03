package com.kmg.soundboardDbHelper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.youtube.model.Video;

@Controller
@RequestMapping("/")
public class MainController {
	SoundRepository soundRepository;
	YoutubeService youtubeService;

	
	@Autowired
	public MainController(SoundRepository soundRepository) {
		this.soundRepository = soundRepository;
	}
	
	@RequestMapping("/hello")
	public String welcome() {
		return "hello";
	}
	//get a number of seconds from minutes:seconds format
	public String getSeconds(Time time) {
		int min = time.getHours();
		int sec = time.getMinutes();
		int t = (60 * min) + sec;
		return String.valueOf(t);
	}
	
	@RequestMapping(value = "/ordered", method = RequestMethod.GET)
	public String showOrdered(Model model) {
		HashMap<Video, HashMap<String, String>> videos = getOrdered();
		if(videos != null && videos.size() > 0) {
			model.addAttribute("numberOfVideos", videos.size());
		}else {
			model.addAttribute("numberOfVideos", 0);
		}
		
		//add a list of video objects to browser's model
		model.addAttribute("videos", videos);
		//open showResults.html
		return "showResults";
	}
	
	public HashMap<Video, HashMap<String,String>> getOrdered(){
		List<Sound> all = getByKruszwil();		
		
		HashMap<Video, HashMap<String,String>> hashMap = new HashMap<>();
		youtubeService = new YoutubeService();

		for(int j = 0; j < all.size(); j++) {				
			HashMap<String, String> map = new HashMap<>();
			
			for(int k = 0; k < all.size(); k++) {					
				if(all.get(k).getVidId().equals(all.get(j).getVidId())) {
					map.put(all.get(k).getText(), getSeconds(all.get(k).getTime()));
				}
			}
			hashMap.put(youtubeService.getVideo(all.get(j).getVidId()), map);				
		}	
		
		return hashMap;
	}

	
	public List<Sound> getByKruszwil(){
		youtubeService = new YoutubeService();
		List<Sound> temp = new ArrayList<>();
		for(Sound sound: this.soundRepository.findAll()) {
			if(youtubeService.isFromChannel(sound.getVidId(), "UC4uocvXN4aPFQG6paBaMb1A"))
				temp.add(sound);
		}
		
		return temp;
		
	}
}
























