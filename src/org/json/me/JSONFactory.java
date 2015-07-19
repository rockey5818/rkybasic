package org.json.me;

public interface JSONFactory
{
    JSONArrayDef createJSONArray(String content) throws JSONException;

    JSONObjectDef createJSONObject(String content) throws JSONException;
}
