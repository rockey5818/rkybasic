package com.nfkj.basic.defer;

public class DeferBinderFactory
{
    private static DeferBinderFactory factory = null;
    private DeferObjectCreator creator = null;

    public static DeferBinderFactory get()
    {
        if (null == factory)
        {
            factory = new DeferBinderFactory();
        }
        return factory;
    }

    public DeferObjectCreator getCreator()
    {
        return this.creator;
    }

    public void registerDeferObjectCreator(final DeferObjectCreator creator)
    {
        this.creator = creator;
    }

    public boolean isDeferObjectCreatorRegistered()
    {
        return null != this.creator;
    }
}
