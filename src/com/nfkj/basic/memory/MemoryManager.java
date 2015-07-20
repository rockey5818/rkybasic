package com.nfkj.basic.memory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.nfkj.basic.util.Logger;
import com.nfkj.basic.util.RkyLog;
import com.nfkj.device.cache.ContextUtils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
/**
 * 
 * @author Rockey
 *	@description manager mem ,includ storage or memory storage.
 */
public class MemoryManager
{
    private static MemoryManager instance;
    private long lastTime = 0;

    public static MemoryManager get()
    {
        if (instance == null)
        {
            instance = new MemoryManager();
        }
        return instance;
    }

    public void traceInfo()
    {
        long c = System.currentTimeMillis();
        if (c - lastTime > 60 * 1000)
        {
            int availMem = getFreeMemoryInfo();
            int maxMemory = (int) Runtime.getRuntime().maxMemory() / 1024;
            int totalMemory = (int) Runtime.getRuntime().totalMemory() / 1024;
            int freeMemory = (int) Runtime.getRuntime().freeMemory() / 1024;
            String info = "[http memory> max :" + maxMemory + " BK, total: " + totalMemory + " BK,free: " + freeMemory
                    + " BK, avail : " + availMem + " M, page : "
                    + ContextUtils.getTopActivityName(ContextUtils.getSharedContext());
            info += " ,Memory: " + getAvailableInternalMemorySize() + " M ,TotalMemory: "
                    + getTotalInternalMemorySize() + " M";
            
            info += " ,SD all: " + getSDTotalSize() + "M, SD Availale: " + getSDAvailaleSize() + "M";
            Logger.info(info);
            lastTime = c;
        }
    }

    public static long getTotalInternalMemorySize()
    {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize / 1024 / 1024;
    }

    public static long getAvailableInternalMemorySize()
    {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize / 1024 / 1024;
    }

    /**
     * 获取手机内存大小
     * 
     * @return
     */
    private long getTotalMemory()
    {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try
        {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString)
            {
                // Log.i(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        }
        catch (IOException e)
        {
        }
        return initial_memory;// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 获取手机CPU信息
     * 
     * @return
     */
    public String getCpuInfo()
    {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = { "", "" };
        String[] arrayOfString;
        try
        {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++)
            {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        }
        catch (IOException e)
        {
        }
        String info = cpuInfo[0] + "," + cpuInfo[1];
        return info;
    }

    public static int getFreeMemoryInfo()
    {
        ActivityManager am = (ActivityManager) ContextUtils.getSharedContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(outInfo);
        long a = outInfo.availMem / 1024 / 1024;

        return (int) a;
    }

    public static long getSDAvailaleSize()
    {

        File path = Environment.getExternalStorageDirectory(); // 取得sdcard文件路径

        StatFs stat = new StatFs(path.getPath());

        long blockSize = stat.getBlockSize();

        long availableBlocks = stat.getAvailableBlocks();

        return availableBlocks * blockSize /1024 /1024;

        // (availableBlocks * blockSize)/1024 KIB 单位

        // (availableBlocks * blockSize)/1024 /1024 MIB单位

    }

    public static long getSDTotalSize()
    {

        File path = Environment.getExternalStorageDirectory();

        StatFs stat = new StatFs(path.getPath());

        long blockSize = stat.getBlockSize();

        long availableBlocks = stat.getBlockCount();

        return availableBlocks * blockSize /1024 /1024;

    }
}
