package com.nfkj.basic.util.json;



import com.nfkj.basic.defer.CoreDeferObject;
import com.nfkj.basic.util.Strings;


import java.util.Map;

import org.json.me.JSONArrayDef;
import org.json.me.JSONException;
import org.json.me.JSONObjectDef;

public final class JSONUtil
{
    /**
     * Don't let anyone instantiate this class.
     */
    private JSONUtil()
    {
        // This constructor is intentionally empty.
    }

    public static boolean hasKey(final String key, final JSONObjectDef json)
    {
        return json.has(key);
    }

    public static String getStringFromJsonThenRemove(final String key, final JSONObjectDef json) throws JSONException
    {
        final String value = json.getString(key);
        json.remove(key);
        return value;
    }

    public static JSONArrayDef getArrayFromJsonThenRemove(final String key, final JSONObjectDef json)
            throws JSONException
    {
        final JSONArrayDef value = json.getJSONArray(key);
        json.remove(key);
        return value;
    }

    public static JSONObjectDef getObjectFromJSONArrayThenRemove(final int index, final JSONArrayDef jsonArray)
            throws JSONException
    {
        final JSONObjectDef value = jsonArray.getJSONObject(index);
        jsonArray.put(index, null);
        return value;
    }

    public static JSONObjectDef getObjectFromArray(final int index, final JSONArrayDef jsonArray) throws JSONException
    {
        return jsonArray.getJSONObject(index);
    }

    public static JSONObjectDef getObjectFromJson(final String key, final JSONObjectDef json) throws JSONException
    {
        return json.getJSONObject(key);
    }

    public static String getStringFromJson(final String key, final JSONObjectDef json) throws JSONException
    {
        return json.getString(key);
    }

    public static JSONArrayDef getArrayFromJson(final String key, final JSONObjectDef json) throws JSONException
    {
        return json.getJSONArray(key);
    }

    public static JSONObjectDef createJSONObject()
    {
        try
        {
            return createJSONObject("");
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static JSONObjectDef createJSONObject(final String content) throws JSONException
    {
        return CoreDeferObject.get().getJsonFactory().createJSONObject(content);
    }

    public static JSONArrayDef createJSONArray()
    {
        try
        {
            return CoreDeferObject.get().getJsonFactory().createJSONArray("");
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static JSONArrayDef createJSONArray(String content) throws JSONException
    {
        return CoreDeferObject.get().getJsonFactory().createJSONArray(content);
    }

    public static JSONObjectDef createNecessaryJSONObject(final String content)
    {
        JSONObjectDef jsonObj;
        if (Strings.notNullOrEmpty(content))
        {
            try
            {
                jsonObj = createJSONObject(content);
            }
            catch (Exception e)
            {
                jsonObj = createJSONObject();
            }
        }
        else
        {
            jsonObj = createJSONObject();
        }
        return jsonObj;
    }

    /**
     * Create a {@link JSONObjectDef} from a {@link Map}.
     *
     * @param map The {@link Map} which contains data to build a {@link JSONObjectDef}.
     * @return The {@link JSONObjectDef} object which built from the provided {@link Map}.
     */
    public static JSONObjectDef createJsonObject(final Map<String, String> map)
    {
        final JSONObjectDef jsonObject = createJSONObject();
        for (final Map.Entry<String, String> entry : map.entrySet())
        {
            jsonObject.put(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }
}
