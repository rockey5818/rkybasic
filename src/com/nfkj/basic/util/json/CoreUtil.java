package com.nfkj.basic.util.json;


import java.util.ArrayList;
import java.util.List;

import com.nfkj.basic.util.CoreSettings;
import com.nfkj.basic.util.Strings;

/**
 * @author Rockey
 */
public final class CoreUtil
{
    private static final String SLASH = "/";

    /**
     * Don't let anyone instantiate this class.
     */
    private CoreUtil()
    {
        // This constructor is intentionally empty.
    }

    /**
     * Add resource prefix to a url.
     *
     * @param url Url string to add resource prefix.
     * @return Full url of the resource.
     */
    public static String addResourcePrefix(final String url)
    {
        if (Strings.nullOrEmpty(url) || hasPrefix(url))
        {
            return url;
        }

        final String prefix = CoreSettings.getResourceUrlPrefix();
        if (prefix.endsWith(SLASH) && url.startsWith(SLASH))
        {
            return prefix + url.substring(1);
        }
        else if (prefix.endsWith(SLASH) || url.startsWith(SLASH))
        {
            return prefix + url;
        }
        else
        {
            return prefix + SLASH + url;
        }
    }

    /**
     * Add resource prefix to all elements of a URL list.
     *
     * @param urlWithoutPrefixList The URL list to add prefix.
     * @return The URL list with prefix.
     */
    public static List<String> addResourcePrefix(final List<String> urlWithoutPrefixList)
    {
        final List<String> urlList = new ArrayList<String>();
        for (final String urlWithoutPrefix : urlWithoutPrefixList)
        {
            urlList.add(addResourcePrefix(urlWithoutPrefix));
        }
        return urlList;
    }

    public static String deleteResourcePrefix(final String url)
    {
        if (Strings.nullOrEmpty(url))
        {
            return "";
        }

        final int indexAfterProtocol = url.indexOf("//");
        if (-1 == indexAfterProtocol)
        {
            return url;
        }
        final int relativeUrl = url.indexOf(SLASH, indexAfterProtocol + 2);
        if (-1 == relativeUrl)
        {
            return "";
        }
        return url.substring(relativeUrl + 1);
    }

    /**
     * Check if url has already contains prefix.
     *
     * @param url Url to check.
     * @return {@code True} if prefix exist, otherwise {@code false}.
     */
    private static boolean hasPrefix(final String url)
    {
        return url.contains("://");
    }

    /**
     * Check if the provided filename was end with ".gif".
     *
     * @param filename The provided filename.
     * @return {@code True} if was, otherwise {@code false}.
     */
    public static boolean isGif(final String filename)
    {
        return Strings.notNullOrEmpty(filename) && filename.toLowerCase().endsWith(".gif");
    }

    /**
     * Check if the provided filename was end with ".png".
     *
     * @param filename The provided filename.
     * @return {@code True} if was, otherwise {@code false}.
     */
    public static boolean isPng(final String filename)
    {
        return Strings.notNullOrEmpty(filename) && filename.toLowerCase().endsWith(".png");
    }
}
