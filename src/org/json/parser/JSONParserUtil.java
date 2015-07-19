package org.json.parser;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class JSONParserUtil
{
    public static List<Field> getAllFields(final Class<?> clazz)
    {
        final List<Field> result = getAllFieldsRec(clazz, new ArrayList<Field>());
        if (result.size() > 0 && "isa".equals(result.get(0).getName()))
        {
            result.remove(0);
        }
        return result;
    }

    private static List<Field> getAllFieldsRec(final Class<?> clazz, final List<Field> result)
    {
        final Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null)
        {
            getAllFieldsRec(superClazz, result);
        }
        final Field[] declaredFields = clazz.getDeclaredFields();
        for (final Field declaredField : declaredFields)
        {
            if (!Modifier.isStatic(declaredField.getModifiers()))
            {
                result.add(declaredField);
            }
        }
        return result;
    }
}
