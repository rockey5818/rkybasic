package com.nfkj.basic.defer;

import org.json.me.JSONFactory;
import org.json.parser.LargeJsonParserDef;


/**
 * @author Rockey
 */
public final class CoreDeferObject
{
    private static final CoreDeferObject DEFER = new CoreDeferObject();
    private DeferMD5 deferMD5;
    private JSONFactory jsonFactory;
    private LargeJsonParserDef jsonParser;

    public static CoreDeferObject get()
    {
        return DEFER;
    }

    private CoreDeferObject()
    {
        // This constructor is intentionally empty.
    }

    public DeferMD5 getDeferMD5()
    {
        return deferMD5;
    }

    public void setDeferMD5(final DeferMD5 deferMD5)
    {
        this.deferMD5 = deferMD5;
    }

    public boolean isMd5UtilRegistered()
    {
        return null != getDeferMD5();
    }

    public JSONFactory getJsonFactory()
    {
        return jsonFactory;
    }

    public void setJsonFactory(final JSONFactory jsonFactory)
    {
        this.jsonFactory = jsonFactory;
    }

    public boolean isJsonFactoryRegistered()
    {
        return null != getJsonFactory();
    }
  
}
