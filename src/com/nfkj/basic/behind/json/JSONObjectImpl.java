package com.nfkj.basic.behind.json;

import org.json.JSONObject;
import org.json.me.JSONArrayDef;
import org.json.me.JSONException;
import org.json.me.JSONObjectDef;

import java.util.Iterator;

public class JSONObjectImpl implements JSONObjectDef
{
    private JSONObject jsonObject;

    public JSONObject getJsonObject()
    {
        return jsonObject;
    }

    public JSONObjectImpl(String content) throws JSONException
    {
        try
        {
            jsonObject = new JSONObject(content);
        }
        catch (org.json.JSONException e)
        {
            throw new JSONException(e.getMessage());
        }
    }

    public JSONObjectImpl(JSONObject jsonObject)
    {
        this.jsonObject = jsonObject;
    }

    public JSONObjectImpl()
    {
        jsonObject = new JSONObject();
    }

    @Override
    public JSONArrayDef getJSONArray(String key) throws JSONException
    {
        try
        {
            if (!has(key))
            {
                return null;
            }
            return new JSONArrayImpl(jsonObject.getJSONArray(key));
        }
        catch (org.json.JSONException e)
        {
            throw new JSONException(e.getMessage());
        }
    }

    @Override
    public JSONObjectDef getJSONObject(String key) throws JSONException
    {
        try
        {
            if (!has(key))
            {
                return null;
            }
            return new JSONObjectImpl(jsonObject.getJSONObject(key));
        }
        catch (org.json.JSONException e)
        {
            throw new JSONException(e.getMessage());
        }
    }

    @Override
    public String getString(String key) throws JSONException
    {
        try
        {
            return has(key) ? jsonObject.getString(key) : "";
        }
        catch (org.json.JSONException e)
        {
            throw new JSONException(e.getMessage());
        }
    }

    @Override
    public boolean has(String key)
    {
        return jsonObject != null && jsonObject.has(key);
    }

    @Override
    public void put(String key, Object value)
    {
        try
        {
            if (value instanceof JSONObjectImpl)
            {
                jsonObject.put(key, ((JSONObjectImpl) value).getJsonObject());
            }
            else if (value instanceof JSONArrayImpl)
            {
                jsonObject.put(key, ((JSONArrayImpl) value).getJsonArray());
            }
            else
            {
                jsonObject.put(key, value);
            }
        }
        catch (org.json.JSONException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void remove(String key)
    {
        jsonObject.remove(key);
    }

    @Override
    public String toString()
    {
        return jsonObject.toString();
    }

	@Override
	public Iterator<String> keys()
	{
		return jsonObject.keys();
	}
}