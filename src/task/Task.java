package task;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import common.ApnsUtil;
import common.FcmUtil;

/**
 * FCM And Apns run Task
 * @author LSKun
 *
 */
public class Task {
	public static int RUN_MODE_ANDROID = 1;
	public static int RUN_MODE_IOS = 2;
	Logger logger = Logger.getLogger(this.getClass());
	
	public static int phoneType = 1;
	
	public void println( String msg ) {
	  logger.info(msg);
	}
	
	public static void main(String[] args) {
	  
	  Task smartSchdl = new Task();
	  smartSchdl.println("Task START ============= ");
	  
	  Timer timer = new Timer();
	  //1000 = 1초
	  //test
	  timer.schedule(new testTask(), 1000, 10000);
	}
	
	public static class testTask extends TimerTask{
		Task smartSchdl = new Task();

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String sndRes = "";
			HashMap tmpParam = new HashMap();
    		tmpParam.put("appDeviceToken", "9fc578ffd5f0b5701c391979da27b3248883eebf787cd5212806379db234dcfd");
    		tmpParam.put("title", "테스트 push입니다.");
	        tmpParam.put("cntnt", "테스트 push입니다.");
	        
	        try {
	        	if(phoneType == Task.RUN_MODE_ANDROID){
	        		sndRes = FcmUtil.pushFCMNotification(tmpParam);		 
	        	} else {
	        		sndRes = ApnsUtil.sendApns(tmpParam);
	        	}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
