package org.json.parser;

public interface JsonObjectTrigger
{
    void parserFoundObjectStart();

    void parserFoundObjectKey(String key);

    void parserFoundObjectEnd();

    void parserFoundArrayStart();

    void parserFoundArrayEnd();

    void parserFoundString(String string);

    void parserFoundError(String err);

    void parserFoundEnd();
}
