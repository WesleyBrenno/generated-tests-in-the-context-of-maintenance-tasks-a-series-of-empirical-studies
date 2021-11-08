package collections.comparators;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ComparatorChainTest {

    @Test()
	public void test_9() {
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain0 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass1 = strComparableComparatorChain0.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator2 = strComparableComparatorChain0
				.reversed();
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain3 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>(
				(java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain0);
		boolean boolean4 = strComparableComparatorChain0.isLocked();
		org.junit.Assert.assertTrue("'" + boolean4 + "' != '" + false + "'", boolean4 == false);
	}

	@Test()
	public void test_8() {
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain7 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass8 = strComparableComparatorChain7.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator9 = strComparableComparatorChain7
				.reversed();
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain10 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass11 = strComparableComparatorChain10.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator12 = strComparableComparatorChain10
				.reversed();
		strComparableComparatorChain7.addComparator(strComparableComparator12, true);
		org.junit.Assert.assertNotNull(strComparableComparator12);
	}

	@Test()
	public void test_7() {
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain0 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass1 = strComparableComparatorChain0.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator2 = strComparableComparatorChain0
				.reversed();
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain3 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>(
				(java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain0);
		boolean boolean4 = strComparableComparatorChain0.isLocked();
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain6 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>(
				(java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain0, true);
		java.util.ArrayList<java.util.Comparator<java.lang.Comparable<java.lang.String>>> strComparableComparatorList19 = new java.util.ArrayList<java.util.Comparator<java.lang.Comparable<java.lang.String>>>();
		boolean boolean20 = strComparableComparatorList19
				.add((java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain0);
		org.junit.Assert.assertTrue("'" + boolean20 + "' != '" + true + "'", boolean20 == true);
	}

	@Test()
	public void test_6() {
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain0 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass1 = strComparableComparatorChain0.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator2 = strComparableComparatorChain0
				.reversed();
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain3 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>(
				(java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain0);
		boolean boolean4 = strComparableComparatorChain0.isLocked();
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain6 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>(
				(java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain0, true);
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain7 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass8 = strComparableComparatorChain7.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator9 = strComparableComparatorChain7
				.reversed();
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain10 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass11 = strComparableComparatorChain10.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator12 = strComparableComparatorChain10
				.reversed();
		strComparableComparatorChain7.addComparator(strComparableComparator12, true);
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator15 = null;
		strComparableComparatorChain7.addComparator(strComparableComparator15);
		strComparableComparatorChain7.setReverseSort((int) 'a');
		java.util.ArrayList<java.util.Comparator<java.lang.Comparable<java.lang.String>>> strComparableComparatorList19 = new java.util.ArrayList<java.util.Comparator<java.lang.Comparable<java.lang.String>>>();
		boolean boolean20 = strComparableComparatorList19
				.add((java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain0);
		boolean boolean21 = strComparableComparatorList19
				.add((java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain7);
		org.junit.Assert.assertTrue("'" + boolean21 + "' != '" + true + "'", boolean21 == true);
	}

	@Test()
	public void test_5() {
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain24 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass25 = strComparableComparatorChain24.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator26 = strComparableComparatorChain24
				.reversed();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator27 = strComparableComparatorChain24
				.reversed();
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain28 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass29 = strComparableComparatorChain28.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator30 = strComparableComparatorChain28
				.reversed();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator31 = strComparableComparatorChain28
				.reversed();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator32 = strComparableComparator27
				.thenComparing(
						(java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain28);
		org.junit.Assert.assertNotNull(strComparableComparator27);
	}

	@Test()
	public void test_4() {
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain28 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass29 = strComparableComparatorChain28.getClass();
		org.junit.Assert.assertNotNull(wildcardClass29);
	}

	@Test()
	public void test_3() {
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain28 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass29 = strComparableComparatorChain28.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator30 = strComparableComparatorChain28
				.reversed();
		org.junit.Assert.assertNotNull(strComparableComparator30);
	}

	@Test()
	public void test_2() {
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain28 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass29 = strComparableComparatorChain28.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator30 = strComparableComparatorChain28
				.reversed();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator31 = strComparableComparatorChain28
				.reversed();
		org.junit.Assert.assertNotNull(strComparableComparator31);
	}

	@Test()
	public void test_1() {
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain24 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass25 = strComparableComparatorChain24.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator26 = strComparableComparatorChain24
				.reversed();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator27 = strComparableComparatorChain24
				.reversed();
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain28 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass29 = strComparableComparatorChain28.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator30 = strComparableComparatorChain28
				.reversed();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator31 = strComparableComparatorChain28
				.reversed();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator32 = strComparableComparator27
				.thenComparing(
						(java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain28);
		org.junit.Assert.assertNotNull(strComparableComparator32);
	}

	@Test()
	public void test_0() {
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain0 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass1 = strComparableComparatorChain0.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator2 = strComparableComparatorChain0
				.reversed();
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain3 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>(
				(java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain0);
		boolean boolean4 = strComparableComparatorChain0.isLocked();
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain6 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>(
				(java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain0, true);
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain7 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass8 = strComparableComparatorChain7.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator9 = strComparableComparatorChain7
				.reversed();
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain10 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass11 = strComparableComparatorChain10.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator12 = strComparableComparatorChain10
				.reversed();
		strComparableComparatorChain7.addComparator(strComparableComparator12, true);
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator15 = null;
		strComparableComparatorChain7.addComparator(strComparableComparator15);
		strComparableComparatorChain7.setReverseSort((int) 'a');
		java.util.ArrayList<java.util.Comparator<java.lang.Comparable<java.lang.String>>> strComparableComparatorList19 = new java.util.ArrayList<java.util.Comparator<java.lang.Comparable<java.lang.String>>>();
		boolean boolean20 = strComparableComparatorList19
				.add((java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain0);
		boolean boolean21 = strComparableComparatorList19
				.add((java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain7);
		java.util.BitSet bitSet22 = null;
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain23 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>(
				(java.util.List<java.util.Comparator<java.lang.Comparable<java.lang.String>>>) strComparableComparatorList19,
				bitSet22);
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain24 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass25 = strComparableComparatorChain24.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator26 = strComparableComparatorChain24
				.reversed();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator27 = strComparableComparatorChain24
				.reversed();
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain28 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		java.lang.Class<?> wildcardClass29 = strComparableComparatorChain28.getClass();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator30 = strComparableComparatorChain28
				.reversed();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator31 = strComparableComparatorChain28
				.reversed();
		java.util.Comparator<java.lang.Comparable<java.lang.String>> strComparableComparator32 = strComparableComparator27
				.thenComparing(
						(java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain28);
		try {
			strComparableComparatorChain23.addComparator(strComparableComparator27, true);
			org.junit.Assert.fail("Expected exception of type java.lang.NullPointerException; message: null");
		} catch (java.lang.NullPointerException e) {
		}
	}
}