package com.nfkj.basic.util.json;

import com.nfkj.basic.defer.CoreDeferObject;
import com.nfkj.basic.defer.DeferMD5;


public final class MD5Util
{
    /**
     * Don't let anyone instantiate this class.
     */
    private MD5Util()
    {
        // This constructor is intentionally empty.
    }

    public static String md5Encrypt(final String originStr)
    {
        final DeferMD5 deferMD5 = CoreDeferObject.get().getDeferMD5();
        return deferMD5.md5(originStr);
    }
}
