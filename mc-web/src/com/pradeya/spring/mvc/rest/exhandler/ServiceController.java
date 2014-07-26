package com.pradeya.spring.mvc.rest.exhandler;

//http://kamisama.github.io/cal-heatmap/datas-years.json
//C:/Users/g702998/mahesha/personal/pradeya/cal/data.json
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletContext;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pradeya.cast.domain.Schedule;
import com.pradeya.cast.service.ScheduleService;


/**
 * Example Spring MVC Controller that will throw exceptions for specific URLs to
 * show exception handling.
 */
@Controller
@RequestMapping("/schedule")
public class ServiceController {

	@Autowired
	ScheduleService scheduleService;
	

	@RequestMapping(method = GET)
	@ResponseBody
	public Iterable<Schedule> findByPushState() {

		Iterable<Schedule> it = scheduleService.findByPushState("pending".toUpperCase());
		
		Iterator<Schedule> itr = null;
		if(it != null){
			itr = it.iterator();
			Schedule schedule = null;
			while(itr.hasNext()){
				schedule = itr.next();
				schedule.setPushState("PUSHED");
				scheduleService.update(schedule);
			}
			
		}
		return it;
	}
	
	
}
