package collections.comparators;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FixedOrderComparatorTest {

    public static boolean debug = false;

    @Test
    public void test() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test079");
        collections.comparators.FixedOrderComparator fixedOrderComparator0 = new collections.comparators.FixedOrderComparator();
        boolean boolean1 = fixedOrderComparator0.isLocked();
        fixedOrderComparator0.setUnknownObjectBehavior((int) (byte) 1);
        int int6 = fixedOrderComparator0.compare((java.lang.Object) "hi!", (java.lang.Object) "");
        boolean boolean7 = fixedOrderComparator0.isLocked();
        try {
            fixedOrderComparator0.setUnknownObjectBehavior((int) (short) -1);
            org.junit.Assert.fail("Expected exception of type java.lang.UnsupportedOperationException; message: Cannot modify a FixedOrderComparator after a comparison");
        } catch (java.lang.UnsupportedOperationException e) {
        }
        org.junit.Assert.assertTrue("'" + boolean1 + "' != '" + false + "'", boolean1 == false);
        org.junit.Assert.assertTrue("'" + int6 + "' != '" + 0 + "'", int6 == 0);
        org.junit.Assert.assertTrue("'" + boolean7 + "' != '" + true + "'", boolean7 == true);
    }
}

