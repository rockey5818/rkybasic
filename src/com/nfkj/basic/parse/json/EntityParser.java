package com.nfkj.basic.parse.json;

import org.json.me.*;

import com.nfkj.basic.util.Logger;
import com.nfkj.basic.util.Strings;
import static com.nfkj.basic.util.Preconditions.checkArgument;
import com.nfkj.basic.util.json.JSONUtil;


public class EntityParser
{
    protected static boolean hasKey(final String key, final JSONObjectDef jsonObj)
    {
        return JSONUtil.hasKey(key, jsonObj);
    }

    public static String getString(final String key, final JSONObjectDef jsonObj, final String defaultValue)
    {
        checkArgument(null != jsonObj);
        checkArgument(Strings.notNullOrEmpty(key));

        String result = defaultValue;
        if (null != jsonObj && Strings.notNullOrEmpty(key))
        {
            if (hasKey(key, jsonObj))
            {
                try
                {
                    result = JSONUtil.getStringFromJson(key, jsonObj);
                }
                catch (Throwable ex)
                {
                    result = defaultValue;
                }
            }
        }
        return result;
    }

    public static int getInteger(final String key, final JSONObjectDef jsonObj, final int defaultValue)
    {
        int result = defaultValue;
        final String strResult = getString(key, jsonObj, null);
        try
        {
            if (null != strResult)
            {
                result = Integer.parseInt(strResult);
            }
        }
        catch (Throwable ex)
        {
            Logger.logThrowable(ex);
        }
        return result;
    }

    public static long getLong(final String key, final JSONObjectDef jsonObj, final long defaultValue)
    {
        long result = defaultValue;
        final String strResult = getString(key, jsonObj, null);
        try
        {
            if (null != strResult)
            {
                result = Long.parseLong(strResult);
            }
        }
        catch (Throwable ex)
        {
            Logger.logThrowable(ex);
        }
        return result;
    }

    public static double getDouble(final String key, final JSONObjectDef jsonObj, final double defaultValue)
    {
        double result = defaultValue;
        final String strResult = getString(key, jsonObj, null);
        try
        {
            if (null != strResult)
            {
                result = Double.parseDouble(strResult);
            }
        }
        catch (Throwable ex)
        {
            Logger.logThrowable(ex);
        }
        return result;
    }

    public static boolean getBoolean(String key, JSONObjectDef jsonObj, boolean defaultValue)
    {
        boolean result = defaultValue;
        final String strResult = getString(key, jsonObj, null);
        try
        {
            if (null != strResult)
            {
                result = Strings.parseBoolean(strResult);
            }
        }
        catch (Throwable ex)
        {
            Logger.logThrowable(ex);
        }
        return result;
    }

    public static JSONObjectDef getJSONObject(final String key, final JSONObjectDef parent, final JSONObjectDef son)
    {
    	checkArgument(null != parent);
        checkArgument(Strings.notNullOrEmpty(key));

        JSONObjectDef result = son;
        if (null != parent && Strings.notNullOrEmpty(key))
        {
            if (hasKey(key, parent))
            {
                try
                {
                    result = JSONUtil.getObjectFromJson(key, parent);
                }
                catch (Throwable ex)
                {
                	Logger.logThrowable(ex);
                }
            }
        }
        
        return result;
    }

    public static JSONObjectDef getJSONObject(final int index, final JSONArrayDef parent, final JSONObjectDef son)
    {
    	checkArgument(null != parent);
        checkArgument(index >= 0);

        JSONObjectDef result = son;
        if (null != parent && index >= 0)
        {
            if (index < parent.length())
            {
                try
                {
                    result = JSONUtil.getObjectFromArray(index, parent);
                }
                catch (Throwable ex)
                {
                	Logger.logThrowable(ex);
                }
            }
        }
        return result;
    }

    public static JSONArrayDef getJSONArray(final String key, final JSONObjectDef parent, final JSONArrayDef son)
    {
    	checkArgument(null != parent);
        checkArgument(Strings.notNullOrEmpty(key));

        JSONArrayDef result = son;
        if (null != parent && Strings.notNullOrEmpty(key))
        {
            if (hasKey(key, parent))
            {
            	try
                {
                    result = JSONUtil.getArrayFromJson(key, parent);
                }
                catch (final Throwable ex)
                {
                    Logger.logThrowable(ex);
                }
            }
        }
        return result;
    }

    public static Object getObject(final int index, final JSONArrayDef parent, final JSONObjectDef son)
    {
    	checkArgument(null != parent);
        checkArgument(index >= 0);

        Object result = son;
        if (null != parent && index >= 0)
        {
            if (index < parent.length())
            {
                try
                {
                    result = parent.get(index);
                }
                catch (Throwable ex)
                {
                	Logger.logThrowable(ex);
                }
            }
        }
        return result;
    }
}
