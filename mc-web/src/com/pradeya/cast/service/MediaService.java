package com.pradeya.cast.service;

import java.io.IOException;
import java.io.Serializable;
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

import com.pradeya.cast.domain.Media;
import com.pradeya.cast.domain.Media;
import com.pradeya.cast.domain.Media;
import com.pradeya.cast.util.CastUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

@SuppressWarnings("serial")
@Service
public class MediaService implements Serializable {

	private Logger L = LoggerFactory.getLogger(MediaService.class);

	public static final String WEB_SERVICE_CONTEXT = "mc-webservice";
	public static final String WEB_SERVICE_MEDIA_PATH = "media";

	public static String WEB_SERVICE_BASE_URL = "http://" + CastUtil.SERVER_IP
			+ ":" + CastUtil.SERVER_PORT + "/" + WEB_SERVICE_CONTEXT + "/"
			+ WEB_SERVICE_MEDIA_PATH + "/";
	private Client client = Client.create();

	public Media createMedia(Media media) {

		ClientResponse clientResponse = null;
		try {

			WebResource webResource = client.resource(WEB_SERVICE_BASE_URL);
			L.debug("Invoking create media {}: ", WEB_SERVICE_BASE_URL);
			clientResponse = webResource.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, getJSONFromObject(media));

			if (clientResponse.getStatus() == 200) {
				L.debug("createMedia successful ");
				return getMediaObject(clientResponse.getEntity(String.class));
			}
			L.debug("createMedia failure ");

			return null;
		} catch (UniformInterfaceException uie) {
			L.error("Encountered exception {}: ", uie);
			return null;
		} finally {
			if (clientResponse != null) {
				clientResponse.close();
			}
		}
	}
	
	public Media findMedia(long mediaId) {
		String response = null;
		Client client = Client.create();
		String url = WEB_SERVICE_BASE_URL +mediaId ;

		WebResource webResource = client.resource(url);
		L.debug("Invoking findMedia {}: ", url);
		try {
			response = webResource.accept(MediaType.APPLICATION_JSON).get(
					String.class);
		} catch (UniformInterfaceException uie) {
			L.error("Encountered exception {}: ", uie);
			return null;
		}

		L.debug("findMedia successful ");
		return getMediaObject(response);
	}
	
	
	public ArrayList<Media> findMediaByUserName(String username) {
		String response = null;
		Client client = Client.create();
		String url = WEB_SERVICE_BASE_URL +"user/"+username ;

		L.debug("findAllMedia {}: ", url);
		WebResource web_resource = client.resource(url);
		response = web_resource.accept(MediaType.APPLICATION_JSON).get(
				String.class);
		L.debug("findAllMedia successful");
		return (getMediaObjectList(response));

	}
	

	public String getJSONFromObject(Media media) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(media);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Media getMediaObject(String string) {

		Media media = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			media = mapper.readValue(string,
					new TypeReference<Media>() {
					});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return media;
	}
	
	public ArrayList<Media> getMediaObjectList(String string) {

		ArrayList<Media> mediaList = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			mediaList = mapper.readValue(string, TypeFactory.defaultInstance()
					.constructCollectionType(List.class, Media.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mediaList;
	}

	

}
