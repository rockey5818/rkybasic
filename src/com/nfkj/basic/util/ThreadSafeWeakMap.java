package com.nfkj.basic.util;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.nfkj.device.cache.AvqUtils;

public class ThreadSafeWeakMap<Type>
{

    private Lock m_lock = new ReentrantLock();
    private Map<String, WeakReference<Type>> m_Buffer = new HashMap<String, WeakReference<Type>>();

    public ThreadSafeWeakMap()
    {

    }

    public void add(String key, Type obj)
    {
        if (key == null || obj == null)
        {
            return;
        }

        m_lock.lock();

        m_Buffer.put(key, new WeakReference<Type>(obj));
        Set<String> keys = m_Buffer.keySet();
        Map<String, WeakReference<Type>> tempBuffer = new HashMap<String, WeakReference<Type>>();

        for (String tempkey : keys)
        {
            if (AvqUtils.Weak.isValidWeak(m_Buffer.get(tempkey)))
            {
                tempBuffer.put(tempkey, m_Buffer.get(tempkey));
            }
            else
            {
                // System.out.println("frank release buffer:" + tempkey);
            }
        }

        m_Buffer = tempBuffer;

        // System.out.println("frank buffer size:"+m_Buffer.size());

        m_lock.unlock();
    }

    public Type get(String key)
    {
        if (key == null)
        {
            return null;
        }

        Type obj = null;

        m_lock.lock();

        WeakReference<Type> weakObj = m_Buffer.get(key);

        if (AvqUtils.Weak.isValidWeak(weakObj))
        {
            obj = weakObj.get();
        }

        m_lock.unlock();

        return obj;
    }

    public void remove(Type obj)
    {
        if (obj == null)
        {
            return;
        }

        m_lock.lock();

        Set<String> keys = m_Buffer.keySet();
        Iterator<String> it = keys.iterator();

        while (it.hasNext())
        {
            String key = it.next();
            Type value = null;
            if (AvqUtils.Weak.isValidWeak(m_Buffer.get(key)))
            {
                value = m_Buffer.get(key).get();
                if (obj == value)
                {
                    m_Buffer.remove(key);
                    return;
                }
            }
        }

        m_lock.unlock();
    }
    
    public void remove()
    {
        m_Buffer.clear();
    }

}
