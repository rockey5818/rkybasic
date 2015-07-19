package com.nfkj.basic.util;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Rockey
 */
public final class CoreSettings
{
    public static Network CURRENT_NETWORK = Network.INTERNET;

    private static final String DEFAULT_WAN_RESOURCE_URL_PREFIX = "http://r.nfkj.com/";
    private static final String DEFAULT_LAN_RESOURCE_URL_PREFIX = "http://r.nfkj.net/";
    private static final String DEV_DEFAULT_WAN_RESOURCE_URL_PREFIX = "http://r.nfkj.com/";
    private static final String DEV_DEFAULT_LAN_RESOURCE_URL_PREFIX = "http://r.nfkj.com/";

    // ================================================================== *
  
    private static String resourceUrlPrefix;
    
    /**
     * Don't let anyone instantiate this class.
     */
    private CoreSettings()
    {
        // This constructor is intentionally empty.
    }

    public static String getResourceUrlPrefix()
    {
      return null;
    }

    public static void setResourceUrlPrefix(final String prefix)
    {
        CoreSettings.resourceUrlPrefix = prefix;
    }

    private static String getOriginalHttpRequestUrlRoot()
    {
       return null;
    }

    private static String getBackupHttpRequestUrlRoot()
    {
      return null;
    }

    private static String getBackupResourceUrlPrefix(String urlPrefix)
    {

    	return null;
    }

   
    public static boolean isHostError(int httpStatusCode)
    {
        final boolean result;

        switch (httpStatusCode)
        {
            case 502:
            case 503:
            case 504:
                result = true;
                break;
            default:
                result = false;
                break;
        }

        return result;
    }

    public enum Network
    {
        INTERNET, INTRANET
    }
    public enum HTTPRootType
    {
        UNKNOWN, API, UAPI, RESOURCE
    }

}
