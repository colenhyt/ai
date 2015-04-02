/**
 * $RCSfile: CacheSizes.java,v $
 * $Revision: 1.1 $
 * $Date: 2007/10/25 09:29:51 $
 *
 * New Jive  from Jdon.com.
 *
 * This software is the proprietary information of CoolServlets, Inc.
 * Use is subject to license terms.
 */

package bk.biz.memory.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class for determining the sizes in bytes of commonly used objects.
 * Classes implementing the Cacheable interface should use this class to
 * determine their size.
 */
public class CacheSizes {

    /**
     * Returns the size in bytes of a basic Object. This method should only
     * be used for actual Object objects and not classes that extend Object.
     *
     * @return the size of an Object.
     */
    public static int sizeOfObject() {
        return 4;
    }

    /**
     * Returns the size in bytes of a String.
     *
     * @param string the String to determine the size of.
     * @return the size of a String.
     */
    public static int sizeOfString(String string) {
        if (string == null) {
            return 0;
        }
        return 4 + string.length()*2;
    }

    /**
     * Returns the size in bytes of a primitive int.
     *
     * @return the size of a primitive int.
     */
    public static int sizeOfInt() {
        return 4;
    }

    /**
     * Returns the size in bytes of a primitive char.
     *
     * @return the size of a primitive char.
     */
    public static int sizeOfChar() {
        return 2;
    }

    /**
     * Returns the size in bytes of a primitive boolean.
     *
     * @return the size of a primitive boolean.
     */
    public static int sizeOfBoolean() {
        return 1;
    }

    /**
     * Returns the size in bytes of a primitive long.
     *
     * @return the size of a primitive long.
     */
    public static int sizeOfLong() {
        return 8;
    }

    /**
     * Returns the size in bytes of a primitive double.
     *
     * @return the size of a primitive double.
     */
    public static int sizeOfDouble() {
        return 8;
    }

    /**
     * Returns the size in bytes of a Date.
     *
     * @return the size of a Date.
     */
    public static int sizeOfDate() {
        return 12;
    }

    /**
     * Returns the size in bytes of a Properties object. All properties and
     * property names must be Strings.
     *
     * @param properties the Properties object to determine the size of.
     * @return the size of a Properties object.
     */
    public static int sizeOfProperties(Properties properties) {
        if (properties == null) {
            return 0;
        }
        //Base properties object
        int size = 36;
        //Add in size of each property
        Enumeration enums = properties.elements();
        while(enums.hasMoreElements()) {
            String prop = (String)enums.nextElement();
            size += sizeOfString(prop);
        }
        //Add in property names
        enums = properties.propertyNames();
        while(enums.hasMoreElements()) {
            String prop = (String)enums.nextElement();
            size += sizeOfString(prop);
        }
        return size;
    }

    /**
     * Returns the size in bytes of a Map object. All keys and
     * values <b>must be Strings</b>.
     *
     * @param map the Map object to determine the size of.
     * @return the size of the Map object.
     */
    public static int sizeOfMap(Map map) {
        if (map == null) {
            return 0;
        }
        //Base map object -- should be something around this size.
        int size = 36;
        //Add in size of each value
        Iterator iter = map.values().iterator();
        while(iter.hasNext()) {
            String value = (String)iter.next();
            size += sizeOfString(value);
        }
        //Add in each key
        iter = map.keySet().iterator();
        while(iter.hasNext()) {
            String key = (String)iter.next();
            size += sizeOfString(key);
        }
        return size;
    }
}
