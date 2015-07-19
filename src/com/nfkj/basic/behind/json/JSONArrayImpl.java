package com.nfkj.basic.behind.json;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.me.JSONArrayDef;
import org.json.me.JSONException;
import org.json.me.JSONObjectDef;

public class JSONArrayImpl implements JSONArrayDef
{
    private JSONArray jsonArray = new JSONArray();

    public JSONArray getJsonArray()
    {
        return jsonArray;
    }

    public JSONArrayImpl(JSONArray array)
    {
        this.jsonArray = array;
    }

    public JSONArrayImpl()
    {
        jsonArray = new JSONArray();
    }
    
    public JSONArrayImpl(String content) throws JSONException
    {
        try
        {
            jsonArray = new JSONArray(content);
        }
        catch (org.json.JSONException e)
        {
            throw new JSONException(e.getMessage());
        }
    }

    @Override
    public JSONObjectDef getJSONObject(int index) throws JSONException
    {
        try
        {
            return new JSONObjectImpl(jsonArray.getString(index));
        }
        catch (org.json.JSONException e)
        {
            throw new JSONException(e.getMessage());
        }
    }

    @Override
    public int length()
    {
        return jsonArray.length();
    }

    @Override
    public void put(Object value)
    {
        if (value instanceof JSONObjectImpl)
        {
            jsonArray.put(((JSONObjectImpl) value).getJsonObject());
        }
        else if (value instanceof JSONArrayImpl)
        {
            jsonArray.put(((JSONArrayImpl) value).getJsonArray());
        }
        else
        {
            jsonArray.put(value);
        }
    }

    @Override
    public void put(int index, Object value) throws JSONException
    {
        try
        {
            if (value instanceof JSONObjectImpl)
            {
                jsonArray.put(index, ((JSONObjectImpl) value).getJsonObject());
            }
            else if (value instanceof JSONArrayImpl)
            {
                jsonArray.put(index, ((JSONArrayImpl) value).getJsonArray());
            }
            else
            {
                jsonArray.put(index, value);
            }

        }
        catch (org.json.JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Object get(int index) throws JSONException
    {
        try
        {
            Object obj = jsonArray.get(index);
            if (obj instanceof JSONObject)
            {
                return getJSONObject(index);
            }
            else
            {
                return jsonArray.get(index);
            }
        }
        catch (org.json.JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString()
    {
        return jsonArray.toString();
    }
}
