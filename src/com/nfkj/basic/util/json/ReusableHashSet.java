package com.nfkj.basic.util.json;

import java.util.HashSet;

/**
 * @author Rockey
 */
public final class ReusableHashSet<E>  extends HashSet<E>
{
    private volatile boolean usable = false;

    public void init()
    {
        usable = true;
    }

    public boolean initialized()
    {
        return usable;
    }

    @Override
    public boolean add(E element)
    {
        return usable && super.add(element);
    }

    public void recycle()
    {
        usable = false;
        clear();
    }
}
