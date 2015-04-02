/**
 * $RCSfile: CacheableStringArray.java,v $
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
 * Wrapper for long[] values so they can be treated as cacheable objects.
 */
public class CacheableStringArray implements Cacheable {

    /**
     * Wrapped long object.
     */
    private String[] longArray;

    /**
     * Creates a new CacheableLong.
     *
     * @param string the Long object to wrap.
     */
    public CacheableStringArray(String[] longArray) {
        this.longArray = longArray;
    }

    /**
     * Returns the long [] wrapped by the CacheableLongArray object.
     *
     * @return the long [] object.
     */
    public String[] getStringArray() {
        return longArray;
    }

    //FROM THE CACHEABLE INTERFACE//

    public int getSize() {
        return CacheSizes.sizeOfObject() + CacheSizes.sizeOfLong() * longArray.length;
    }
}