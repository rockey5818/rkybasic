package org.json.me.impl;

import org.json.me.JSONException;
import org.json.me.JSONObjectDef;

public class JSONTokener
{

    private int myIndex;

    private final String mySource;

    public JSONTokener(final String s)
    {
        myIndex = 0;
        mySource = s;
    }

    public void back()
    {
        if (myIndex > 0)
        {
            myIndex -= 1;
        }
    }

    public boolean more()
    {
        return myIndex < mySource.length();
    }

    public char next()
    {
        if (more())
        {
            final char c = mySource.charAt(myIndex);
            myIndex += 1;
            return c;
        }
        return 0;
    }

    public String next(final int n) throws JSONException
    {
        final int i = myIndex;
        final int j = i + n;
        if (j >= mySource.length())
        {
            throw syntaxError("Substring bounds error");
        }
        myIndex += n;
        return mySource.substring(i, j);
    }

    public char nextClean() throws JSONException
    {
        for (;;)
        {
            char c = next();
            if (c == '/')
            {
                switch (next())
                {
                    case '/':
                        do
                        {
                            c = next();
                        } while (c != '\n' && c != '\r' && c != 0);
                        break;
                    case '*':
                        for (;;)
                        {
                            c = next();
                            if (c == 0)
                            {
                                throw syntaxError("Unclosed comment.");
                            }
                            if (c == '*')
                            {
                                if (next() == '/')
                                {
                                    break;
                                }
                                back();
                            }
                        }
                        break;
                    default:
                        back();
                        return '/';
                }
            }
            else if (c == '#')
            {
                do
                {
                    c = next();
                } while (c != '\n' && c != '\r' && c != 0);
            }
            else if (c == 0 || c > ' ')
            {
                return c;
            }
        }
    }

    public String nextString(final char quote) throws JSONException
    {
        char c;
        final StringBuffer sb = new StringBuffer();
        for (;;)
        {
            c = next();
            switch (c)
            {
                case 0:
                case '\n':
                case '\r':
                    throw syntaxError("Unterminated string");
                case '\\':
                    c = next();
                    switch (c)
                    {
                        case 'b':
                            sb.append('\b');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 'u':
                            sb.append((char) Integer.parseInt(next(4), 16));
                            break;
                        case 'x':
                            sb.append((char) Integer.parseInt(next(2), 16));
                            break;
                        default:
                            sb.append(c);
                    }
                    break;
                default:
                    if (c == quote)
                    {
                        return sb.toString();
                    }
                    sb.append(c);
            }
        }
    }

    public Object nextValue() throws JSONException
    {
        char c = nextClean();
        final String s;

        switch (c)
        {
            case '"':
            case '\'':
                return nextString(c);
            case '{':
                back();
                return new JSONObject(this);
            case '[':
                back();
                return new JSONArray(this);
        }

        final StringBuffer sb = new StringBuffer();
        while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0)
        {
            sb.append(c);
            c = next();
        }
        back();

        s = sb.toString().trim();
        if (s.equals(""))
        {
            throw syntaxError("Missing value.");
        }
        if (s.toLowerCase().equals("null"))
        {
            return JSONObjectDef.NULL;
        }

        return s;
    }

    public JSONException syntaxError(final String message)
    {
        return new JSONException(message + toString());
    }

    public String toString()
    {
        return " at character " + myIndex + " of " + mySource;
    }
}