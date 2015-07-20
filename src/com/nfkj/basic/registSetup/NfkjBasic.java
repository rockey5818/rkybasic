package com.nfkj.basic.registSetup;

import com.nfkj.basic.behind.json.JSONAndroidFactoryImpl;
import com.nfkj.basic.defer.CoreDeferObject;
import com.nfkj.basic.defer.DeferBase64Imp;
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
			 registerBase64Util();
			 registerJsonFactory();
		}
		
		private void registerBase64Util() 
		{
		  if (CoreDeferObject.get().isRegisterDeferBase64())
	        {
			  CoreDeferObject.get().registerDeferBase64(new DeferBase64Imp());
	        }
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
