package com.nfkj.basic.util.json;

import java.util.TreeSet;

/**
 * @author Rockey
 */
public final class ReusableTreeSet<E> extends TreeSet<E>
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
