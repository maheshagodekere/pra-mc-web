package com.pradeya.cast.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pradeya.cast.domain.Member;
import com.pradeya.cast.domain.Organization;
import com.pradeya.cast.ui.bean.UnknownResourceException;
import com.pradeya.cast.util.CastUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

@SuppressWarnings("serial")
@Service
public class OrganizationService implements Serializable{
	private static Logger L = LoggerFactory
			.getLogger(OrganizationService.class);

	//Mediacast Server details
//	public @Value("${mediacast.server.ip}") String SERVER_IP = "54.255.134.91";
//	public @Value("${mediacast.server.port}") String SERVER_PORT = "8080";
	
	public static final String WEB_SERVICE_CONTEXT = "mc-webservice";
	public static final String WEB_SERVICE_ORGANIZATION_PATH = "organization";

	public static String WEB_SERVICE_BASE_URL = "http://" + CastUtil.SERVER_IP
			+ ":" + CastUtil.SERVER_PORT + "/" + WEB_SERVICE_CONTEXT + "/"
			+ WEB_SERVICE_ORGANIZATION_PATH + "/";
	private Client client = Client.create();

	public ArrayList<Organization> findAllOrganization() {
		L.debug("findAllOrganization {}: ", WEB_SERVICE_BASE_URL);
		new Throwable().printStackTrace();
		WebResource webResource = client.resource(WEB_SERVICE_BASE_URL);
		String response = webResource.accept(MediaType.APPLICATION_JSON).get(
				String.class);
		L.debug("findAllOrganization successful ");
		return getAllOrgObject(response);
	}

	public boolean create(Organization organization) {

		ClientResponse clientResponse = null;
		try {

			WebResource webResource = client.resource(WEB_SERVICE_BASE_URL);
			L.debug("invoking create Organization {}: ", WEB_SERVICE_BASE_URL);
			clientResponse = webResource
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, getJSONFromObject(organization));

			if (clientResponse.getStatus() == 200) {
				L.debug("Create Organization successful ");
				return true;
			}
			L.debug("Create Organization failure ");

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

	public int update(Organization organization)
			throws UnknownResourceException {

		String url = WEB_SERVICE_BASE_URL + organization.getId();
		ClientResponse clientResponse = null;

		try {
			WebResource webResource = client.resource(url);
			L.debug("Invoking update Organization {}: ", url);
			clientResponse = webResource.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.put(ClientResponse.class, getJSONFromObject(organization));

			if (clientResponse.getStatus() == 200) {
				L.debug("Create Organization successful ");
				return 1;
			}
			L.debug("Create Organization failure ");
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

	public long findOrganizationId(String domain) {
		Client client = Client.create();
		String url = WEB_SERVICE_BASE_URL + "domain/" + domain + "/";

		WebResource webResource = client.resource(url);
		L.debug("Invoking findOrganizationId {}: ", url);
		String response = null;
		try {
			response = webResource.accept(MediaType.APPLICATION_JSON).get(
					String.class);
		} catch (UniformInterfaceException uie) {
			L.error("Encountered exception {}: ", uie);
			return 0;
		}
		L.debug("findOrganizationId successful ");
		return response == null ? 0 : Long.parseLong(response);

	}
	
	public Organization findOrganizationDetail(long orgId) {
		String response = null;
		Client client = Client.create();
		String url = WEB_SERVICE_BASE_URL + "detail/"+orgId ;

		WebResource webResource = client.resource(url);
		L.debug("Invoking findOrganizationDetail {}: ", url);
		try {
			response = webResource.accept(MediaType.APPLICATION_JSON).get(
					String.class);
		} catch (UniformInterfaceException uie) {
			L.error("Encountered exception {}: ", uie);
			return null;
		}

		L.debug("findOrganizationDetail successful ");
		return getOrgObject(response);
	}

	public Organization findAllMember(long orgId) {
		String response = null;
		Client client = Client.create();
		String url = WEB_SERVICE_BASE_URL + orgId + "/member";

		WebResource webResource = client.resource(url);
		L.debug("Invoking findAllMember {}: ", url);
		try {
			response = webResource.accept(MediaType.APPLICATION_JSON).get(
					String.class);
		} catch (UniformInterfaceException uie) {
			L.error("Encountered exception {}: ", uie);
			return null;
		}

		L.debug("findAllMember successful ");
		return getOrgObject(response);
	}

	public Organization findMember(long orgId, String username) {
		String response = null;
		Client client = Client.create();
		String url = WEB_SERVICE_BASE_URL + orgId + "/member" + "/"
				+ username;

		WebResource webResource = client.resource(url);
		L.debug("Invoking findAllMember {}: ", url);
		try {
			response = webResource.accept(MediaType.APPLICATION_JSON).get(
					String.class);
		} catch (UniformInterfaceException uie) {
			L.error("Encountered exception {}: ", uie);
			return null;
		}

		L.debug("findAllMember successful ");
		return getOrgObject(response);
	}

	public boolean createMember(long orgId, Member fp) {
		String url = WEB_SERVICE_BASE_URL + orgId + "/member";
		ClientResponse clientResponse = null;

		try {
			WebResource webResource = client.resource(url);
			L.debug("Invoking createMember {}: ", url);
			clientResponse = webResource.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, getJSONFromObject(fp));

			if (clientResponse.getStatus() == 200) {
				L.debug("createMember successful ");
				return true;
			}
			L.debug("createMember failure ");
			return false;
		} finally {
			if (clientResponse != null) {
				clientResponse.close();
			}
		}
	}

	public int updateMember(long orgId, Member fp)
			throws UnknownResourceException {
		String url = WEB_SERVICE_BASE_URL + orgId + "/member" + "/"
				+ fp.getUserName();
		ClientResponse clientResponse = null;

		try {
			WebResource webResource = client.resource(url);
			L.debug("Invoking updateMember {}: ", url);
			clientResponse = webResource.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.put(ClientResponse.class, getJSONFromObject(fp));

			if (clientResponse.getStatus() == 200) {
				L.debug("updateMember successful ");
				return 1;
			}
			L.debug("updateMember failure ");
			return 0;
		} finally {
			if (clientResponse != null) {
				clientResponse.close();
			}
		}
	}

	public String getJSONFromObject(Organization organization) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(organization);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getJSONFromObject(Member planner) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(planner);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Organization getOrgObject(String string) {

		Organization organization = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			organization = mapper.readValue(string,
					new TypeReference<Organization>() {
					});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return organization;
	}

	public ArrayList<Organization> getAllOrgObject(String string) {
		ArrayList<Organization> list = new ArrayList<Organization>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			list = mapper.readValue(string, TypeFactory.defaultInstance()
					.constructCollectionType(List.class, Organization.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	// ****************To Be DELETED *****************************************

	// public ArrayList<Member> getAllPlannerObject(String string) {
	// ArrayList<Member> list = new ArrayList<Member>();
	// ObjectMapper mapper = new ObjectMapper();
	// try {
	// list = mapper.readValue(
	// string,
	// TypeFactory.defaultInstance().constructCollectionType(
	// List.class, Member.class));
	// } catch (JsonParseException e) {
	// e.printStackTrace();
	// } catch (JsonMappingException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return list;
	// }

	// public Member getPlannerObject(String string) {
	//
	// Member planner = null;
	// ObjectMapper mapper = new ObjectMapper();
	//
	// try {
	// planner = mapper.readValue(string,
	// new TypeReference<Member>() {
	// });
	// } catch (JsonParseException e) {
	// e.printStackTrace();
	// } catch (JsonMappingException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return planner;
	// }

}