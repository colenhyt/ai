/**
 * $RCSfile: LongList.java,v $
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
 * A simplified List for long values. Only the bare number of methods needed
 * by Jive have been implemented so far, so additional implementation work
 * would be welcome.<p>
 *
 * The implementation uses an array for maximum speed. If the number of elements
 * grows larger than capacity, the capacity will automatically grow.
 */
public class LongList {

    long [] elements;
    int capacity;
    int size;

    /**
     * Creates a new list of long values with a default capacity of 50.
     */
    public LongList() {
        this(50);
    }

    /**
     * Creates a new list of long values with a specified initial capacity.
     *
     * @param initialCapacity a capacity to initialize the list with.
     */
    public LongList(int initialCapacity) {
        size = 0;
        capacity = initialCapacity;
        elements = new long[capacity];
    }

    /**
     * Adds a new long value to the end of the list.
     */
    public void add(long value) {
        elements[size] = value;
        size++;
        if (size == capacity) {
            capacity = capacity * 2;
            long[] newElements = new long[capacity];
            for (int i=0; i<size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
    }

    /**
     * Returns the long value at the specified index. If the index is not
     * valid, an IndexOutOfBoundException will be thrown.
     *
     * @param index the index of the value to return.
     * @return the value at the specified index.
     */
    public long get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " not valid.");
        }
        return elements[index];
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list.
     */
    public int size() {
        return size;
    }

    /**
     * Returns a new array containing the list elements.
     *
     * @return an array of the list elements.
     */
    public long[] toArray() {
        int size = this.size;
        long[] newElements = new long[size];
        for (int i=0; i<size; i++) {
            newElements[i] = elements[i];
        }
        return newElements;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (int i=0; i<this.size; i++) {
            buf.append(elements[i]).append(" ");
        }
        return buf.toString();
    }
}