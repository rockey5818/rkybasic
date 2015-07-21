package com.nfkj.basic.util.network;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

import com.nfkj.device.cache.AvqUtils;
import com.nfkj.device.cache.ContextUtils;
import com.nfkj.type.NetworkStatesType;

public class NetworkUtils
{

    private static List<WeakReference<NetworkUtilsCallback>> m_List = new ArrayList<WeakReference<NetworkUtilsCallback>>();
    private static NetworkUtils sharedInstance = null;

    public static synchronized NetworkUtils get()
    {
        if (sharedInstance == null)
        {
            sharedInstance = new NetworkUtils();
        }
        return sharedInstance;
    }

    public interface NetworkUtilsCallback
    {
        public void onNetworkChange(NetworkStatesType type);
    }

    /*
     * Public Method
     */
    public void addObserver(NetworkUtilsCallback callback)
    {
        List<WeakReference<NetworkUtilsCallback>> tempList = new ArrayList<WeakReference<NetworkUtilsCallback>>();

        for (WeakReference<NetworkUtilsCallback> weakCallback : m_List)
        {
            if (AvqUtils.Weak.isValidWeak(weakCallback))
            {
                if (weakCallback.get() == callback)
                {
                    return;
                }
                else
                {
                    tempList.add(weakCallback);
                }
            }

        }

        tempList.add(new WeakReference<NetworkUtilsCallback>(callback));
        m_List = tempList;

    }

    public void removeOberver(NetworkUtilsCallback callback)
    {
        List<WeakReference<NetworkUtilsCallback>> tempList = new ArrayList<WeakReference<NetworkUtilsCallback>>();
        for (WeakReference<NetworkUtilsCallback> weakCallback : m_List)
        {
            if (AvqUtils.Weak.isValidWeak(weakCallback))
            {
                if (weakCallback.get() == callback)
                {
                    continue;
                }
                else
                {
                    tempList.add(weakCallback);
                }
            }

        }

        m_List = tempList;
    }

    Handler handler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {

            super.handleMessage(msg);
            NetworkStatesType type = (NetworkStatesType) msg.obj;

            for (WeakReference<NetworkUtilsCallback> weakCallback : m_List)
            {
                if (AvqUtils.Weak.isValidWeak(weakCallback))
                {
                    weakCallback.get().onNetworkChange(type);
                }
            }
        }

    };

    public void onNetworkChange(NetworkStatesType nst)
    {
        AvqUtils.handler.send_message(handler, nst, 0);
    }

    public NetworkStatesType getNetWorkType()
    {
        ConnectivityManager cm = (ConnectivityManager) ContextUtils.getSharedContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        NetworkStatesType curNetworkStatus;

        if (null != activeNetwork)
        {
            int nType = activeNetwork.getType();
            if (nType == ConnectivityManager.TYPE_WIFI || nType == ConnectivityManager.TYPE_WIMAX)
            {
                curNetworkStatus = NetworkStatesType.WIFI_CONNECTED;
            }
            else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                curNetworkStatus = NetworkStatesType.DATA_CONNECTED;
            }
            else
            {
                curNetworkStatus = NetworkStatesType.DATA_CONNECTED;
            }
        }
        else
        {
            curNetworkStatus = NetworkStatesType.DIS_CONNECTED;
        }

        return curNetworkStatus;
    }

}
