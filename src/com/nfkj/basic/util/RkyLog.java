package com.nfkj.basic.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nfkj.basic.logging.ProtocolLogger;
import com.nfkj.device.cache.CacheManager;

import android.util.Log;

/**
 * 
 * @author Rockey
 *
 */
public class RkyLog implements ProtocolLogger
{
    final private static boolean m_bPrintLog = false;
    final private static boolean m_bPrintGdaze = false;
    private static RkyLog m_ins = null;
    private Map<String, Boolean> mSqlError = new HashMap<String, Boolean>();

    public static RkyLog getIns()
    {
        if (m_ins == null)
        {
            synchronized (RkyLog.class)
            {
                if (m_ins == null)
                {
                    m_ins = new RkyLog();
                }
            }
        }

        return m_ins;
    }
    
    public boolean needPrint()
    {
    	return  m_bPrintLog;
    }

    public void info(String msg)
    {
        if (msg != null && needPrint())
        {
            v("", msg);

            String socketTag = "[socket]";
            String socketConenctTag = "[connect socket]";
            String httpTag = "[http";
            String dazeTag = "<GDAZE";

            int index = msg.indexOf(socketTag);

            if (index != -1)
            {
            	index = msg.indexOf(dazeTag);
            	
            	if (index != -1 && !m_bPrintGdaze)
            	{
            		return;
            	}
            	
                CacheManager.get().writeCustomLog(msg, "javabehindSocket");

                return;
            }

            index = msg.indexOf(socketConenctTag);

            if (index != -1)
            {
                CacheManager.get().writeCustomLog(msg, "javabehindSocket");
                return;
            }

            index = msg.indexOf(httpTag);

            if (index != -1)
            {
                CacheManager.get().writeCustomLog(msg, "javabehindHttp");

                /**
                 * @caution
                 * 
                 *          should add return as if http request contains
                 *          keywords sqlError will cause below storeError Report
                 *          and dead circle
                 * 
                 * @author rockey
                 * 
                 */
                return;
            }

            String sqlError = "^execSql Error(.)*";
            Pattern pattern = Pattern.compile(sqlError);
            Matcher matcher = pattern.matcher(msg);

            /**
             * @caution
             * 
             *          1. store sql may cause sqlException then dead cirle. Add
             *          5 seconds mechanism to solve this problem. 2. upload sql
             *          error http log may cause store error log again. Add http
             *          log return to solve this problem.
             * 
             * @author rockey
             */

            if (matcher.matches())
            {
                if (mSqlError.get(msg) == null)
                {
                    if (mSqlError.size() > 100)
                    {
                        Map<String, Boolean> temp = new HashMap<String, Boolean>();
                        mSqlError = temp;
                    }

                    mSqlError.put(String.valueOf(msg), true);
            //        UploaderManager.get().storeJavaBehindErrorLog(msg);
                }
            }
        }
    }

    @Override
    public void receive(String s)
    {
        if (s != null)
        {
            v("", s);
        }
    }

    @Override
    public void send(String s)
    {
        if (s != null)
        {
            v("", s);
        }
    }

    static public void v(String tag, String format, Object... args)
    {
        if (m_bPrintLog)
        {
            if (tag == null)
            {
                tag = "";
            }
            String prefix = "";
            String string = format;
            try
            {
                string = prefix + String.format(format, args);
            }
            catch (Exception e)
            {
            }

            if (string == null)
            {
                string = "";
            }
            Log.v(tag, string);
        }
    }

    static public void i(String tag, String format, Object... args)
    {
        if (m_bPrintLog)
        {
            if (tag == null)
            {
                tag = "";
            }
            String prefix = "";
            String string = format;
            try
            {
                string = prefix + String.format(format, args);
            }
            catch (Exception e)
            {
            }

            if (string == null)
            {
                string = "";
            }
            Log.i(tag, string);
        }
    }

    static public void e(String tag, String format, Object... args)
    {
        if (m_bPrintLog)
        {
            if (tag == null)
            {
                tag = "";
            }
            String prefix = "";
            String string = format;
            try
            {
                string = prefix + String.format(format, args);
            }
            catch (Exception e)
            {
            }

            if (string == null)
            {
                string = "";
            }
            Log.e(tag, string);
        }
    }

    static public void d(String tag, String format, Object... args)
    {
        if (m_bPrintLog)
        {
            if (tag == null)
            {
                tag = "";
            }
            String prefix = "";
            String string = format;
            try
            {
                string = prefix + String.format(format, args);
            }
            catch (Exception e)
            {
            }

            if (string == null)
            {
                string = "";
            }
            Log.d(tag, string);

        }
    }

    static public void w(String tag, String format, Object... args)
    {
        if (m_bPrintLog)
        {
            if (tag == null)
            {
                tag = "";
            }
            String prefix = "";
            String string = format;
            try
            {
                string = prefix + String.format(format, args);
            }
            catch (Exception e)
            {
            }

            if (string == null)
            {
                string = "";
            }
            Log.w(tag, string);

        }
    }

    @Override
    public void logThrowable(Throwable throwable)
    {
        if (throwable == null)
        {
            return;
        }
//        UploaderManager.get().storeJavaBehindErrorLog(
//                "logThrowable:\nMessage:" + String.valueOf(throwable.getMessage()) + "\n\nException:"
//                        + AvqUtils.string.getThrowableDescription(throwable));
    }

}
