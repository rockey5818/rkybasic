package com.nfkj.basic.storage;


import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PrivateStorage extends Storage
{
    private final Set<Long> createdCacheTables = Collections.newSetFromMap(new ConcurrentHashMap<Long, Boolean>());
    private final Object creatorLock = new Object();

    /**
     * {@link PrivateStorage} constructor.
     *
     * @param storageManager The storage manager which provide SQL statement execution functions.
     */
    public PrivateStorage(final StorageManagerDef storageManager)
    {
        super(storageManager);
    }

    /**
     * Creates the cache table it it doesn't exist.
     * 
     * @param ownerUid
     *            the owner id of the cache table.
     */
    public void createCacheTableIfNotExists(final long ownerUid)
    {
        if (!createdCacheTables.contains(ownerUid))
        {
            synchronized (creatorLock)
            {
                if (!createdCacheTables.contains(ownerUid))
                {
                    createCacheTable(ownerUid);
                    createdCacheTables.add(ownerUid);
                }
            }
        }
    }

    /**
     * Clear cached data which belongs to the specified user.
     * 
     * @param ownerUid
     *            User ID of the cache owner.
     */
    public void clear(final long ownerUid)
    {
        synchronized (creatorLock)
        {
            createdCacheTables.remove(ownerUid);
            dropCacheTable(ownerUid);
        }
        createCacheTableIfNotExists(ownerUid);
    }

    /**
     * Create cache table for the specified user.
     * 
     * @param ownerUid
     *            User ID of the cache owner.
     */
    protected abstract void createCacheTable(final long ownerUid);

    /**
     * Get name of the cache table which belongs to the specified user.
     * 
     * @param ownerUid
     *            User ID of the cache owner.
     * @return Name of the cache table which belongs to the specified user.
     */
    protected abstract String getCacheTableName(final long ownerUid);

    /**
     * Drop cache table which belongs to the specified user.
     * 
     * @param ownerUid
     *            User ID of the cache owner.
     */
    private void dropCacheTable(final long ownerUid)
    {
        execute("DROP TABLE IF EXISTS `" + getCacheTableName(ownerUid) + "`");
    }
}
