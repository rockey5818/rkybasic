package org.json.me;

import java.util.*;

public interface JSONObjectDef
{
    Object NULL = new Null();

    final class Null
    {
        @Override
        public boolean equals(Object object)
        {
            return object == null || object == this;
        }

        @Override
        public String toString()
        {
            return "null";
        }

        @Override
        protected Object clone()
        {
            return this;
        }
    }

    JSONArrayDef getJSONArray(String key) throws JSONException;

    JSONObjectDef getJSONObject(String key) throws JSONException;

    String getString(String key) throws JSONException;

    boolean has(String key);

    void put(String key, Object value);

    void remove(String key);
    
    Iterator<String> keys();

    String toString();
}