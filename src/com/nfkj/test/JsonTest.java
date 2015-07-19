package com.nfkj.test;

import org.json.me.JSONException;
import org.json.parser.JSONParser;

import com.nfkj.basic.behind.json.JSONAndroidFactoryImpl;
import com.nfkj.basic.defer.CoreDeferObject;

 /**  
 * @author Rockey
 * @Description todo
 */
public class JsonTest {
	public static void main(String[] args) {
//		CoreDeferObject.get().setJsonFactory(new JSONAndroidFactoryImpl());
//		try {
//		People p=	JSONParser.fromJson("{\"id\":\"1\",\"name\":\"d\",\"des\":\"logo\"}", People.class);
//			
//		System.out.println(p.getId());
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} 
//		
	    String putStr = "{\"b\":\"b\",\"bb\":\"b\"}";
		JSONAndroidFactoryImpl fc = new JSONAndroidFactoryImpl();
		try {
			fc.createJSONObject(putStr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
