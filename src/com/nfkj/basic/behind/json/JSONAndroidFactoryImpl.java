package com.nfkj.basic.behind.json;

import org.json.me.JSONArrayDef;
import org.json.me.JSONException;
import org.json.me.JSONFactory;
import org.json.me.JSONObjectDef;

import com.nfkj.basic.util.Strings;

public class JSONAndroidFactoryImpl implements JSONFactory
{

    @Override
    public JSONObjectDef createJSONObject(String content) throws JSONException
    {
        if (Strings.notNullOrEmpty(content))
        {
            return new JSONObjectImpl(content);
        }
        else
        {
            return new JSONObjectImpl();
        }
    }

    @Override
    public JSONArrayDef createJSONArray(String content) throws JSONException
    {
    	if (Strings.notNullOrEmpty(content))
        {
            return new JSONArrayImpl(content);
        }
        else
        {
            return new JSONArrayImpl();
        }
    }

}
