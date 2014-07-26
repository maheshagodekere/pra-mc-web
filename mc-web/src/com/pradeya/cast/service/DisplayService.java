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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pradeya.cast.domain.Display;
import com.pradeya.cast.ui.bean.UnknownResourceException;
import com.pradeya.cast.util.CastUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

@Service
public class DisplayService {

	private Logger L = LoggerFactory.getLogger(DisplayService.class);

	//Mediacast Server details
	
	public static final String WEB_SERVICE_CONTEXT = "mc-webservice";
	public static final String WEB_SERVICE_DISPLAY_PATH = "display";

	public static String WEB_SERVICE_BASE_URL = "http://" + CastUtil.SERVER_IP
			+ ":" + CastUtil.SERVER_PORT + "/" + WEB_SERVICE_CONTEXT + "/"
			+ WEB_SERVICE_DISPLAY_PATH + "/";

	private Client client = Client.create();

	public ArrayList<Display> findAllDisplay() {
		L.debug("findAllDisplay {}: ", WEB_SERVICE_BASE_URL);
		new Throwable().printStackTrace();
		WebResource webResource = client.resource(WEB_SERVICE_BASE_URL);
		String response = webResource.accept(MediaType.APPLICATION_JSON).get(
				String.class);
		L.debug("findAllDisplay successful ");
		return getAllDisplayObject(response);
	}

	public boolean createDisplay(Display display) {

		ClientResponse clientResponse = null;
		try {

			WebResource webResource = client.resource(WEB_SERVICE_BASE_URL);
			L.debug("Invoking create display {}: ", WEB_SERVICE_BASE_URL);
			clientResponse = webResource.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, getJSONFromObject(display));

			if (clientResponse.getStatus() == 200) {
				L.debug("createDisplay successful ");
				return true;
			}
			L.debug("createDisplay failure ");

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
	
	public int update(Display display)
			throws UnknownResourceException {

		String url = WEB_SERVICE_BASE_URL + display.getId();
		ClientResponse clientResponse = null;

		try {
			WebResource webResource = client.resource(url);
			L.debug("Invoking update Display {}: ", url);
			clientResponse = webResource.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.put(ClientResponse.class, getJSONFromObject(display));

			if (clientResponse.getStatus() == 200) {
				L.debug("Update Display successful ");
				return 1;
			}
			L.debug("Update Display failure ");
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

	
	public ArrayList<Display> getAllDisplayObject(String string) {
		ArrayList<Display> list = new ArrayList<Display>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			list = mapper.readValue(string, TypeFactory.defaultInstance()
					.constructCollectionType(List.class, Display.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}


	public String getJSONFromObject(Display display) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(display);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
