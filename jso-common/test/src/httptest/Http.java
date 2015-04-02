/**
 * Copyright 2005 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package httptest;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;


/** An implementation of the Http protocol. */
public class Http {

  static final int BUFFER_SIZE = 8 * 1024;

  private static final int MAX_REDIRECTS =2;

  static String PROXY_HOST = null;
  static int PROXY_PORT = 8080;
  static boolean PROXY = (PROXY_HOST != null && PROXY_HOST.length() > 0);
  
  static int TIMEOUT = 600000;
  static int MAX_CONTENT= 64*1024;

  static int MAX_DELAYS= 20;
  static int MAX_THREADS_PER_HOST =1;

  static String AGENT_STRING = getAgentString();

  static long SERVER_DELAY =5* 1000;

  static {
  }

  /** Maps from InetAddress to a Long naming the time it should be unblocked.
   * The Long is zero while the address is in use, then set to now+wait when
   * a request finishes.  This way only one thread at a time accesses an
   * address. */
  private static HashMap BLOCKED_ADDR_TO_TIME = new HashMap();
    
  /** Maps an address to the number of threads accessing that address. */
  private static HashMap THREADS_PER_HOST_COUNT = new HashMap();

  /** Queue of blocked InetAddress.  This contains all of the non-zero entries
   * from BLOCKED_ADDR_TO_TIME, ordered by increasing time. */
  private static LinkedList BLOCKED_ADDR_QUEUE = new LinkedList();


  private static InetAddress blockAddr(URL url) {
    InetAddress addr=null;
      try {
		addr = InetAddress.getByName(url.getHost());
	} catch (UnknownHostException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    
    int delays = 0;
    while (true) {
      cleanExpiredServerBlocks();                 // free held addresses

      Long time;
      synchronized (BLOCKED_ADDR_TO_TIME) {
        time = (Long) BLOCKED_ADDR_TO_TIME.get(addr);
        if (time == null) {                       // address is free

          // get # of threads already accessing this addr
          Integer counter = (Integer)THREADS_PER_HOST_COUNT.get(addr);
          int count = (counter == null) ? 0 : counter.intValue();
          
          count++;                              // increment & store
          THREADS_PER_HOST_COUNT.put(addr, new Integer(count));
          
          if (count >= MAX_THREADS_PER_HOST) {
            BLOCKED_ADDR_TO_TIME.put(addr, new Long(0)); // block it
          }
          return addr;
        }
      }

      if (delays == MAX_DELAYS)
		try {
			throw new Exception(url.toExternalForm()+"Exceeded http.max.delays: retry later.");
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

      long done = time.longValue();
      long now = System.currentTimeMillis();
      long sleep = 0;
      if (done == 0) {                            // address is still in use
        sleep = SERVER_DELAY;                     // wait at least delay

      } else if (now < done) {                    // address is on hold
        sleep = done - now;                       // wait until its free
      }

      try {
        Thread.sleep(sleep);
      } catch (InterruptedException e) {}
      delays++;
    }
  }
  
  private static void cleanExpiredServerBlocks() {
    synchronized (BLOCKED_ADDR_TO_TIME) {
      while (!BLOCKED_ADDR_QUEUE.isEmpty()) {
        InetAddress addr = (InetAddress)BLOCKED_ADDR_QUEUE.getLast();
        long time = ((Long)BLOCKED_ADDR_TO_TIME.get(addr)).longValue();
        if (time <= System.currentTimeMillis()) {
          BLOCKED_ADDR_TO_TIME.remove(addr);
          BLOCKED_ADDR_QUEUE.removeLast();
        } else {
          break;
        }
      }
    }
  }

  private static void unblockAddr(InetAddress addr) {
    synchronized (BLOCKED_ADDR_TO_TIME) {
      int addrCount = ((Integer)THREADS_PER_HOST_COUNT.get(addr)).intValue();
      if (addrCount == 1) {
        THREADS_PER_HOST_COUNT.remove(addr);
        BLOCKED_ADDR_QUEUE.addFirst(addr);
        BLOCKED_ADDR_TO_TIME.put
          (addr, new Long(System.currentTimeMillis()+SERVER_DELAY));
      }
      else {
        THREADS_PER_HOST_COUNT.put(addr, new Integer(addrCount - 1));
      }
    }
  }
  
  public String getProtocolOutput(URL fle) {
    String urlString = fle.toString();
    try {
      URL url = new URL(urlString);

      int redirects = 0;
      while (true) {
        
//        if (!RobotRulesParser.isAllowed(url))
//          throw new ResourceGone(url, "Blocked by robots.txt");
        
        InetAddress addr = blockAddr(url);
        HttpResponse response;
        try {
          response = new HttpResponse(urlString, url); // make a request
        } finally {
          unblockAddr(addr);
        }
        
        int code = response.getCode();
        
        return new String(response.toContent(),"utf-8");
      }
    } catch (Exception e) {
      return null;
    } 
  }

  private static String getAgentString() {
    String agentName = "NutchCVS";
    String agentVersion = "NutchCVS";
    String agentDesc = "NutchCVS";;
    String agentURL = "http://lucene.apache.org/nutch/bot.html";
    String agentEmail = "nutch-agent@lucene.apache.or";

    StringBuffer buf= new StringBuffer();

    buf.append(agentName);
    if (agentVersion != null) {
      buf.append("/");
      buf.append(agentVersion);
    }
    if ( ((agentDesc != null) && (agentDesc.length() != 0))
         || ((agentEmail != null) && (agentEmail.length() != 0))
         || ((agentURL != null) && (agentURL.length() != 0)) ) {
      buf.append(" (");

      if ((agentDesc != null) && (agentDesc.length() != 0)) {
        buf.append(agentDesc);
        if ( (agentURL != null) || (agentEmail != null) )
          buf.append("; ");
      }

      if ((agentURL != null) && (agentURL.length() != 0)) {
        buf.append(agentURL);
        if (agentEmail != null) 
          buf.append("; ");
      }

      if ((agentEmail != null) && (agentEmail.length() != 0)) 
        buf.append(agentEmail);

      buf.append(")");
    }
    return buf.toString();
  }

  /** For debugging. */
  public static void main(String[] args) throws Exception {

    Http http = new Http();

    String content=http.getProtocolOutput(new URL("http://jakarta.apache.org"));
//    System.out.println(content);

  }

}
