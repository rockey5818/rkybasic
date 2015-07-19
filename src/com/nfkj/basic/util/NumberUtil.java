package com.nfkj.basic.util;

import java.util.Calendar;

public final class NumberUtil
{
    private NumberUtil()
    {
    }

    public static int getUserAge(long birthDate)
    {
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTimeInMillis(birthDate);
        int birthMonth = birthCalendar.get(Calendar.MONTH);
        int birthDayOfMonth = birthCalendar.get(Calendar.DAY_OF_MONTH);
        int birthYear = birthCalendar.get(Calendar.YEAR);
        Calendar currentCalendar = Calendar.getInstance();
        int currentMonth = currentCalendar.get(Calendar.MONTH);
        int currentDayOfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH);
        int currentYear = currentCalendar.get(Calendar.YEAR);
        if (currentMonth > birthMonth || currentMonth == birthMonth && currentDayOfMonth >= birthDayOfMonth)
        {
            return getNonNegativeValue(currentYear - birthYear);
        }
        else
        {
            return getNonNegativeValue(currentYear - birthYear - 1);
        }
    }

    private static int getNonNegativeValue(int value)
    {
        return value < 0 ? 0 : value;
    }

    public static int getColorIntValueByHexString(String string)
    {
        if (string != null)
        {
            int length = string.length();
            if (length <= 6)
            {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < 6 - length; i++)
                {
                    stringBuilder.append("0");
                }
                stringBuilder.append(string);
            }
            return new Long(Long.parseLong("ff" + string, 16)).intValue();
        }
        return new Long(Long.parseLong("fff08428", 16)).intValue();
    }
}
