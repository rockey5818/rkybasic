package com.nfkj.basic.storage;


import java.util.List;
import java.util.Map;

import com.nfkj.basic.parse.map.MapParser;
import com.nfkj.basic.util.url.UrlCodec;
/**
 * 
 * @author Rockey
 *
 */
public class Storage
{
    protected static final String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS";
    protected static final String CREATE_INDEX_IF_NOT_EXISTS = "CREATE INDEX IF NOT EXISTS";
    protected static final String CREATE_UNIQUE_INDEX_IF_NOT_EXISTS = "CREATE UNIQUE INDEX IF NOT EXISTS";


    protected static final String INSERT_INTO = "INSERT INTO";
    protected static final String INSERT_OR_REPLACE_INTO = "INSERT OR REPLACE INTO";
    protected static final String INSERT_OR_IGNORE_INTO = "INSERT OR IGNORE INTO";

    protected static final String SELECT = "SELECT";
    protected static final String FROM = "FROM";
    protected static final String WHERE = "WHERE";
    protected static final String AND = "AND";
    protected static final String OR = "OR";
    protected static final String NOT = "NOT";
    protected static final String ORDER_BY = "ORDER BY";
    protected static final String DESC = "DESC";
    protected static final String LIMIT = "LIMIT";
    protected static final String OFFSET = "OFFSET";
    protected static final String UPDATE = "UPDATE";
    protected static final String SET = "SET";
    protected static final String DELETE = "DELETE";
    protected static final String IN = "IN";
    protected static final String ON = "ON";
    protected static final String AS = "AS";
    protected static final String LIKE = "LIKE";
    protected static final String GROUP_BY = "GROUP BY";
    protected static final String VALUES = "VALUES";

    protected static final String INTEGER = "INTEGER";
    protected static final String TEXT = "TEXT";
    protected static final String REAL = "REAL";

    protected static final String PRIMARY_KEY = "PRIMARY KEY";
    protected static final String NOT_NULL = "NOT NULL";
    protected static final String DEFAULT = "DEFAULT";
    protected static final String AUTOINCREMENT = "AUTOINCREMENT";
    protected static final String UNIQUE = "UNIQUE";

    protected static final String COUNT = "COUNT";
    protected static final String MAX = "MAX";
    protected static final String EXISTS = "EXISTS";

    protected static final String TRUE = "1";
    protected static final String FALSE = "0";

    protected static final int EXIST = 1;

    protected static final byte NOT_SET = -1;

    private static final byte TRUE_VALUE = 1;

    /** The storage manager which provide SQL statement execution functions. */
    private final StorageManagerDef storageManager;

    /**
     * Storage constructor
     *
     * @param storageManager The storage manager which provide SQL statement execution functions.
     */
    public Storage(final StorageManagerDef storageManager)
    {
        this.storageManager = storageManager;
    }

    /**
     * Get the storage manager.
     *
     * @return The storage manager.
     */
    protected final StorageManagerDef getStorageManager()
    {
        return storageManager;
    }

    /**
     * Wraps the string with url codec.
     *
     * @param str The string to wrap.
     * @return The wrapped string.
     */
    protected static String wrap(final String str)
    {
        return UrlCodec.encode(str);
    }

    /**
     * Unwraps the string with url codec.
     *
     * @param str The string to unwrap.
     * @return The unwrapped string.
     */
    protected static String unwrap(final String str)
    {
        return UrlCodec.decode(str);
    }

    /**
     * Execute a SQL query.
     *
     * @param query The query to be executed.
     */
    protected final void execute(final String query)
    {
        getStorageManager().executeSQL(query);
    }

    /**
     * Execute multiple SQL queries.
     *
     * @param queries The queries to be executed.
     */
    protected final void execute(final List<String> queries)
    {
        getStorageManager().executeMultiSQL(queries);
    }

    /**
     * Execute SQL query and fetch result as a {@link String}.
     *
     * @param query The SQL query to be executed.
     * @return The execution result.
     */
    protected final String executeForString(final String query)
    {
        return getStorageManager().executeSQLForString(query);
    }

