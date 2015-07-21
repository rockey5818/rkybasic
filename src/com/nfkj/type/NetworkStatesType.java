package com.nfkj.type;

import com.nfkj.basic.util.Logger;
import com.nfkj.basic.util.Strings;


public enum NetworkStatesType
{
    DATA_CONNECTED(0), WIFI_CONNECTED(1), DIS_CONNECTED(2);

    private final int value;

    NetworkStatesType(int value)
    {
        this.value = value;
    }

    public static NetworkStatesType from(String value)
    {
        if (Strings.notNullOrEmpty(value))
        {
            try
            {
                int typeValue = Integer.parseInt(value);
                for (NetworkStatesType type : values())
                {
                    if (type.getValue() == typeValue)
                    {
                        return type;
                    }
                }
            }
            catch (Exception e)
            {
                Logger.logThrowable(e);
            }
        }
        return null;
    }

    public int getValue()
    {
        return value;
    }
}
