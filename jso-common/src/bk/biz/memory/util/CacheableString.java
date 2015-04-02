/**
 * $RCSfile: CacheableString.java,v $
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
 * Wrapper for String objects so they can be treated as Cacheable objects.
 * String is a final class, so it can't be extended.
 */
public class CacheableString implements Cacheable {

    /**
     * Wrapped String object.
     */
    private String string;

    /**
     * Creates a new CacheableString.
     *
     * @param string the String object to wrap.
     */
    public CacheableString(String string) {
        this.string = string;
    }

    /**
     * Returns the String wrapped by the CacheableString object.
     *
     * @return the String object.
     */
    public String getString() {
        return string;
    }

    //FROM THE CACHEABLE INTERFACE//

    public int getSize() {
        return CacheSizes.sizeOfString(string);
    }
}
