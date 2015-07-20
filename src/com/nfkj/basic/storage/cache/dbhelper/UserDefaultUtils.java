package com.nfkj.basic.storage.cache.dbhelper;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONException;

import com.nfkj.device.cache.AvqUtils;
import com.nfkj.device.cache.ContextUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
/**
 * 
 * @author Rockey
 *
 */
public class UserDefaultUtils
{
	//just normal constants for nfkj
    private final static String PreferenceName = "NFKJPREFERENCE";
    private final static String PreferenceKey_Env = "NFKJ_ENV";
    private final static String PreferenceKey_CurrentPhotoUri = "CurrentPhotoUri";
    private final static String PreferenceKey_CurrentPhotoFile = "CurrentPhotoFile";
    private final static String PreferenceKey_CurrentPhotoIndex = "CurrentPhotoIndex";
    private final static String PreferenceKey_TopicEnableNotification = "TopicEnableNotification";
    private final static String PreferenceKey_Check_NewVersion = "CheckNewVersion";
    private final static String PreferenceKey_Debug_Mode = "PreferenceKey_Debug_Mode";
    private final static String PreferenceKey_Network_Type = "PreferenceKey_Network_Type";
    private final static String PreferenceKey_NeedLogout = "PreferenceKey_NeedLogout";
    private final static String PreferenceKey_CURRENTLOCATION = "PreferenceKey_CURRENTLOCATION";
    private final static String PreferenceKey_VersionNameAndDbVersion = "PreferenceKey_VersionNameAndDbVersion";
    private final static String PreferenceKey_Prefix_ProtraitGuideShown = "protrait_guide_shown_";
    private final static String PreferenceKey_DeviceUuid = "PreferenceKey_DeviceUuid";
    private final static String PreferenceKey_Custom = "PreferenceKey_Custom";

    public static void saveString(String key, String value)
    {
        AvqUtils.context.writeToSPref(ContextUtils.getSharedContext(), PreferenceName,
                PreferenceKey_Custom + String.valueOf(key), value);
    }

    public static String loadString(String key)
    {
        String s = AvqUtils.context.readFromSPref(ContextUtils.getSharedContext(), PreferenceName, PreferenceKey_Custom
                + String.valueOf(key));
        return s;
    }
  
    public static void saveDefaultString(String key, String value)
    {
        AvqUtils.context.writeToSPref(ContextUtils.getSharedContext(), PreferenceName, String.valueOf(key), value);
    }

    public static String loadDefaultString(String key)
    {
        String s = null;
        try
        {
            s = AvqUtils.context.readFromSPref(ContextUtils.getSharedContext(), PreferenceName, String.valueOf(key));
        }
        catch (Exception e)
        {
            return null;
        }

        return s;
    }

    public static boolean isFirstTimeUse(Context ctx)
    {
      //  SharedPreferences sp = ctx.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        return false;
    }

    public static void setNotFirstTimeUse(Context ctx)
    {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE).edit();
        // save to share storage
    }

	public static String getVersionRoute() {
		
		return "1.0.0";
	}

   
}
