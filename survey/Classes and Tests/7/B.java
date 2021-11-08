package collections.comparators;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ComparatorChainTest {

	@Test()
	public void test_1() {
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain3 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		int int4 = strComparableComparatorChain3.size();
		org.junit.Assert.assertTrue("'" + int4 + "' != '" + 0 + "'", int4 == 0);
	}

	@Test()
	public void test_0() {
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain0 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		int int1 = strComparableComparatorChain0.size();
		collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>> strComparableComparatorChain3 = new collections.comparators.ComparatorChain<java.lang.Comparable<java.lang.String>>();
		int int4 = strComparableComparatorChain3.size();
		try {
			strComparableComparatorChain0.setComparator((-1),
					(java.util.Comparator<java.lang.Comparable<java.lang.String>>) strComparableComparatorChain3,
					false);
			org.junit.Assert.fail("Expected exception of type java.lang.ArrayIndexOutOfBoundsException; message: -1");
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
		}
	}
}