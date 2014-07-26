/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pradeya.cast.gcm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Simple implementation of a data store using standard Java collections.
 * <p>
 * This class is thread-safe but not persistent (it will lost the data when the
 * app is restarted) - it is meant just as an example.
 */
public final class Datastore {

  private static final List<String> regIds = new ArrayList<String>();
  private static final ConcurrentHashMap<String,String> regIdMap = new ConcurrentHashMap<String,String>();
  
  
  private static final Logger logger =
      Logger.getLogger(Datastore.class.getName());

  private Datastore() {
    throw new UnsupportedOperationException();
  }

  
  public static boolean anyDevices() {
	  return regIdMap.containsKey("gs.mahesha@gmail.com");
  }
  
  /**
   * Registers a device.
   */
  public static void register(String regId) {
    logger.info("Registering " + regId);
    synchronized (regIds) {
      regIds.add(regId);
    }
    regIdMap.put("gs.mahesha@gmail.com", regId);
    
    System.out.println("***** registration Successful");
  }

  /**
   * Unregisters a device.
   */
  public static void unregister(String regId) {
    logger.info("Unregistering " + regId);
    synchronized (regIds) {
      regIds.remove(regId);
    }
    regIdMap.remove("gs.mahesha@gmail.com", regId);
    System.out.println("***** unregistration Successful");

  }

  /**
   * Updates the registration id of a device.
   */
  public static void updateRegistration(String oldId, String newId) {
    logger.info("Updating " + oldId + " to " + newId);
    synchronized (regIds) {
      regIds.remove(oldId);
      regIds.add(newId);
      
    }
    regIdMap.replace("gs.mahesha@gmail.com",oldId,newId);
    //regIdMap.put("gs.mahesha@gmail.com", newId);
    System.out.println("***** update registration Successful");

  }

  /**
   * Gets all registered devices.
   */
  public static List<String> getDevices() {
    synchronized (regIds) {
      return new ArrayList<String>(regIds);
    }
  }

  
  /**
   * Gets all registered devices.
   */
  public static String getDevice(String id) {
      return regIdMap.get(id);
    
  }
}
