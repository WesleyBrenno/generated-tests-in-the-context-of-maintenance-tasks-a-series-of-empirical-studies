package collections.comparators;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ComparatorChainTest {

    public static boolean debug = false;

    @Test
    public void test() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test27");
        collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain0 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
        collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain1 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
        strComparableComparatorChain0.addComparator((java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain1);
        java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator3 = null;
        strComparableComparatorChain1.addComparator(strComparableComparator3);
        strComparableComparatorChain1.setReverseSort((int) (short) 10);
        try {
            int int9 = strComparableComparatorChain1.compare((java.lang.Comparable<java.lang.String>) "", (java.lang.Comparable<java.lang.String>) "hi!");
            org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException; message: null");
        } catch (java.lang.NullPointerException e) {
        }
    }
}

