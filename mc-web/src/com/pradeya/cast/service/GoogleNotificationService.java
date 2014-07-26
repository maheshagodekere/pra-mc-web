package com.pradeya.cast.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.pradeya.cast.domain.Schedule;
import com.pradeya.cast.gcm.Datastore;

/**
 * Google Notification service to fire notification to devices via GCM
 * 
 */

@Service
public class GoogleNotificationService {

	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private ScheduleService scheduleService;

	// cannot exceed 1000 devices (GCM limit) per multicast
	// private static final int MULTICAST_SIZE = 1;
	public static final String MEDIA_ID = "mid";
	public static final String SCHEDULE_ID = "sid";
	
	private static final String API_KEY = "AIzaSyCu88TVLiXgqu8ktPwAaUMa47hqfHye0eM";
	private static final Sender sender = new Sender(API_KEY);

	public void dispatch() {

		if(!Datastore.anyDevices()) {
			System.out.println("No registred devices yet...nothing to send!");
			return;
		}
//		Iterable<Schedule> itm = scheduleService.findByPushState("PENDING".toLowerCase());
//		System.out.println("### Got all the PENDING Schedules!"+itm);
//		
//
//
//		System.out.println("$$$ Starting dispatch execution...");
//
//		System.out.println("Creating/Using 5 TaskExecutor Threads for GCM...");
//
//		Iterator<Schedule> it = itm.iterator();
//
//		while (it.hasNext()) {
//			final Schedule schedule = it.next();
//			System.out.println("Schedule..." + schedule.getMediaId());
//
//			// final Media media = mediaService.findOne(schedule.getMediaId());
//
//			// System.out.println("Media..."+media);
//
//			if (this.taskExecutor != null) {
//				this.taskExecutor.execute(new Runnable() {
//					public void run() {
//						executorAsync(schedule);
//						try {
//							Thread.sleep(30000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				});
//			}
//		}

		System.out.println("$$$ Completed dispatch execution...");
	}

	private void executorAsync(final Schedule schedule) {

		final List<String> devices = new ArrayList<String>(1);
		devices.add(Datastore.getDevice("gs.mahesha@gmail.com"));
	    System.out.println("***** getDevice: "+ Datastore.getDevice("gs.mahesha@gmail.com"));

		/* 000000000000000000000000000000000000000000000000 */

		Message message = new Message.Builder().
								addData(MEDIA_ID,""+schedule.getMediaId()).
								addData(SCHEDULE_ID, ""+schedule.getId()).
								build();
		System.out.println("***** Message for server: "+message); 
		MulticastResult multicastResult;
		try {
			multicastResult = sender.send(message, devices, 5);
		} catch (IOException e) {
			System.out.println("Error posting messages" + e);
			return;
		}
		List<Result> results = multicastResult.getResults();
		// analyze the results
		for (int i = 0; i < devices.size(); i++) {
			String regId = devices.get(i);
			Result result = results.get(i);
			String messageId = result.getMessageId();
			if (messageId != null) {
				System.out.println("Succesfully sent message to device: "
						+ regId + "; messageId = " + messageId);
				String canonicalRegId = result.getCanonicalRegistrationId();
				if (canonicalRegId != null) {
					// same device has more than on registration id: update it
					System.out.println("canonicalRegId " + canonicalRegId);
					Datastore.updateRegistration(regId, canonicalRegId);
				}
//				schedule.setPushState("PUSHED");
//				scheduleService.update(schedule);
				//scheduleService. updatePushState(schedule.getId(),"PUSHED");
			} else {
				String error = result.getErrorCodeName();
				if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
					// application has been removed from device - unregister it
					System.out.println("Unregistered device: " + regId);
					Datastore.unregister(regId);
				} else {
					System.out.println("Error sending message to " + regId
							+ ": " + error);
				}
			}
		}

		/* 00000000000000000000000000000000000000000000000000000000000000000 */

		//System.out.println("@@@@@@@@@@@@@@@@@@Starting Async execution..."
		//		+ schedule.getMediaId());
		String threadName = Thread.currentThread().getName();
		//System.out.println("   " + threadName
		//		+ "@@@@@@@@@@@@@@@@@@@ has began working.");
		try {
			
			//Sleeping is temporary....need to take this out
			Thread.sleep(100);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("@@@@@ Completed Async execution...");
	}
}
