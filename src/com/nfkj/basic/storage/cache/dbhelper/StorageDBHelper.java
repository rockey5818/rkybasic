package com.nfkj.basic.storage.cache.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nfkj.basic.util.Logger;
import com.nfkj.device.cache.ContextUtils;
/**
 * 
 * @author Rockey
 *
 */
public class StorageDBHelper extends SQLiteOpenHelper
{
    final public static String TABLENAME_DEFUALT = "DEFAULTSTORAGETABLE";
    final private String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLENAME_DEFUALT + " ("
            + "ID INTEGER primary key AUTOINCREMENT," + "KEY TEXT," + "VALUE TEXT);";
    final private static String DB_NAME = "nfkjstorage.db";
    private SQLiteDatabase myDataBase;

    public static StorageDBHelper get()
    {
        return Holder.INSTANCE;
    }

    private StorageDBHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
        try
        {
            myDataBase = getWritableDatabase();
        }
        catch (Exception e)
        {
            // 磁盘空间满
            try
            {
                myDataBase = getReadableDatabase();
            }
            catch (Exception readException)
            {
                // 磁盘空间满 不能创建
                Logger.logThrowable(readException);
            }
        }
    }

    @Override
    public synchronized void close()
    {
        if (myDataBase != null)
        {
            myDataBase.close();
        }
    }

    public List<Map<String, String>> execAllQuery(String sql)
    {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        if (myDataBase == null)
        {
            return result;
        }

        try
        {
            SQLiteCursor cursor = (SQLiteCursor) myDataBase.rawQuery(sql, null);

            String[] columnNames = cursor.getColumnNames();

            while (cursor.moveToNext())
            {
                Map<String, String> map = new HashMap<String, String>();

                for (String columnName : columnNames)
                {
                    int index = cursor.getColumnIndex(columnName);
                    map.put(columnName, cursor.getString(index));
                }

                result.add(map);
            }
            cursor.close();
        }
        catch (Exception e)
        {
            Logger.error("execSql:" + sql + "\nError:" + e.getMessage());
        }

        return result;
    }

    public List<String> execQuery(String sql)
    {
        List<String> result = new ArrayList<String>();
        if (myDataBase == null)
        {
            return result;
        }

        try
        {
            SQLiteCursor cursor = (SQLiteCursor) myDataBase.rawQuery(sql, null);

            String[] columnNames = cursor.getColumnNames();

            while (cursor.moveToNext())
            {
                if (columnNames.length > 0)
                {
                    int index = cursor.getColumnIndex(columnNames[0]);
                    result.add(cursor.getString(index));
                }
            }

            cursor.close();
        }
        catch (Exception e)
        {
            Logger.error("execSql:" + sql + "\nError:" + e.getMessage());
        }

        return result;
    }

    public synchronized void execSql(String sql)
    {
        if (myDataBase == null)
        {
            return;
        }
        try
        {
            myDataBase.execSQL(sql);
        }
        catch (Exception e)
        {
            Logger.error("execSql:" + sql + "\nError:" + e.getMessage());
        }

    }

    public void executeMultiSQL(List<String> sqls)
    {
        if (myDataBase == null || sqls == null)
        {
            return;
        }
        myDataBase.beginTransaction();
        try
        {
            for (String sql : sqls)
            {
                myDataBase.execSQL(sql);
            }
            myDataBase.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            Logger.error("execSql:" + sqls + "\nError:" + e.getMessage());
        }
        finally
        {
            myDataBase.endTransaction();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     * 
     * @return true if it exists, false if it doesn't
     */
    // private boolean checkDataBase(String DB_PATH)
    // {
    // SQLiteDatabase checkDB = null;
    // try
    // {
    // String myPath = DB_PATH + DB_NAME;
    // checkDB = SQLiteDatabase.openDatabase(myPath, null,
    // SQLiteDatabase.OPEN_READWRITE);
    // }
    // catch (SQLiteException e)
    // {
    // // database does't exist yet.
    // }
    // catch (Exception e)
    // {
    // }
    //
    // if (checkDB != null)
    // {
    // checkDB.close();
    // }
    //
    // return checkDB != null;
    // }

    private static class Holder
    {
        public static final StorageDBHelper INSTANCE = new StorageDBHelper(ContextUtils.getSharedContext());
    }
}
