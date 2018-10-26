package common;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.JSONObject;

import cert.Context;


public class FcmUtil {
	
	public static String resTest;
	
    public static String pushFCMNotification( HashMap pushInfo )
            throws Exception {
    	 
    	
    	String authKey = Context.FcmKey; // You FCM AUTH key
    	String FCMUrl = "https://fcm.googleapis.com/fcm/send";
        
        URL url = new URL(FCMUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        JSONObject info = new JSONObject();
        
        info.put("title", pushInfo.get("title").toString()); // Notification body
        info.put("body", pushInfo.get("cntnt").toString()); // Notification body
        
        System.out.println("appDeviceToken ==== " + pushInfo.get("appDeviceToken").toString().trim());
        String appDeviceToken = pushInfo.get("appDeviceToken").toString().trim();
        json.put("notification", info);
        json.put("to", pushInfo.get("appDeviceToken").toString().trim()); // deviceID
                
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(json.toString());
        wr.flush();
        
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        String resStr = "";
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
        	resStr = resStr + output;
        }

        conn.disconnect();
        
        return resStr;
    }
    
    public static void main(String[] args) throws Exception {
    	
    	HashMap pushInfo = new HashMap();
    	    	
    	pushInfo.put("title", "test");
    	pushInfo.put("cntnt", "test.");
    	pushInfo.put("appDeviceToken", Context.FcmTestTokenKey);
    	
    	try {
    		resTest = pushFCMNotification( pushInfo );
    		System.out.println("resTest == "+resTest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }


}
