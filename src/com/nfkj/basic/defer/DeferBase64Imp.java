package com.nfkj.basic.defer;

import android.util.Base64;

public class DeferBase64Imp implements DeferBase64
{
    @Override
    public String base64(String original)
    {
        return Base64.encodeToString(original.getBytes(), Base64.NO_WRAP);
    }

    @Override
    public byte[] base64Decode(final String original)
    {
        return Base64.decode(original, Base64.NO_WRAP);
    }

    @Override
    public byte[] base64(byte[] byteArray)
    {
        return Base64.encode(byteArray, Base64.NO_WRAP);
    }
}
