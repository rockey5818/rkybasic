package org.json.me;

public interface JSONArrayDef
{
    JSONObjectDef getJSONObject(int index) throws JSONException;

    int length();

    void put(Object value);

    void put(int index, Object value) throws JSONException;

    Object get(int index) throws JSONException;

    String toString();
}