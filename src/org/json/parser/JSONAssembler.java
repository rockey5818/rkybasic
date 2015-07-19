package org.json.parser;

import org.json.me.JSONException;

public class JSONAssembler
{
    public static <T> String toJson(final T t) throws JSONException
    {
        try
        {
            JSONEntityDesc entity = (JSONEntityDesc)t;
            return entity.toJson().toString();
        }
        catch (Exception e)
        {
            throw new JSONException("[object to json]-error, object is " + t + ", exception type is " + e.getClass().getName() + ", exception message is " + e.getMessage());
        }
    }

    public static <T> String toJson(final T t, final Class<?> clazz) throws JSONException
    {
        try
        {
            JSONEntityDesc entity = (JSONEntityDesc)t;
            return entity.toJson().toString();
        }
        catch (Exception e)
        {
            throw new JSONException("[part of object to json]-error, object is " + t + ", class is " + clazz.getName() + ", exception type is " + e.getClass().getName()
                    + ", exception message is " + e.getMessage());
        }
    }
}
