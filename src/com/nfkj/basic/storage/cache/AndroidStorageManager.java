package com.nfkj.basic.storage.cache;


import java.util.List;
import java.util.Map;

import com.nfkj.basic.storage.cache.dbhelper.StorageDBHelper;
import com.nfkj.basic.util.Logger;
import com.nfkj.basic.util.RkyLog;
import com.nfkj.device.cache.CacheManager;
/**
 * 
 * @author Rockey
 *
 */
public class AndroidStorageManager extends MemStorageManager
{
    private static final String DATABASE_OPERATION = "[DBOP]";

    public static AndroidStorageManager get()
    {
        return Holder.INSTANCE;
    }

    private static final boolean DEBUG = false;

    @Override
    public void executeMultiSQL(List<String> queries)
    {
        checkThread();
        long time1 = System.currentTimeMillis();
        StorageDBHelper.get().executeMultiSQL(queries);
        long time2 = System.currentTimeMillis();

        long elapsedTime = time2 - time1;

        if (elapsedTime > 50 && Thread.currentThread().getId() == 1)
        {
            CacheManager.get().writeHttpLog("[SQL] MultiSQL: " + elapsedTime + "毫秒\n" + String.valueOf(queries));
        }

        if (DEBUG)
        {
            RkyLog.getIns().info(DATABASE_OPERATION + " " + String.valueOf(queries));
        }
    }

    private void checkThread()
    {
        if (DEBUG && Thread.currentThread().getId() == 1)
        {
            throw new IllegalStateException("The main thread calls database");
        }
    }
    
    @Override
    public void executeSQL(String query)
    {
        checkThread();
        long time1 = System.currentTimeMillis();
        StorageDBHelper.get().execSql(query);
        long time2 = System.currentTimeMillis();

        long elapsedTime = time2 - time1;

        if (elapsedTime > 50 && Thread.currentThread().getId() == 1)
        {
            CacheManager.get().writeHttpLog("[SQL] execSQL" + elapsedTime + "毫秒\n" + String.valueOf(query));
        }

        if (DEBUG)
        {
        	RkyLog.getIns().info(DATABASE_OPERATION + " " + query);
        }
    }

    @Override
    public List<String> executeSQLForList(String query)
    {
        checkThread();
        long time1 = System.currentTimeMillis();
        List<String> result = StorageDBHelper.get().execQuery(query);
        long time2 = System.currentTimeMillis();

        long elapsedTime = time2 - time1;

        if (elapsedTime > 50 && Thread.currentThread().getId() == 1)
        {
            CacheManager.get().writeHttpLog("[SQL] executeSQLForList" + elapsedTime + "毫秒\n" + String.valueOf(query));
        }

        if (DEBUG)
        {
        	RkyLog.getIns().info(DATABASE_OPERATION + " " + query);
        }

        return result;
    }

    @Override
    public List<Map<String, String>> executeSQLForMapList(String query)
    {
        checkThread();
        long time1 = System.currentTimeMillis();
        List<Map<String, String>> result = StorageDBHelper.get().execAllQuery(query);
        long time2 = System.currentTimeMillis();

        long elapsedTime = time2 - time1;

        if (elapsedTime > 50 && Thread.currentThread().getId() == 1)
        {
            CacheManager.get().writeHttpLog("[SQL] executeSQLForMapList" + elapsedTime + "毫秒\n" + String.valueOf(query));
        }

        if (DEBUG)
        {
            RkyLog.getIns().info(DATABASE_OPERATION + " " + query);
        }

        return result;
    }

    @Override
    public String executeSQLForString(String query)
    {
        checkThread();
        long time1 = System.currentTimeMillis();
        List<String> result = StorageDBHelper.get().execQuery(query);
        long time2 = System.currentTimeMillis();

        long elapsedTime = time2 - time1;

        if (elapsedTime > 50 && Thread.currentThread().getId() == 1)
        {
            CacheManager.get().writeHttpLog("[SQL] executeSQLForString" + elapsedTime + "毫秒\n" + String.valueOf(query));
        }

        if (DEBUG)
        {
        	RkyLog.getIns().info(DATABASE_OPERATION + " " + query);
        }

        return result.isEmpty() ? null : result.get(0);
    }

    private static class Holder
    {
        public static final AndroidStorageManager INSTANCE = new AndroidStorageManager();
    }

    @Override
    public long executeSqlForLong(final String query)
    {
        final String strResult = executeSQLForString(query);
        try
        {
            return Long.parseLong(strResult);
        }
        catch (final NumberFormatException ex)
        {
            return -1;
        }
    }

    @Override
    public int executeSqlForInt(final String query)
    {
        final String strResult = executeSQLForString(query);
        try
        {
            return Integer.parseInt(strResult);
        }
        catch (final NumberFormatException ex)
        {
            return -1;
        }
    }
}
