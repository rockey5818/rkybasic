package com.nfkj.basic.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.nfkj.basic.defer.Creators;
import com.nfkj.basic.logging.ProtocolLogger;

public class Logger
{
    private static final ProtocolLogger LOGGER = Creators.getDeferObjectCreator().createProtocolLogger();
    private static final Executor executor = Executors.newSingleThreadExecutor();
    private static boolean enabledLogger = true;

    /**
     * Don't let anyone instantiate this class.
     */
    private Logger()
    {
        // This constructor is intentionally empty.
    }

    public static void logThrowable(final Throwable e)
    {
        if (LOGGER != null && enabledLogger)
        {
            log(e.getMessage());
            LOGGER.logThrowable(e);
        }
    }

    public static void log(final String msg)
    {
        write(msg);
    }

    public static void info(final String msg)
    {
        write(msg);
    }

    public static void warn(final String msg)
    {
        write(msg);
    }

    public static void error(final String msg)
    {
        write(msg);
    }

    private static void writeAsync(final String msg)
    {
        executor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                LOGGER.info(msg);
            }
        });
    }

    public static void enabled()
    {
        enabledLogger = true;
    }

    public static void disabled()
    {
        enabledLogger = false;
    }

    private static void write(final String msg)
    {
        if (LOGGER != null && enabledLogger)
        {
            writeAsync(msg);
        }
    }
}
