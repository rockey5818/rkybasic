package org.json.parser;

import org.json.me.JSONException;
import org.json.me.JSONObjectDef;

import com.nfkj.basic.util.json.JSONUtil;


public class JSONParser
{
    public static <T> T fromJson(String json, Class<T> clazz) throws JSONException
    {
        try
        {
            JSONObjectDef jsonObj = JSONUtil.createJSONObject(json);
            return parseJsonObject(jsonObj, clazz);
        }
        catch (Exception e)
        {
            throw new JSONException("[json to object]-error, type is " + clazz.getName()
                    + ", json string is " + json + ", exception type is " + e.getClass().getName()
                    + ", exception message is " + e.getMessage());
        }
    }

    public static <T> void updateWithJson(String json, T t) throws JSONException
    {
        try
        {
            JSONObjectDef jsonObj = JSONUtil.createJSONObject(json);
            JSONEntityDesc entity = (JSONEntityDesc)t;
            entity.fromJson(jsonObj);
        }
        catch (Exception e)
        {
            throw new JSONException("[json for update object]-error, object is " + t
                    + ", json string is " + json + ", exception type is " + e.getClass().getName()
                    + ", exception message is " + e.getMessage());
        }
    }

    private static <T> T parseJsonObject(JSONObjectDef jsonObj, Class<T> clazz) throws Exception
    {
        T result = clazz.newInstance();
        JSONEntityDesc entity = (JSONEntityDesc)result;
        entity.fromJson(jsonObj);
        return result;
    }
}
