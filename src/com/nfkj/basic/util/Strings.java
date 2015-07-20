package com.nfkj.basic.util;
import java.util.Collection;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @author Rockey
 *
 */
public class Strings
{
    /**
     * Compares 2 strings. AAA&lt;AAa&lt;aAA&lt;Bbb&lt;bBB&lt;CCC&lt;CCCc
     * 
     * @return negative if s1 &lt; s2, 0 if s1 == s2, positive if s1 &gt; s2
     */
    public static int compare(String s1, String s2)
    {
        int len1 = s1.length();
        int len2 = s2.length();
        int len = Math.min(len1, len2);
        for (int i = 0; i < len; ++i)
        {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            char l1 = Character.toLowerCase(c1);
            char l2 = Character.toLowerCase(c2);
            if (l1 != l2)
            {
                return l1 < l2 ? -1 : 1;
            }

            if (c1 != c2)
            {
                return c1 < c2 ? -1 : 1;
            }
        }
        return len1 - len2;
    }

    /**
     * Compare protocol commands, why we don't use
     * string.equals/string.equalsIgnoreCase is because we need to make it be
     * easy maintained. Like, if we want to make the command comparison be case
     * insensitive, we only need to change this method.
     * 
     * @param cmd
     *            the first command
     * @param another
     *            another command
     * 
     * @return true for command equality, false for otherwise
     */
    public static boolean equalCommands(String cmd, String another)
    {
        return equalsIgnoreCase(cmd, another);
    }

    /**
     * Compare user names, why we don't use
     * string.equals/string.equalsIgnoreCase is because we need to make it be
     * easy maintained. Like, if we want to make the name comparison be case
     * insensitive, we only need to change this method.
     * 
     * @param name
     *            the first name
     * @param another
     *            another name
     * @return true for name equality, false for otherwise
     */
    public static boolean equalNames(String name, String another)
    {
        return equals(name, another);
    }

    /**
     * Checks if two strings are equals. What this method differs from
     * String.equals(s) is that this method allows the strings to be null.
     * 
     * @param s1
     *            the first input string
     * @param s2
     *            the second input string
     * @return true when two not null strings are equal, false for otherwise.
     */
    public static boolean equals(String s1, String s2)
    {
        return !(s1 == null || s2 == null) && s1.equals(s2);
    }

    /**
     * Checks if two strings are equal case insensitively. What this method
     * differs from String.equals(s) is that this method allows the strings to
     * be null.
     * 
     * @param s1
     *            the first input string
     * @param s2
     *            the second input string
     * @return true when two not null strings are equal case insensitively,
     *         false for otherwise.
     */
    public static boolean equalsIgnoreCase(String s1, String s2)
    {
        return !(s1 == null || s2 == null) && s1.equalsIgnoreCase(s2);
    }

    /**
     * Checks if all the chars in the string are digits.
     * 
     * @param str
     *            the input string
     * @return true for all digits, false for otherwise.
     */
    public static boolean isDigits(String str)
    {
        if (nullOrEmpty(str))
        {
            return false;
        }

        Pattern pattern = Pattern.compile("-?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * Joins multiple numbers to a string with specified separator.
     * 
     * @param numbers
     *            The set of numbers
     * @param separator
     *            The specified separator
     * @return The joined string.
     */
    public static String joinIntegerSet(final Set<Integer> numbers, final char separator)
    {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (final int number : numbers)
        {
            if (!first)
            {
                builder.append(separator);
            }
            builder.append(number);
            first = false;
        }
        return builder.toString();
    }

    /**
     * Joins multiple numbers to a string with specified separator.
     * 
     * @param numbers
     *            The set of numbers
     * @param separator
     *            The specified separator
     * @return The joined string.
     */
    public static String joinNumbers(final Set<Long> numbers, final char separator)
    {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Long number : numbers)
        {
            if (!first)
            {
                builder.append(separator);
            }
            builder.append(number);
            first = false;
        }
        return builder.toString();
    }

    /**
     * Joins multiple strings to a string with specified separator.
     * 
     * @param strings
     *            The set of strings;
     * @param separator
     *            The specified separator
     * @return The joined string.
     */
    public static String joinStrings(final Collection<String> strings, final char separator)
    {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (final String str : strings)
        {
            if (!first)
            {
                builder.append(separator);
            }
            builder.append(str);
            first = false;
        }
        return builder.toString();
    }

    /**
     * Checks if the string is not null or empty.
     * 
     * @param str
     *            the input string
     * @return true when the string is not null or empty, false for otherwise.
     */
    public static boolean notNullOrEmpty(String str)
    {
        return str != null && str.length() > 0 && !equals(str, "null");
    }

    /**
     * Checks if the string is null or empty.
     * 
     * @param str
     *            the input string
     * @return true when the string is either null or empty, false for
     *         otherwise.
     */
    public static boolean nullOrEmpty(String str)
    {
        return str == null || str.length() == 0;
    }

    /**
     * Returns a literal pattern <code>String</code> for the specified
     * <code>String</code>.
     * 
     * <p>
     * This method produces a <code>String</code> that can be used to create a
     * <code>Pattern</code> that would match the string <code>s</code> as if it
     * were a literal pattern.
     * </p>
     * Metacharacters or escape sequences in the input sequence will be given no
     * special meaning.
     * 
     * @param s
     *            The string to be literalized
     * @return A literal string replacement
     */
    public static String quote(String s)
    {
        int slashEIndex = s.indexOf("\\E");
        if (slashEIndex == -1)
            return "\\Q" + s + "\\E";

        String sb = "\\Q";

        slashEIndex = 0;
        int current = 0;
        while ((slashEIndex = s.indexOf("\\E", current)) != -1)
        {
            sb += s.substring(current, slashEIndex);
            current = slashEIndex + 2;
            sb += "\\E\\\\E\\Q";
        }
        sb += s.substring(current, s.length());
        sb += "\\E";
        return sb;
    }

    /**
     * Checks if the string starts with the given char.
     * 
     * @param s
     *            the string
     * @param ch
     *            the char
     * @return true if the string starts with the char, false for otherwise.
     */
    public static boolean startsWith(String s, char ch)
    {
        return !s.isEmpty() && s.charAt(0) == ch;
    }

    public static boolean parseBoolean(final String string)
    {
        return Strings.equals("1", string) || Boolean.parseBoolean(string);
    }

    public static String joinLong(final Collection<Long> numbers, final char separator)
    {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (final long number : numbers)
        {
            if (!first)
            {
                builder.append(separator);
            }
            builder.append(number);
            first = false;
        }
        return builder.toString();
    }
}
