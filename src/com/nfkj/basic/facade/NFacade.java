package com.nfkj.basic.facade;

import org.json.me.JSONFactory;

import com.nfkj.basic.defer.CoreDeferObject;

/**
 * 
 * @author Rockey
 */
public class NFacade 
{
	private final static NFacade INSTANCE  = new NFacade();
	
	private NFacade()
	{
	}
	
	public  static NFacade get()
	{
		return INSTANCE;
	}
	
    public static boolean isJsonFactoryRegistered()
    {
    	return CoreDeferObject.get().isJsonFactoryRegistered();
    }
    
    public static void registerJSONFactory(final JSONFactory jsonFactory)
    {
        CoreDeferObject.get().setJsonFactory(jsonFactory);
    }
    
	
}