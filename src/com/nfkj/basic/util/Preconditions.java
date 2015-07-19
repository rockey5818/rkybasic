package com.nfkj.basic.util;

import static java.lang.String.format;

/**
 * @author rockey
 */
public final class Preconditions
{
    /**
     * Don't let anyone instantiate this class.
     */
    private Preconditions()
    {
        // This constructor is intentionally empty.
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to
     * the calling method.
     * 
     * @param expression
     *            a boolean expression
     * @return {@code True} if the expression was valid, otherwise {@code false}
     *         .
     */
    public static boolean checkArgument(final boolean expression)
    {
        boolean valid = true;
        if (!expression)
        {
            Logger.logThrowable(new IllegalArgumentException());
            valid = false;
        }
        return valid;
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to
     * the calling method.
     * 
     * @param expression
     *            a boolean expression
     * @param errorMessage
     *            the exception message to use if the check fails
     */
    public static void checkArgument(final boolean expression, final String errorMessage)
    {
        if (!expression)
        {
        	Logger.logThrowable(new IllegalArgumentException(errorMessage));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to
     * the calling method.
     * 
     * @param expression
     *            a boolean expression
     * @param errorMessageTemplate
     *            a template for the exception message should the check fail.
     *            The message is formed by replacing each {@code %s} placeholder
     *            in the template with an argument. These are matched by
     *            position - the first {@code %s} gets
     *            {@code errorMessageArgs[0]}, etc. Unmatched arguments will be
     *            appended to the formatted message in square braces. Unmatched
     *            placeholders will be left as-is.
     * @param errorMessageArgs
     *            the arguments to be substituted into the message template.
     *            Arguments are converted to strings using
     *            {@link String#valueOf(Object)}.
     */
    public static void checkArgument(final boolean expression, final String errorMessageTemplate,
            final Object... errorMessageArgs)
    {
        if (!expression)
        {
        	Logger.logThrowable(new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs)));
        }
    }
}
