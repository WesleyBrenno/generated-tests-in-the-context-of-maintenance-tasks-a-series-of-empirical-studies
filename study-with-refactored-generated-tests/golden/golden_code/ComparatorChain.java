package org.apache.commons.collections4.comparators;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
public class ComparatorChain<E> implements Comparator<E>, Serializable {
    private static final long serialVersionUID = -721644942746081630L;
    private final List<Comparator<E>> comparatorChain;
    private BitSet orderingBits = null;
    private boolean isLocked = false;
    public ComparatorChain() {
        this(new ArrayList<Comparator<E>>(), new BitSet());
    }
    public ComparatorChain(final Comparator<E> comparator) {
        this(comparator, false);
    }
    public ComparatorChain(final Comparator<E> comparator, final boolean reverse) {
        comparatorChain = new ArrayList<>(1);
        comparatorChain.add(comparator);
        orderingBits = new BitSet(1);
        if (reverse == true) {
            orderingBits.set(0);
        }
    }
    public ComparatorChain(final List<Comparator<E>> list) {
        this(list, new BitSet(list.size()));
    }
    public ComparatorChain(final List<Comparator<E>> list, final BitSet bits) {
        comparatorChain = list;
        orderingBits = bits;
    }
    public void addComparator(final Comparator<E> comparator) {
        addComparator(comparator, false);
    }
    public void addComparator(final Comparator<E> comparator, final boolean reverse) {
        checkLocked();
        comparatorChain.add(comparator);
        if (reverse == true) {
            orderingBits.set(comparatorChain.size() - 1);
        }
    }
    public void setComparator(final int index, final Comparator<E> comparator) throws IndexOutOfBoundsException {
        setComparator(index, comparator, false);
    }
    public void setComparator(final int index, final Comparator<E> comparator, final boolean reverse) {
        checkLocked();
        comparatorChain.set(index,comparator);
        if (reverse == true) {
            orderingBits.set(index);
        } else {
            orderingBits.clear(index);
        }
    }
    public void setForwardSort(final int index) {
        checkLocked();
        orderingBits.clear(index);
    }
    public void setReverseSort(final int index) {
        checkLocked();
        orderingBits.set(index);
    }
    public int size() {
        return comparatorChain.size();
    }
    public boolean isLocked() {
        return isLocked;
    }
    private void checkLocked() {
        if (isLocked == true) {
            throw new UnsupportedOperationException(
                    "Comparator ordering cannot be changed after the first comparison is performed");
        }
    }
    private void checkChainIntegrity() {
        if (comparatorChain.size() == 0) {
            throw new UnsupportedOperationException("ComparatorChains must contain at least one Comparator");
        }
    }
    @Override
    public int compare(final E o1, final E o2) throws UnsupportedOperationException {
        if (isLocked == false) {
            checkChainIntegrity();
            isLocked = true;
        }
        final Iterator<Comparator<E>> comparators = comparatorChain.iterator();
        for (int comparatorIndex = 0; comparators.hasNext(); ++comparatorIndex) {
            final Comparator<? super E> comparator = comparators.next();
            int retval = comparator.compare(o1,o2);
            if (retval != 0) {
                if (orderingBits.get(comparatorIndex) == true) {
                    if (retval > 0) {
                        retval = -1;
                    } else {
                        retval = 1;
                    }
                }
                return retval;
            }
        }
        return 0;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        if (null != comparatorChain) {
            hash ^= comparatorChain.hashCode();
        }
        if (null != orderingBits) {
            hash ^= orderingBits.hashCode();
        }
        return hash;
    }
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (null == object) {
            return false;
        }
        if (object.getClass().equals(this.getClass())) {
            final ComparatorChain<?> chain = (ComparatorChain<?>) object;
            return (null == orderingBits ? null == chain.orderingBits : orderingBits.equals(chain.orderingBits)) &&
                   (null == comparatorChain ? null == chain.comparatorChain :
                                              comparatorChain.equals(chain.comparatorChain));
        }
        return false;
    }
}