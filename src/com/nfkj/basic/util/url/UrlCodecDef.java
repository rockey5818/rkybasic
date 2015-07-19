package com.nfkj.basic.util.url;

public interface UrlCodecDef
{
    String decode(String content);

    String encode(String s);
}
