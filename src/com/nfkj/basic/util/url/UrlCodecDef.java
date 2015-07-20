package com.nfkj.basic.util.url;
/**
 * 
 * @author Rockey
 *
 */
public interface UrlCodecDef
{
    String decode(String content);

    String encode(String s);
}
