package com.nfkj.basic.defer.impl;

import com.nfkj.basic.defer.DeferObjectCreator;
import com.nfkj.basic.logging.ProtocolLogger;
import com.nfkj.basic.util.RkyLog;

/**
 * 
 * @author Rockey
 *
 */
public class JavaDeferObjectCreator implements DeferObjectCreator
{
  
    @Override
    public ProtocolLogger createProtocolLogger()
    {
        return new RkyLog();
    }

}