    /**
     * Execute SQL query and fetch result as an {@code int}.
     *
     * @param query The SQL query to be executed.
     * @return The execution result.
     */
    protected final int executeForInt(final String query)
    {
        return getStorageManager().executeSqlForInt(query);
    }

    /**
     * Execute SQL query and fetch result as a {@code long}.
     *
     * @param query The SQL query to be executed.
     * @return The execution result.
     */
    protected final long executeForLong(final String query)
    {
        return getStorageManager().executeSqlForLong(query);
    }

    /**
     * Execute a SQL query and fetch result as a {@code List} of {@code Map}.
     *
     * @param query The SQL query to be executed.
     * @return The execution result.
     */
    protected final List<Map<String, String>> executeForMapList(final String query)
    {
        return getStorageManager().executeSQLForMapList(query);
    }

    /**
     * Execute a SQL query and fetch result as a {@code List} of {@code String}.
     *
     * @param query The SQL query to be executed.
     * @return The execution result.
     */
    protected final List<String> executeForList(final String query)
    {
        return getStorageManager().executeSQLForList(query);
    }

    /**
     * Get value of a specified key from provided map as a long.
     *
     * @param map          The provided data map.
     * @param key          The specified key.
     * @param defaultValue The default return value if the specified key not exist.
     * @return Value of the specified key if exist. Otherwise default value returned.
     */
    protected final long getLong(final Map<String, String> map, final String key, final long defaultValue)
    {
        return MapParser.getLong(map, key, defaultValue);
    }

    /**
     * Get value of a specified key from provided map as a long.
     *
     * @param map The provided data map.
     * @param key The specified key.
     * @return Value of the specified key if exist. Otherwise -1 returned.
     */
    protected final long getLong(final Map<String, String> map, final String key)
    {
        return getLong(map, key, -1L);
    }

    /**
     * Get value of a specified key from provided map as a double.
     *
     * @param map          The provided data map.
     * @param key          The specified key.
     * @param defaultValue The default return value if the specified key not exist.
     * @return Value of the specified key if exist. Otherwise default value returned.
     */
    protected final double getDouble(final Map<String, String> map, final String key, final double defaultValue)
    {
        return MapParser.getDouble(map, key, defaultValue);
    }

    /**
     * Get value of a specified key from provided map as a double.
     *
     * @param map The provided data map.
     * @param key The specified key.
     * @return Value of the specified key if exist. Otherwise -1 returned.
     */
    protected final double getDouble(final Map<String, String> map, final String key)
    {
        return getDouble(map, key, -1D);
    }

    /**
     * Get value of a specified key from provided map as a integer.
     *
     * @param map          The provided data map.
     * @param key          The specified key.
     * @param defaultValue The default return value if the specified key not exist.
     * @return Value of the specified key if exist. Otherwise default value returned.
     */
    protected final int getInteger(final Map<String, String> map, final String key, final int defaultValue)
    {
        return MapParser.getInteger(map, key, defaultValue);
    }

    /**
     * Get value of a specified key from provided map as a integer.
     *
     * @param map The provided data map.
     * @param key The specified key.
     * @return Value of the specified key if exist. Otherwise -1 returned.
     */
    protected final int getInteger(final Map<String, String> map, final String key)
    {
        return getInteger(map, key, -1);
    }

    /**
     * Get value of a specified key from provided map as a string.
     *
     * @param map The provided data map.
     * @param key The specified key.
     * @return Value of the specified key if exist. Otherwise an empty string returned.
     */
    protected final String getString(final Map<String, String> map, final String key)
    {
        return MapParser.getString(map, key);
    }

    /**
     * Get value of a specified key from provided map as a byte.
     *
     * @param map The provided data map.
     * @param key The specified key.
     * @return Value of the specified key if exist. Otherwise -1 returned.
     */
    protected final byte getByte(final Map<String, String> map, final String key, final byte defaultValue)
    {
        return MapParser.getByte(map, key, defaultValue);
    }

    protected final boolean getBoolean(final Map<String, String> map, final String key)
    {
        return TRUE_VALUE == getInteger(map, key);
    }
}
