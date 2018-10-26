package common;


import java.util.HashMap;

import cert.Context;
import javapns.communication.ConnectionToAppleServer;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.Payload;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

public class ApnsUtil {
	public static int RUN_MODE_DEVELOPMENT = 1;
	public static int RUN_MODE_PRODUCTION = 2;
	
	public static String sendApns(HashMap pushInfo) throws Exception {
		try {
			int runMode = 2;
			
			Payload payLoad = new PushNotificationPayload(
					pushInfo.get("cntnt").toString(), 
					1, 
					"default");

			PushNotificationManager manager = new PushNotificationManager();
			
			String host = null;
			String certificatePath = null;
			if (runMode == RUN_MODE_DEVELOPMENT) {
				host = "gateway.sandbox.push.apple.com";
				certificatePath = Context.ApnsKeyPath;
			} else if (runMode == RUN_MODE_PRODUCTION) {
				host = "gateway.push.apple.com";
				certificatePath = Context.ApnsKeyPath;
			}
			
			System.out.println("host === " + host);
			int port = 2195;
			String certificatePassword = "Your password";
			certificatePassword = Context.ApnsCertKeyPassword;
			Device client = new BasicDevice(pushInfo.get("appDeviceToken").toString());
			client.setDeviceId("iPhone");
			manager.initializeConnection(new AppleNotificationServerBasicImpl(
					certificatePath, 
					certificatePassword, 
					ConnectionToAppleServer.KEYSTORE_TYPE_PKCS12, 
					host, 
					port
					));
			PushedNotification notification = manager.sendNotification(client, payLoad, true);
		} catch (Exception ex) {
			ex.printStackTrace(); 
		}
		
		return "iPhone sendNotification Success";
	}
	
	
	public void sendApns2(int runMode, String deviceToken, String alertMessage, int badgeCount, String soundFile) throws Exception {
		try {
			
			Payload payLoad = new PushNotificationPayload(
					alertMessage, 
					badgeCount, 
					soundFile);
			
			PushNotificationManager manager = new PushNotificationManager();
			
			String host = null;
			String certificatePath = null;
			if (runMode == RUN_MODE_DEVELOPMENT) {
				host = "gateway.sandbox.push.apple.com";
				certificatePath = Context.ApnsKeyPath;
			} else if (runMode == RUN_MODE_PRODUCTION) {
				host = "gateway.push.apple.com";
				certificatePath = Context.ApnsKeyPath;
			}
			
			System.out.println("host === " + host);
			int port = 2195;
			String certificatePassword = "Your password";
			certificatePassword = Context.ApnsCertKeyPassword;
			Device client = new BasicDevice(deviceToken);
			client.setDeviceId("iPhone");
			
			manager.initializeConnection(new AppleNotificationServerBasicImpl(
					certificatePath, 
					certificatePassword, 
					ConnectionToAppleServer.KEYSTORE_TYPE_PKCS12, 
					host, 
					port
					));
			PushedNotification notification = manager.sendNotification(client, payLoad, true);
			
		} catch (Exception ex) {
			ex.printStackTrace(); 
		}
	}
	
	public static void main(String... args) throws Exception{
		ApnsUtil apns = new ApnsUtil();
		apns.sendApns2(
				RUN_MODE_PRODUCTION,
				"9fc578ffd5f0b5701c391979da27b3248883eebf787cd5212806379db234dcfd",
				"test",
				1,
				"default");
	}
}