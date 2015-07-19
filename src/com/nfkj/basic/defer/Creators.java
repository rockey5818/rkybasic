package com.nfkj.basic.defer;

 /**  
 * @author Rockey
 * @Description todo
 */
public abstract class Creators
{
	  /**
     * Don't let anyone instantiate this class.
     */
    protected Creators()
    {
        // This constructor is intentionally empty.
    }

    public static DeferObjectCreator getDeferObjectCreator()
    {
        return DeferBinderFactory.get().getCreator();
    }
}
