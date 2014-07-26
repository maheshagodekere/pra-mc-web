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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pradeya.cast.domain.Mail;
import com.pradeya.cast.ui.bean.UnknownResourceException;
import com.pradeya.cast.util.CastUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@SuppressWarnings("serial")
@Service
public class MailService{
	private static Logger L = LoggerFactory
			.getLogger(OrganizationService.class);
	
	//Mediacast Server details
	
	public static final String WEB_SERVICE_CONTEXT = "mc-webservice";
	public static final String WEB_SERVICE_ORGANIZATION_PATH = "mail";

	public static String WEB_SERVICE_BASE_URL = "http://"+CastUtil.SERVER_IP + ":"
			+ CastUtil.SERVER_PORT + "/" + WEB_SERVICE_CONTEXT + "/"
			+ WEB_SERVICE_ORGANIZATION_PATH + "/";
	private Client client = Client.create();

	public ArrayList<Mail> findAllMail() {
		L.debug("findAllMail {}: ", WEB_SERVICE_BASE_URL);
		WebResource webResource = client.resource(WEB_SERVICE_BASE_URL);
		String response = webResource.accept(MediaType.APPLICATION_JSON).get(String.class);
				
		L.debug("findAllOrganization successful ");
		return getAllMailObject(response);
	}

	public boolean createMail(Mail mail) {

		ClientResponse clientResponse = null;
		try {

			WebResource webResource = client.resource(WEB_SERVICE_BASE_URL);
			L.debug("invoking create Organization {}: ", WEB_SERVICE_BASE_URL);
			clientResponse = webResource
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, getJSONFromObject(mail));

			if (clientResponse.getStatus() == 200) {
				L.debug("Create Organization successful ");
				return true;
			}
			L.debug("Create Organization failure ");
			return false;
		} finally {
			if (clientResponse != null) {
				clientResponse.close();
			}
		}
	}

	public int update(Mail mail)
			throws UnknownResourceException {

		String url = WEB_SERVICE_BASE_URL + mail.getId();
		ClientResponse clientResponse = null;

		try {
			WebResource webResource = client.resource(url);
			L.debug("Invoking update Organization {}: ", url);
			clientResponse = webResource.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.put(ClientResponse.class, getJSONFromObject(mail));

			if (clientResponse.getStatus() == 200) {
				L.debug("Create Organization successful ");
				return 1;
			}
			L.debug("Create Organization failure ");
			return 0;
		} finally {
			if (clientResponse != null) {
				clientResponse.close();
			}
		}
	}

	public String getJSONFromObject(Mail mail) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(mail);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Mail> getAllMailObject(String string) {
		ArrayList<Mail> list = new ArrayList<Mail>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			list = mapper.readValue(string, TypeFactory.defaultInstance()
					.constructCollectionType(List.class, Mail.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}
