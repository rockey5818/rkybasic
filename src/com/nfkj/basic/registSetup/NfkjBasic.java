package com.nfkj.basic.registSetup;

import com.android.volley.Request;
import com.nfkj.basic.behind.json.JSONAndroidFactoryImpl;
import com.nfkj.basic.facade.NFacade;
/**
 * 
 * @author Rockey
 *	initialization http ,datamodel ,cryption and so on  modules
 */
public class NfkjBasic
{
		/**
		 * don't let anyone to create
		 */
		private NfkjBasic()
		{
			
		}
		
		public static NfkjBasic  INSTANCE = new NfkjBasic();
		
		public static void registDevice()
		{
			
		}
		
		 /**
	     * Register JSON factory.
	     */
	    private static void registerJsonFactory()
	    {
	        if (!NFacade.isJsonFactoryRegistered())
	        {
	        	NFacade.registerJSONFactory(new JSONAndroidFactoryImpl());
	        }
	    }
		
}
