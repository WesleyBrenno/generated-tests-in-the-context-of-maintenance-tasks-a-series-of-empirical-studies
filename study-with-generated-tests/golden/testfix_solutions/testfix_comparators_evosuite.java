package collections.comparators;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 *
 */
public class FixedOrderComparatorTest {

	@Test
	public void test()  throws Throwable  {
		FixedOrderComparator fixedOrderComparator0 = new FixedOrderComparator();
		fixedOrderComparator0.setUnknownObjectBehavior(1);
		int int0 = fixedOrderComparator0.compare((Object) null, (Object) null);
		assertEquals(1, fixedOrderComparator0.getUnknownObjectBehavior());
		assertTrue(fixedOrderComparator0.isLocked());
		assertEquals(0, int0);
	}

}
