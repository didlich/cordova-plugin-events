package org.app.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.util.Log;

public class EventsExample extends CordovaPlugin {
    
    private static final String TAG = "EventsExample";
	
	private CallbackContext eventCallbackContext;
    private CallbackContext callbackContext;

	public static final String EVENT_FOO = "foo-event";
    public static final String ACTION_STATUS = "status";
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		try {
            
			String msg;
            this.callbackContext = callbackContext;
			
            if (ACTION_STATUS.equals(action)) {			  
			  Log.d(TAG, ACTION_STATUS);
			  sendEvent(EVENT_FOO);
			  return true;
			}
            
            if ("method".equals(action)) {
                Log.d(TAG, "method");			  
                this.eventCallbackContext = callbackContext;
                return true;
            }
			
			
			String err = "invalid-action";
			error(createJson(err));
			Log.e(TAG, err);            
			return false;
		} catch(Exception e) {
			Log.e(TAG, "Exception: " + e.getMessage());
			error(createJson("Exception: " + e.getMessage()));
			return false;
		} 
	}

    private JSONObject createJson(String msg) {        
		return createJson(msg, new ArrayList<String>());
	}

	private JSONObject createJson(String msg, Collection<String> data) {
		JSONObject json = null;
		JSONArray array = new JSONArray(data);
		try {                       
			json = new JSONObject();
			json.put("msg", msg);            
			json.put("data", array);
		} catch (JSONException e) {
			Log.d(TAG, "Can not create JSON: " + e);
		}
		return json;
	}
	

	private void error(JSONObject json) {
		PluginResult progressResult = new PluginResult(PluginResult.Status.ERROR, json);     
		progressResult.setKeepCallback(true);
		callbackContext.sendPluginResult(progressResult);
	}

	private void success(JSONObject json) {
		PluginResult progressResult = new PluginResult(PluginResult.Status.OK, json);     
		progressResult.setKeepCallback(true);
		callbackContext.sendPluginResult(progressResult);
	}
    
    private void sendEvent(String event) {
        if (this.eventCallbackContext != null) {            
            PluginResult dataResult = new PluginResult(PluginResult.Status.OK, event);
            dataResult.setKeepCallback(true);
            this.eventCallbackContext.sendPluginResult(dataResult);
        }
    }
}
