package collections.comparators;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ComparatorChainTest {

    @Test
    public void testSetComparatorTaking3ArgumentsWithPositiveAndTrue() throws Throwable {
        collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain0 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
        java.lang.Class<?> wildcardClass1 = strComparableComparatorChain0.getClass();
        java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator2 = strComparableComparatorChain0.reversed();
        int int3 = strComparableComparatorChain0.size();
        collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain5 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
        java.lang.Class<?> wildcardClass6 = strComparableComparatorChain5.getClass();
        java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator7 = strComparableComparatorChain5.reversed();
        collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain8 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
        java.lang.Class<?> wildcardClass9 = strComparableComparatorChain8.getClass();
        java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator10 = strComparableComparatorChain8.reversed();
        strComparableComparatorChain5.addComparator(strComparableComparator10, true);
        collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain13 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>(strComparableComparator10);
        try {
            strComparableComparatorChain0.setComparator((int) (byte) 100, (java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain13, true);
            org.junit.Assert.fail("Expected exception of type java.lang.IndexOutOfBoundsException; message: Index: 100, Size: 0");
        } catch (java.lang.IndexOutOfBoundsException e) {
        }
        org.junit.Assert.assertNotNull(wildcardClass1);
        org.junit.Assert.assertNotNull(strComparableComparator2);
        org.junit.Assert.assertTrue("'" + int3 + "' != '" + 0 + "'", int3 == 0);
        org.junit.Assert.assertNotNull(wildcardClass6);
        org.junit.Assert.assertNotNull(strComparableComparator7);
        org.junit.Assert.assertNotNull(wildcardClass9);
        org.junit.Assert.assertNotNull(strComparableComparator10);
    }
}