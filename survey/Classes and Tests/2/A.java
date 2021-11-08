package collections.comparators;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FixedOrderComparatorTest {
	
	@Test
	public void test()  {
		collections.comparators.FixedOrderComparator fixedOrderComparator1 = new collections.comparators.FixedOrderComparator();
		boolean boolean2 = fixedOrderComparator1.isLocked();
		boolean boolean4 = fixedOrderComparator1.add((java.lang.Object) 'a');
		int int5 = fixedOrderComparator1.getUnknownObjectBehavior();
		collections.comparators.FixedOrderComparator fixedOrderComparator7 = new collections.comparators.FixedOrderComparator();
		int int8 = fixedOrderComparator7.getUnknownObjectBehavior();
		java.lang.Object[] objArray11 = new java.lang.Object[] { (byte) 100, int5, 100L, fixedOrderComparator7,
				(short) 1, 100L };
		collections.comparators.FixedOrderComparator fixedOrderComparator12 = new collections.comparators.FixedOrderComparator(
				objArray11);
		collections.comparators.FixedOrderComparator fixedOrderComparator13 = new collections.comparators.FixedOrderComparator(
				objArray11);
		collections.comparators.FixedOrderComparator fixedOrderComparator15 = new collections.comparators.FixedOrderComparator();
		boolean boolean16 = fixedOrderComparator15.isLocked();
		boolean boolean18 = fixedOrderComparator15.add((java.lang.Object) 'a');
		int int19 = fixedOrderComparator15.getUnknownObjectBehavior();
		collections.comparators.FixedOrderComparator fixedOrderComparator21 = new collections.comparators.FixedOrderComparator();
		int int22 = fixedOrderComparator21.getUnknownObjectBehavior();
		java.lang.Object[] objArray25 = new java.lang.Object[] { (byte) 100, int19, 100L, fixedOrderComparator21,
				(short) 1, 100L };
		collections.comparators.FixedOrderComparator fixedOrderComparator26 = new collections.comparators.FixedOrderComparator(
				objArray25);
		java.lang.Object obj27 = null;
		boolean boolean28 = fixedOrderComparator26.add(obj27);
		int int29 = fixedOrderComparator26.getUnknownObjectBehavior();
		try {
			boolean boolean31 = fixedOrderComparator13.addAsEqual((java.lang.Object) fixedOrderComparator26,
					(java.lang.Object) "hi!");
			org.junit.Assert.fail(
					"Expected exception of type java.lang.IllegalArgumentException; message: collections.comparators.FixedOrderComparator@7b44b63d not known to collections.comparators.FixedOrderComparator@4a699efa");
		} catch (java.lang.IllegalArgumentException e) {
		}
		org.junit.Assert.assertTrue("'" + boolean2 + "' != '" + false + "'", boolean2 == false);
		org.junit.Assert.assertTrue("'" + boolean4 + "' != '" + true + "'", boolean4 == true);
		org.junit.Assert.assertTrue("'" + int5 + "' != '" + 2 + "'", int5 == 2);
		org.junit.Assert.assertTrue("'" + int8 + "' != '" + 2 + "'", int8 == 2);
		org.junit.Assert.assertNotNull(objArray11);
		org.junit.Assert.assertTrue("'" + boolean16 + "' != '" + false + "'", boolean16 == false);
		org.junit.Assert.assertTrue("'" + boolean18 + "' != '" + true + "'", boolean18 == true);
		org.junit.Assert.assertTrue("'" + int19 + "' != '" + 2 + "'", int19 == 2);
		org.junit.Assert.assertTrue("'" + int22 + "' != '" + 2 + "'", int22 == 2);
		org.junit.Assert.assertNotNull(objArray25);
		org.junit.Assert.assertTrue("'" + boolean28 + "' != '" + true + "'", boolean28 == true);
		org.junit.Assert.assertTrue("'" + int29 + "' != '" + 2 + "'", int29 == 2);
	}
}