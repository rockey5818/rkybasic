package com.nfkj.basic.parse.map;

import java.util.Map;

import com.nfkj.basic.util.Logger;
import com.nfkj.basic.util.Strings;

import static com.nfkj.basic.util.Preconditions.checkArgument;

/**
 * @author Rockey
 */
public final class MapParser
{
    /**
     * Don't let anyone instantiate this class.
     */
    private MapParser()
    {
        // This constructor is intentionally empty.
    }

    /**
     * Get value of a specified key from provided map as a boolean.
     *
     * @param map The provided data map.
     * @param key The specified key.
     * @return Value of the specified key if exist. Otherwise default value returned.
     */
    public static boolean getBoolean(final Map<String, String> map, final String key)
    {
        checkArgument(null != map);
        checkArgument(Strings.notNullOrEmpty(key));

        boolean result = false;
        if (null != map && Strings.notNullOrEmpty(key) && map.containsKey(key))
        {
            final String value = map.get(key);
            result = Strings.parseBoolean(value);
        }
        return result;
    }

    /**
     * Get value of a specified key from provided map as a integer.
     *
     * @param map          The provided data map.
     * @param key          The specified key.
     * @param defaultValue The default return value if the specified key not exist.
     * @return Value of the specified key if exist. Otherwise default value returned.
     */
    public static int getInteger(final Map<String, String> map, final String key, final int defaultValue)
    {
        checkArgument(null != map);
        checkArgument(Strings.notNullOrEmpty(key));

        int result = defaultValue;
        if (null != map && Strings.notNullOrEmpty(key) && map.containsKey(key))
        {
            try
            {
                result = Integer.parseInt(map.get(key));
            }
            catch (NumberFormatException ex)
            {
                Logger.logThrowable(ex);
            }
        }
        return result;
    }

    /**
     * Get value of a specified key from provided map as a long.
     *
     * @param map          The provided data map.
     * @param key          The specified key.
     * @param defaultValue The default return value if the specified key not exist.
     * @return Value of the specified key if exist. Otherwise default value returned.
     */
    public static long getLong(final Map<String, String> map, final String key, final long defaultValue)
    {
        checkArgument(null != map);
        checkArgument(Strings.notNullOrEmpty(key));

        long result = defaultValue;
        if (null != map && Strings.notNullOrEmpty(key) && map.containsKey(key))
        {
            try
            {
                result = Long.parseLong(map.get(key));
            }
            catch (NumberFormatException ex)
            {
            	Logger.logThrowable(ex);
            }
        }
        return result;
    }

    public static double getDouble(final Map<String, String> map, final String key, final double defaultValue)
    {
        checkArgument(null != map);
        checkArgument(Strings.notNullOrEmpty(key));

        double result = defaultValue;
        if (null != map && Strings.notNullOrEmpty(key) && map.containsKey(key))
        {
            try
            {
                result = Double.parseDouble(map.get(key));
            }
            catch (NumberFormatException ex)
            {
            	Logger.logThrowable(ex);
            }
        }
        return result;
    }

    /**
     * Get value of a specified key from provided map as a integer.
     *
     * @param map The provided data map.
     * @param key The specified key.
     * @return Value of the specified key if exist. Otherwise a empty string returned.
     */
    public static String getString(final Map<String, String> map, final String key)
    {
        checkArgument(null != map);
        checkArgument(Strings.notNullOrEmpty(key));

        String result = "";
        if (null != map && Strings.notNullOrEmpty(key) && map.containsKey(key))
        {
            result = map.get(key);
        }
        return result;
    }

    /**
     * Get value of a specified key from provided map as a byte.
     *
     * @param map The provided data map.
     * @param key The specified key.
     * @return Value of the specified key if exist. Otherwise default value returned.
     */
    public static byte getByte(final Map<String, String> map, final String key, final byte defaultValue)
    {
        checkArgument(null != map);
        checkArgument(Strings.notNullOrEmpty(key));

        byte result = defaultValue;
        if (null != map && Strings.notNullOrEmpty(key) && map.containsKey(key))
        {
            try
            {
                result = Byte.parseByte(map.get(key));
            }
            catch (NumberFormatException ex)
            {
            	Logger.logThrowable(ex);
            }
        }
        return result;
    }
}
