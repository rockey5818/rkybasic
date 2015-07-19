package org.json.parser;

import org.json.me.JSONObjectDef;

public interface JSONEntityDesc
{
    void fromJson(JSONObjectDef jsonObj) throws Exception;

    JSONObjectDef toJson() throws Exception;
}
