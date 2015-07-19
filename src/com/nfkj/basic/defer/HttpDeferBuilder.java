package com.nfkj.basic.defer;


import org.json.JSONObject;

import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.nfkj.basic.CallBack.CallBack;

 /**  
 * @author Rockey
 * @Description todo
 */
public abstract class HttpDeferBuilder
{
	public Request<JSONObject>  buildJsonObjectRequestWithGet(String url,final CallBack callback)
	{
		return new JsonObjectRequest(url , null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				callback.onSuccess(response);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onFailure();
			}
		});
	}
	
	public Request<JSONObject>  buildJsonObjectRequestWithPost(String url,JSONObject body,final CallBack callback)
	{
		return new JsonObjectRequest(url , body, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				callback.onSuccess(response);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onFailure();
			}
			
		}); 
	}
}
