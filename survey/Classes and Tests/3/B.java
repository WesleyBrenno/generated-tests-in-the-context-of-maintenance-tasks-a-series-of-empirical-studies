package collections.comparators;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FixedOrderComparatorTest {
	
	@Test()
	public void test_7() {
		java.lang.Object[] objArray2 = new java.lang.Object[] { (short) 0, "hi!" };
		collections.comparators.FixedOrderComparator fixedOrderComparator3 = new collections.comparators.FixedOrderComparator(
				objArray2);
		org.junit.Assert.assertNotNull(objArray2);
	}

	@Test()
	public void test_6() {
		java.lang.Object[] objArray2 = new java.lang.Object[] { (short) 0, "hi!" };
		collections.comparators.FixedOrderComparator fixedOrderComparator3 = new collections.comparators.FixedOrderComparator(
				objArray2);
		java.lang.Object obj4 = null;
		boolean boolean5 = fixedOrderComparator3.add(obj4);
		org.junit.Assert.assertTrue("'" + boolean5 + "' != '" + true + "'", boolean5 == true);
	}

	@Test()
	public void test_5() {
		collections.comparators.FixedOrderComparator fixedOrderComparator8 = new collections.comparators.FixedOrderComparator();
		boolean boolean9 = fixedOrderComparator8.isLocked();
		org.junit.Assert.assertTrue("'" + boolean9 + "' != '" + false + "'", boolean9 == false);
	}

	@Test()
	public void test_4() {
		collections.comparators.FixedOrderComparator fixedOrderComparator8 = new collections.comparators.FixedOrderComparator();
		boolean boolean9 = fixedOrderComparator8.isLocked();
		boolean boolean11 = fixedOrderComparator8.add((java.lang.Object) 'a');
		org.junit.Assert.assertTrue("'" + boolean11 + "' != '" + true + "'", boolean11 == true);
	}

	@Test()
	public void test_3() {
		collections.comparators.FixedOrderComparator fixedOrderComparator8 = new collections.comparators.FixedOrderComparator();
		boolean boolean9 = fixedOrderComparator8.isLocked();
		boolean boolean11 = fixedOrderComparator8.add((java.lang.Object) 'a');
		int int12 = fixedOrderComparator8.getUnknownObjectBehavior();
		collections.comparators.FixedOrderComparator fixedOrderComparator14 = new collections.comparators.FixedOrderComparator();
		int int15 = fixedOrderComparator14.getUnknownObjectBehavior();
		java.lang.Object[] objArray18 = new java.lang.Object[] { (byte) 100, int12, 100L, fixedOrderComparator14,
				(short) 1, 100L };
		org.junit.Assert.assertTrue("'" + int12 + "' != '" + 2 + "'", int12 == 2);
	}

	@Test()
	public void test_2() {
		collections.comparators.FixedOrderComparator fixedOrderComparator14 = new collections.comparators.FixedOrderComparator();
		int int15 = fixedOrderComparator14.getUnknownObjectBehavior();
		org.junit.Assert.assertTrue("'" + int15 + "' != '" + 2 + "'", int15 == 2);
	}

	@Test()
	public void test_1() {
		collections.comparators.FixedOrderComparator fixedOrderComparator8 = new collections.comparators.FixedOrderComparator();
		boolean boolean9 = fixedOrderComparator8.isLocked();
		boolean boolean11 = fixedOrderComparator8.add((java.lang.Object) 'a');
		int int12 = fixedOrderComparator8.getUnknownObjectBehavior();
		collections.comparators.FixedOrderComparator fixedOrderComparator14 = new collections.comparators.FixedOrderComparator();
		int int15 = fixedOrderComparator14.getUnknownObjectBehavior();
		java.lang.Object[] objArray18 = new java.lang.Object[] { (byte) 100, int12, 100L, fixedOrderComparator14,
				(short) 1, 100L };
		collections.comparators.FixedOrderComparator fixedOrderComparator19 = new collections.comparators.FixedOrderComparator(
				objArray18);
		collections.comparators.FixedOrderComparator fixedOrderComparator20 = new collections.comparators.FixedOrderComparator(
				objArray18);
		org.junit.Assert.assertNotNull(objArray18);
	}

	@Test()
	public void test_0() {
		java.lang.Object[] objArray2 = new java.lang.Object[] { (short) 0, "hi!" };
		collections.comparators.FixedOrderComparator fixedOrderComparator3 = new collections.comparators.FixedOrderComparator(
				objArray2);
		java.lang.Object obj4 = null;
		boolean boolean5 = fixedOrderComparator3.add(obj4);
		collections.comparators.FixedOrderComparator fixedOrderComparator8 = new collections.comparators.FixedOrderComparator();
		boolean boolean9 = fixedOrderComparator8.isLocked();
		boolean boolean11 = fixedOrderComparator8.add((java.lang.Object) 'a');
		int int12 = fixedOrderComparator8.getUnknownObjectBehavior();
		collections.comparators.FixedOrderComparator fixedOrderComparator14 = new collections.comparators.FixedOrderComparator();
		int int15 = fixedOrderComparator14.getUnknownObjectBehavior();
		java.lang.Object[] objArray18 = new java.lang.Object[] { (byte) 100, int12, 100L, fixedOrderComparator14,
				(short) 1, 100L };
		collections.comparators.FixedOrderComparator fixedOrderComparator19 = new collections.comparators.FixedOrderComparator(
				objArray18);
		collections.comparators.FixedOrderComparator fixedOrderComparator20 = new collections.comparators.FixedOrderComparator(
				objArray18);
		try {
			boolean boolean21 = fixedOrderComparator3.addAsEqual((java.lang.Object) 1L, (java.lang.Object) objArray18);
			org.junit.Assert.fail(
					"Expected exception of type java.lang.IllegalArgumentException; message: 1 not known to collections.comparators.FixedOrderComparator@5d28bcd5");
		} catch (java.lang.IllegalArgumentException e) {
		}
	}
}