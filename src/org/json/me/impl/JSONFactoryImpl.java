package org.json.me.impl;

import org.json.me.JSONArrayDef;
import org.json.me.JSONException;
import org.json.me.JSONFactory;
import org.json.me.JSONObjectDef;

import com.nfkj.basic.util.Strings;

public class JSONFactoryImpl implements JSONFactory
{
    @Override
    public JSONObjectDef createJSONObject(final String content) throws JSONException
    {
        if (Strings.notNullOrEmpty(content))
        {
            return new JSONObject(content);
        }
        else
        {
            return new JSONObject();
        }
    }

    @Override
    public JSONArrayDef createJSONArray(final String content) throws JSONException
    {
    	if(Strings.notNullOrEmpty(content))
    	{
    		return new JSONArray(content);
    	}
    	else 
    	{
    		return new JSONArray();
    	}
    }
}
