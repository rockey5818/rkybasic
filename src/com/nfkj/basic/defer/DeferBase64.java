package com.nfkj.basic.defer;

public interface DeferBase64
{
    String base64(String original);

    byte[] base64Decode(String original);

    byte[] base64(byte[] byteArray);
}
