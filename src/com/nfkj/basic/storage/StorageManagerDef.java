package com.nfkj.basic.storage;

import java.util.List;
import java.util.Map;

public interface StorageManagerDef
{
    void executeMultiSQL(List<String> queries);

    void executeSQL(String query);

    List<String> executeSQLForList(String query);

    List<Map<String, String>> executeSQLForMapList(String query);

    String executeSQLForString(String query);

    long executeSqlForLong(String query);

    int executeSqlForInt(String query);
}
