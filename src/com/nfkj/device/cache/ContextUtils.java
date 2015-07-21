package com.nfkj.device.cache;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.os.PowerManager;

import java.util.List;
/**
 * @author Rockey
 *
 */
public class ContextUtils extends Application
{
    private static Context context;

    public static Context getSharedContext()
    {
        return context;
    }

    public static boolean isScreenOn()
    {
        if (context == null)
        {
            return false;
        }

        PowerManager powerManager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        return powerManager.isScreenOn();
    }

    public static boolean isRunningForeground()
    {
        if (context == null)
        {
            return false;
        }

        String packageName = context.getPackageName();
        String topActivityClassName = getTopActivityName(context);
        System.out.println("packageName=" + packageName + ",topActivityClassName=" + topActivityClassName);

        if (packageName != null && topActivityClassName != null && topActivityClassName.startsWith(packageName))
        {
            System.out.println("---> isRunningForeGround");
            return true;
        }

        System.out.println("---> isRunningBackGround");
        return false;
    }

    public static String getTopActivityName(Context context)
    {
        String topActivityClassName = null;
        ActivityManager activityManager = (ActivityManager) (context
                .getSystemService(android.content.Context.ACTIVITY_SERVICE));
        List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
        if (runningTaskInfos != null)
        {
            ComponentName name = runningTaskInfos.get(0).topActivity;
            topActivityClassName = name.getClassName();
        }

        return topActivityClassName;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = getApplicationContext();
        
    }
}
