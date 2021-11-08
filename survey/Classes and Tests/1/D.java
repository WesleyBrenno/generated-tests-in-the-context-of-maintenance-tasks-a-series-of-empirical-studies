package collections.comparators;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FixedOrderComparatorTest {
	
	@Test()
	public void testCreatesFixedOrderComparatorTakingNoArgumentsAndCallsIsLocked() {
		collections.comparators.FixedOrderComparator fixedOrderComparator0 = new collections.comparators.FixedOrderComparator();
		boolean boolean1 = fixedOrderComparator0.isLocked();
		org.junit.Assert.assertTrue("'" + boolean1 + "' != '" + false + "'", boolean1 == false);
	}

	@Test()
	public void testCompareThrowsIllegalArgumentException() {
		collections.comparators.FixedOrderComparator fixedOrderComparator0 = new collections.comparators.FixedOrderComparator();
		boolean boolean1 = fixedOrderComparator0.isLocked();
		try {
			int int4 = fixedOrderComparator0.compare((java.lang.Object) (-1), (java.lang.Object) (-1));
			org.junit.Assert.fail(
					"Expected exception of type java.lang.IllegalArgumentException; message: Attempting to compare unknown object -1");
		} catch (java.lang.IllegalArgumentException e) {
		}
	}
}