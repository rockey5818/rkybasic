package com.nfkj.basic.logging;
/**
 * @author Rockey
 */
public interface ProtocolLogger
{
    void info(String s);

    void receive(String s);

    void send(String s);

    void logThrowable(Throwable e);
}
