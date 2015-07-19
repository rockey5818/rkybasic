package com.nfkj.basic.storage.model;

/**
 * @author rockey
 */
public final class CoreCacheTableName
{
    /** Common cache table name prefix. */
    public static final String COMMON_PREFIX = "nfkj_";

    /* Table name for shared cache tables. (START) ================================================================= */

    /** @Description Table name of the user table. 
     for  example 
     public static final String USER_INFO = COMMON_PREFIX + "table name";
    */
    /* Table name for shared cache tables. (END) =================================================================== */

    /* Table name prefix for private cache tables. (START) ========================================================= */

    /** @description  */

    /* Table name prefix for private cache tables. (END) =========================================================== */

    /**
     * Don't let anyone instantiate this class.
     */
    private CoreCacheTableName()
    {
        // This constructor is intentionally empty.
    }
}
