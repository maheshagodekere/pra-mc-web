package com.pradeya.spring.mvc.rest.exhandler;

//http://kamisama.github.io/cal-heatmap/datas-years.json
//C:/Users/g702998/mahesha/personal/pradeya/cal/data.json
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import javax.print.CancelablePrintJob;
import javax.servlet.ServletContext;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import com.pradeya.cast.ui.bean.UserUIMedia;

import sun.util.logging.resources.logging;

/**
 * Example Spring MVC Controller that will throw exceptions for specific URLs to
 * show exception handling.
 */

@Controller
@RequestMapping("/json")
public class MediaHeatmap {
	
	private Logger logger = LoggerFactory.getLogger(MediaHeatmap.class);
	
	@Autowired
	ServletContext servletContext;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/calendar", method = GET)
	@ResponseBody
	public HashMap<String, Integer> getData() {
		logger.debug("JSON Req url invoked: /json/calendar");
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2014, Calendar.APRIL, 01);

		for (int i = 2, j = 10; i < 20; i++, j += 5) {

			hm.put((calendar.getTimeInMillis() / 1000L) + "", j);
			calendar.add(Calendar.DAY_OF_MONTH, i);
		}
		logger.debug("JSON Res url invoked: /json/calendar successful");
		return hm;

		// try {
		// //jsonText =
		// readFile("C:/Users/g702998/mahesha/personal/pradeya/cal2/data.json");
		// jsonText = readFile(servletContext.getRealPath("/WEB-INF/data.json")
		// );
		//
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// ObjectMapper jsonMapper = new ObjectMapper();
		// JsonNode node = null;
		// try {
		// node = jsonMapper.readTree(jsonText);
		// } catch (JsonProcessingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// JsonNode msgNode = node.path("timeStamp");
		// System.out.println(msgNode);
		// return jsonMapper.convertValue(msgNode, HashMap.class);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/calendar/slot/{date}", method = GET)
	@ResponseBody
	public HashMap<String, Integer> getTime(@PathVariable long date) {
		logger.debug("JSON url invoked: /json/calendar/slot/{}",date);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2014, Calendar.APRIL, 01);
		calendar.set(Calendar.HOUR, 10);

		if(date==0L){
			calendar.setTimeInMillis(System.currentTimeMillis());
		}else{
			calendar.setTimeInMillis(date*1000L);
		}

		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		

		for (int i = 2, j = 10; i < 8; i++, j += 1) {

			hm.put((calendar.getTimeInMillis() / 1000L) + "", j);
			calendar.add(Calendar.DAY_OF_MONTH, i);
		}

		return hm;
	}

	private String readFile(String fpath) throws IOException {
		File f = new File(fpath);
		final BufferedReader r = new BufferedReader(new FileReader(f));
		final StringBuilder buf = new StringBuilder(1000);
		String line;
		while ((line = r.readLine()) != null) {
			buf.append(line);
			buf.append("\n");
		}
		return buf.toString();
	}

}
