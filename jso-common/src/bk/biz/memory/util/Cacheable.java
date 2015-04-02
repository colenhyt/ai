/**
 * $RCSfile: Cacheable.java,v $
 * $Revision: 1.1 $
 * $Date: 2007/10/25 09:29:51 $
 *
 * New Jive  from Jdon.com.
 *
 * This software is the proprietary information of CoolServlets, Inc.
 * Use is subject to license terms.
 */

package bk.biz.memory.util;

import java.io.Serializable;

import net.sf.ehcache.Cache;

/**
 * Interface that defines the necessary behavior for objects added to a Cache.
 * Objects only need to know how big they are (in bytes). That size
 * should be considered to be a best estimate of how much memory the Object
 * occupies and may be based on empirical trials or dynamic calculations.<p>
 *
 * While the accuracy of the size calculation is important, care should be
 * taken to minimize the computation time so that cache operations are
 * speedy.
 *
 * @see Cache
 */
public interface Cacheable extends Serializable{

    /**
     * Returns the approximate size of the Object in bytes. The size should be
     * considered to be a best estimate of how much memory the Object occupies
     * and may be based on empirical trials or dynamic calculations.<p>
     *
     * @return the size of the Object in bytes.
     */
    public int getSize();
}
