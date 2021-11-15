package collections.comparators;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ComparatorChainTest {

    @Test
    public void test() throws Throwable {
        collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain0 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
        java.lang.Class<?> wildcardClass1 = strComparableComparatorChain0.getClass();
        java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator2 = strComparableComparatorChain0.reversed();
        collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain3 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>((java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain0);
        boolean boolean4 = strComparableComparatorChain0.isLocked();
        collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain6 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>((java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain0, true);
        try {
            int int9 = strComparableComparatorChain6.compare((java.lang.Comparable<java.lang.String>) "hi!", (java.lang.Comparable<java.lang.String>) "hi!");
            org.junit.Assert.fail("Expected exception of type java.lang.UnsupportedOperationException; message: ComparatorChains must contain at least one Comparator");
        } catch (java.lang.UnsupportedOperationException e) {
        }
        org.junit.Assert.assertNotNull(wildcardClass1);
        org.junit.Assert.assertNotNull(strComparableComparator2);
        org.junit.Assert.assertTrue("'" + boolean4 + "' != '" + false + "'", boolean4 == false);
    }
}