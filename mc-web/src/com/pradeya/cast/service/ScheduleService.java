package com.pradeya.cast.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pradeya.cast.domain.Schedule;
import com.pradeya.cast.domain.Schedule;
import com.pradeya.cast.ui.bean.UnknownResourceException;
import com.pradeya.cast.util.CastUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

@Service
public class ScheduleService {

	private Logger L = LoggerFactory.getLogger(ScheduleService.class);

	//Mediacast Server details
//	public @Value("${mediacast.server.ip}") String SERVER_IP = "54.255.134.91";
//	public @Value("${mediacast.server.port}") String SERVER_PORT = "8080";
	
	public static final String WEB_SERVICE_CONTEXT = "mc-webservice";
	public static final String WEB_SERVICE_SCHEDULE_PATH = "schedule";

	public static String WEB_SERVICE_BASE_URL = "http://" + CastUtil.SERVER_IP
			+ ":" + CastUtil.SERVER_PORT + "/" + WEB_SERVICE_CONTEXT + "/"
			+ WEB_SERVICE_SCHEDULE_PATH + "/";

	private Client client = Client.create();

	public boolean createSchedule(Schedule schedule) {

		ClientResponse clientResponse = null;
		try {

			WebResource webResource = client.resource(WEB_SERVICE_BASE_URL);
			L.debug("Invoking create schedule {}: ", WEB_SERVICE_BASE_URL);
			clientResponse = webResource.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, getJSONFromObject(schedule));

			if (clientResponse.getStatus() == 200) {
				L.debug("createSchedule successful ");
				return true;
			}
			L.debug("createSchedule failure ");

			return false;
		} catch (UniformInterfaceException uie) {
			L.error("Encountered exception {}: ", uie);
			return false;
		} finally {
			if (clientResponse != null) {
				clientResponse.close();
			}
		}
	}
	
	public ArrayList<Schedule> findByPushState(String pushState){

		ClientResponse clientResponse = null;
		try {
			String pushStateUrl = WEB_SERVICE_BASE_URL+"pushstatus"+"/"+pushState+"/";
			WebResource webResource = client.resource(pushStateUrl);
			L.debug("Invoking create schedule {}: ", WEB_SERVICE_BASE_URL);
			String response = webResource.accept(MediaType.APPLICATION_JSON).get(
					String.class);
			L.debug("findAllSchedule successful ");
			return getAllScheduleObject(response);

		} catch (UniformInterfaceException uie) {
			L.error("Encountered exception {}: ", uie);
			return null;
		} finally {
			if (clientResponse != null) {
				clientResponse.close();
			}
		}
	}
	
	public Schedule findSchedule(long id){

		ClientResponse clientResponse = null;
		try {
			String pushStateUrl = WEB_SERVICE_BASE_URL+id;
			WebResource webResource = client.resource(pushStateUrl);
			L.debug("Invoking create schedule {}: ", WEB_SERVICE_BASE_URL);
			String response = webResource.accept(MediaType.APPLICATION_JSON).get(
					String.class);
			L.debug("findAllSchedule successful ");
			return getScheduleObject(response);

		} catch (UniformInterfaceException uie) {
			L.error("Encountered exception {}: ", uie);
			return null;
		} finally {
			if (clientResponse != null) {
				clientResponse.close();
			}
		}
	}
	
	public int update(Schedule schedule)
			throws UnknownResourceException {

		String url = WEB_SERVICE_BASE_URL + schedule.getId();
		ClientResponse clientResponse = null;

		try {
			WebResource webResource = client.resource(url);
			L.debug("Invoking update Schedule {}: ", url);
			clientResponse = webResource.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.put(ClientResponse.class, getJSONFromObject(schedule));

			if (clientResponse.getStatus() == 200) {
				L.debug("Update Schedule successful ");
				return 1;
			}
			L.debug("Update Schedule failure ");
			return 0;
		} catch (UniformInterfaceException uie) {
			L.error("Encountered exception {}: ", uie);
			return 0;
		} finally {
			if (clientResponse != null) {
				clientResponse.close();
			}
		}
	}

	
	public ArrayList<Schedule> getAllScheduleObject(String string) {
		ArrayList<Schedule> list = new ArrayList<Schedule>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			list = mapper.readValue(string, TypeFactory.defaultInstance()
					.constructCollectionType(List.class, Schedule.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}


	public String getJSONFromObject(Schedule schedule) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(schedule);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Schedule getScheduleObject(String string) {

		Schedule schedule = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			schedule = mapper.readValue(string,
					new TypeReference<Schedule>() {
					});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return schedule;
	}
}
