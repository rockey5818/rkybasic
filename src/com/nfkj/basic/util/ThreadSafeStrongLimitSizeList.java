package com.nfkj.basic.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeStrongLimitSizeList
{
    private int m_limitLenght = 1;
    private Lock m_lock = new ReentrantLock();
    private Map<Object, Integer> m_StrongBufferLength = new HashMap<Object, Integer>();
    private List<Object> m_StrongBuffer = new ArrayList<Object>();
    private int m_bufferLength = 0;

    public ThreadSafeStrongLimitSizeList(int limitLength)
    {
        if (limitLength > 0)
        {
            m_limitLenght = limitLength;
        }
    }

    private void refreshStrongBuffer(Object obj, int size)
    {
        if (m_bufferLength > m_limitLenght)
        {
            Object str = m_StrongBuffer.get(0);
            int length = m_StrongBufferLength.get(str);

            m_StrongBuffer.remove(0);
            m_StrongBufferLength.remove(str);
            m_bufferLength -= length;
            refreshStrongBuffer(str, size);
        }
        else
        {
            m_StrongBuffer.add(obj);
            m_StrongBufferLength.put(obj, size);
            m_bufferLength += size;
        }
    }

    public void add(Object obj)
    {
        if (obj == null)
        {
            return;
        }

        /*
         * Below code avoid duplicately adding same object otherwise will cause
         * crash when remove(0) by frankie
         */
        if (m_StrongBufferLength.get(obj) != null)
        {
            return;
        }

        m_lock.lock();
        refreshStrongBuffer(obj, 1);
        // System.out.println("String buffer length:" + m_bufferLength);
        m_lock.unlock();
    }
}
