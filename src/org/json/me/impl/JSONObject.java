package org.json.me.impl;

import org.json.me.JSONArrayDef;
import org.json.me.JSONException;
import org.json.me.JSONObjectDef;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class JSONObject implements JSONObjectDef
{

    private final Hashtable<String, Object> myHashMap;

    public static String quote(final String string)
    {
        if (string == null || string.isEmpty())
        {
            return "\"\"";
        }

        char b;
        char c = 0;
        int i;
        final int len = string.length();
        final StringBuffer sb = new StringBuffer(len + 4);
        String t;

        sb.append('"');
        for (i = 0; i < len; i += 1)
        {
            b = c;
            c = string.charAt(i);
            switch (c)
            {
                case '\\':
                case '"':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '/':
                    if (b == '<')
                    {
                        sb.append('\\');
                    }
                    sb.append(c);
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                default:
                    if (c < ' ')
                    {
                        t = "000" + Integer.toHexString(c);
                        sb.append("\\u").append(t.substring(t.length() - 4));
                    }
                    else
                    {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }

    static String valueToString(final Object value) throws JSONException
    {
        if (value == null || value.equals(null))
        {
            return "null";
        }
        if (value instanceof JSONObject || value instanceof JSONArray)
        {
            return value.toString();
        }
        return quote(value.toString());
    }

    public JSONObject()
    {
        myHashMap = new Hashtable<>();
    }

    public JSONObject(final JSONTokener x) throws JSONException
    {
        this();
        char c;
        String key;

        if (x.nextClean() != '{')
        {
            throw x.syntaxError("A JSONObject text must begin with '{'");
        }
        for (;;)
        {
            c = x.nextClean();
            switch (c)
            {
                case 0:
                    throw x.syntaxError("A JSONObject text must end with '}'");
                case '}':
                    return;
                default:
                    x.back();
                    key = x.nextValue().toString();
            }

            c = x.nextClean();
            if (c == '=')
            {
                if (x.next() != '>')
                {
                    x.back();
                }
            }
            else if (c != ':')
            {
                throw x.syntaxError("Expected a ':' after a key");
            }
            put(key, x.nextValue());

            switch (x.nextClean())
            {
                case ';':
                case ',':
                    if (x.nextClean() == '}')
                    {
                        return;
                    }
                    x.back();
                    break;
                case '}':
                    return;
                default:
                    throw x.syntaxError("Expected a ',' or '}'");
            }
        }
    }

    public JSONObject(final String string) throws JSONException
    {
        this(new JSONTokener(string));
    }

    public Object get(final String key) throws JSONException
    {
        final Object o = opt(key);
        if (o == null)
        {
            throw new JSONException("JSONObject[" + quote(key) + "] not found.");
        }
        return o;
    }

    /*
     * (non-Javadoc)
     * 
     * @see JSONObjectDef#getJSONArray(java.lang.String)
     */
    @Override
    public JSONArrayDef getJSONArray(final String key) throws JSONException
    {
        final Object o = get(key);
        if (o instanceof JSONArray)
        {
            return (JSONArrayDef) o;
        }
        throw new JSONException("JSONObject[" + quote(key) + "] is not a JSONArray.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see JSONObjectDef#getJSONObject(java.lang.String)
     */
    @Override
    public JSONObjectDef getJSONObject(final String key) throws JSONException
    {
        final Object o = get(key);
        if (o instanceof JSONObject)
        {
            return (JSONObjectDef) o;
        }
        throw new JSONException("JSONObject[" + quote(key) + "] is not a JSONObject.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see JSONObjectDef#getString(java.lang.String)
     */
    @Override
    public String getString(final String key) throws JSONException
    {
        return get(key).toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see JSONObjectDef#has(java.lang.String)
     */
    @Override
    public boolean has(final String key)
    {
        return myHashMap.containsKey(key);
    }

    public Object opt(final String key)
    {
        return key == null ? null : myHashMap.get(key);
    }

    public JSONObjectDef put(final String key, final int value) throws JSONException
    {
        put(key, new Integer(value));
        return this;
    }

    public JSONObjectDef put(final String key, final long value) throws JSONException
    {
        put(key, new Long(value));
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see JSONObjectDef#put(java.lang.String, java.lang.Object)
     */
    @Override
    public void put(final String key, final Object value)
    {
        if (key == null)
        {
            throw new NullPointerException("Null key.");
        }
        if (value != null)
        {
            myHashMap.put(key, value);
        }
        else
        {
            remove(key);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see JSONObjectDef#remove(java.lang.String)
     */
    @Override
    public void remove(final String key)
    {
        myHashMap.remove(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see JSONObjectDef#toString()
     */
    @Override
    public String toString()
    {
        try
        {
            final Enumeration<String> keys = myHashMap.keys();
            final StringBuffer sb = new StringBuffer("{");

            while (keys.hasMoreElements())
            {
                if (sb.length() > 1)
                {
                    sb.append(',');
                }
                final Object o = keys.nextElement();
                sb.append(quote(o.toString()));
                sb.append(':');
                sb.append(valueToString(myHashMap.get(o)));
            }
            sb.append('}');
            return sb.toString();
        }
        catch (final Exception e)
        {
            return null;
        }
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((myHashMap == null) ? 0 : myHashMap.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final JSONObject other = (JSONObject) obj;
        if (myHashMap == null)
        {
            if (other.myHashMap != null)
                return false;
        }
        else if (!myHashMap.equals(other.myHashMap))
            return false;
        return true;
    }

	@Override
	public Iterator<String> keys()
	{
		return myHashMap.keySet().iterator();
	}

}