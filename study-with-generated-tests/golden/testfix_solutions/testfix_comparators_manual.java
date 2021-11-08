package collections.comparators;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 *
 */
public class FixedOrderComparatorTest {

	@Test
	public void test() {
		try {
			Object[] emptyArray = null;
			FixedOrderComparator comparator = new FixedOrderComparator(emptyArray);
			fail("Exception was supposed to be thrown!");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

}
