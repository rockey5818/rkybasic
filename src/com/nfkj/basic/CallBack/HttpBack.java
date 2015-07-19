package com.nfkj.basic.CallBack;

import org.json.JSONObject;

 /**  
 * @author Rockey
 * @Description todo
 */
public interface HttpBack 
{
	//overrid to do anythings after http request success
	void onSuccess(JSONObject response);
	//overrid to do anythings after http request fail
	void onFailure();
}
