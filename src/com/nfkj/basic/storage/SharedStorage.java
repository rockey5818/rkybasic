package com.nfkj.basic.storage;

import com.nfkj.basic.annotations.ThreadSafe;

/**
 * 
 * @author Rockey
 *
 */
@ThreadSafe
public abstract class SharedStorage extends Storage
{
    private volatile boolean cacheTableCreated;
    private final Object cacheTableCreatorLock = new Object();

    /**
     * SharedStorage constructor.
     * 
     * @param sm
     *            The storage manager which provide SQL statement execution
     *            functions.
     */
    public SharedStorage(final StorageManagerDef sm)
    {
        super(sm);
    }

    /**
     * Clear cache table data by recreate the cache table.
     */
    public void clear()
    {
        synchronized (cacheTableCreatorLock)
        {
            cacheTableCreated = false;
            dropCacheTable();
        }
        createCacheTableIfNotExists();
    }

    protected final void createCacheTableIfNotExists()
    {
        if (!cacheTableCreated)
        {
            synchronized (cacheTableCreatorLock)
            {
                if (!cacheTableCreated)
                {
                    createCacheTable();
                    cacheTableCreated = true;
                }
            }
        }
    }

    /**
     * Create the cache table.
     */
    protected abstract void createCacheTable();

    /**
     * Get name of the cache table.
     */
    protected abstract String getCacheTableName();

    /**
     * Drop the cache.
     */
    private void dropCacheTable()
    {
        execute("DROP TABLE IF EXISTS `" + getCacheTableName() + "`");
    }
}
