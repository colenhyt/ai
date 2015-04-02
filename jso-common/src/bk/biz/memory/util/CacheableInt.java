/**
 * $RCSfile: CacheableInt.java,v $
 * $Revision: 1.1 $
 * $Date: 2007/10/25 09:29:51 $
 *
 * New Jive  from Jdon.com.
 *
 * This software is the proprietary information of CoolServlets, Inc.
 * Use is subject to license terms.
 */

package bk.biz.memory.util;

/**
 * Wrapper for int values so they can be treated as Cacheable objects.
 * Integer is a final class, so it can't be extended.
 */
public class CacheableInt implements Cacheable {

    /**
     * Wrapped int value.
     */
    private int intValue;

    /**
     * Creates a new CacheableInt.
     *
     * @param string the int value to wrap.
     */
    public CacheableInt(int intValue) {
        this.intValue = intValue;
    }

    /**
     * Returns the underlying int value.
     *
     * @return the int value.
     */
    public int getInt() {
        return intValue;
    }

    //FROM THE CACHEABLE INTERFACE//

    public int getSize() {
        return CacheSizes.sizeOfObject() + CacheSizes.sizeOfInt();
    }
}