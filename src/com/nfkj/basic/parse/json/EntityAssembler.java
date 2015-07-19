package com.nfkj.basic.parse.json;

import org.json.me.*;

import com.nfkj.basic.util.json.JSONUtil;

public class EntityAssembler
{
    protected static boolean hasKey(final String key, final JSONObjectDef jsonObj)
    {
        return JSONUtil.hasKey(key, jsonObj);
    }

    public static JSONObjectDef createJSONObject()
    {
        return JSONUtil.createJSONObject();
    }

    public static JSONArrayDef createJSONArray()
    {
        return JSONUtil.createJSONArray();
    }

    public static void putBoolean(final String key, final boolean value, final JSONObjectDef jsonObj)
    {
        if (value)
        {
            jsonObj.put(key, "1");
        }
        else
        {
            jsonObj.put(key, "0");
        }
    }

    public static void putInt(final String key, final int value, final JSONObjectDef jsonObj)
    {
        jsonObj.put(key, value);
    }

    public static void putLong(final String key, final long value, final JSONObjectDef jsonObj)
    {
        jsonObj.put(key, value);
    }

    public static void putDouble(final String key, final double value, final JSONObjectDef jsonObj)
    {
        jsonObj.put(key, value);
    }

    public static void putFloat(final String key, final float value, final JSONObjectDef jsonObj)
    {
        jsonObj.put(key, value);
    }

    public static void putString(final String key, final String value, final JSONObjectDef jsonObj)
    {
        jsonObj.put(key, value);
    }
}
