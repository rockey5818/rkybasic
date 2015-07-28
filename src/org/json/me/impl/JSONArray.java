package org.json.me.impl;

import org.json.me.JSONArrayDef;
import org.json.me.JSONException;
import org.json.me.JSONObjectDef;

import java.util.Vector;

public class JSONArray implements JSONArrayDef
{
    private final Vector<Object> myArrayList;

    public JSONArray()
    {
        myArrayList = new Vector<Object>();
    }

    public JSONArray(final JSONTokener x) throws JSONException
    {
        this();
        if (x.nextClean() != '[')

        {
            throw x.syntaxError("A JSONArray text must start with '['");
        }
        if (x.nextClean() == ']')
        {
            return;
        }
        x.back();
        for (;;)
        {
            if (x.nextClean() == ',')
            {
                x.back();
                myArrayList.addElement(null);
            }
            else
            {
                x.back();
                myArrayList.addElement(x.nextValue());
            }
            switch (x.nextClean())
            {
                case ';':
                case ',':
                    if (x.nextClean() == ']')
                    {
                        return;
                    }
                    x.back();
                    break;
                case ']':
                    return;
                default:
                    throw x.syntaxError("Expected a ',' or ']'");
            }
        }
    }

    public JSONArray(final Vector<Object> collection)
    {
        if (collection == null)
        {
            myArrayList = new Vector<Object>();
        }
        else
        {
            final int size = collection.size();
            myArrayList = new Vector<Object>(size);
            for (int i = 0; i < size; i++)
            {
                myArrayList.addElement(collection.elementAt(i));
            }
        }
    }

    public JSONArray(final String string) throws JSONException
    {
        this(new JSONTokener(string));
    }
    /*
     * (non-Javadoc)
     * 
     * @see JSONArrayDef#get(int)
     */
    @Override
    public Object get(final int index) throws JSONException
    {
        final Object o = opt(index);
        if (o == null)
        {
            throw new JSONException("JSONArray[" + index + "] not found.");
        }
        return o;
    }

    /*
     * (non-Javadoc)
     * 
     * @see JSONArrayDef#getJSONObject(int)
     */
    @Override
    public JSONObjectDef getJSONObject(final int index) throws JSONException
    {
        final Object o = get(index);
        if (o instanceof JSONObject)
        {
            return (JSONObjectDef) o;
        }
        throw new JSONException("JSONArray[" + index + "] is not a JSONObject.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see JSONArrayDef#length()
     */
    @Override
    public int length()
    {
        return myArrayList.size();
    }

    public Object opt(final int index)
    {
        return index < 0 || index >= length() ? null : myArrayList.elementAt(index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see JSONArrayDef#put(java.lang.Object)
     */
    @Override
    public void put(final Object value)
    {
        myArrayList.addElement(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see JSONArrayDef#put(int, java.lang.Object)
     */
    @Override
    public void put(final int index, final Object value) throws JSONException
    {
        if (index < 0)
        {
            throw new JSONException("JSONArray[" + index + "] not found.");
        }
        if (index < length())
        {
            myArrayList.setElementAt(value, index);
        }
        else
        {
            while (index != length())
            {
                put(JSONObjectDef.NULL);
            }
            put(value);
        }
    }

    public String join(final String separator) throws JSONException
    {
        final int len = length();
        final StringBuffer sb = new StringBuffer();

        for (int i = 0; i < len; i += 1)
        {
            if (i > 0)
            {
                sb.append(separator);
            }
            sb.append(JSONObject.valueToString(myArrayList.elementAt(i)));
        }
        return sb.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see JSONArrayDef#toString()
     */
    @Override
    public String toString()
    {
        try
        {
            return '[' + join(",") + ']';
        }
        catch (final Exception e)
        {
            return null;
        }
    }

}
