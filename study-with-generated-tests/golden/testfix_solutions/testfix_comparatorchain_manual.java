package collections.comparators;

import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/**
 * Tests for ComparatorChain.
 *
 */
public class ComparatorChainTest {

    @Test
    public void test() {
    	  // -1 * Integer.MIN_VALUE is less than 0,
        // test that ComparatorChain handles this edge case correctly
        final ComparatorChain<Integer> chain = new ComparatorChain<>();
        chain.addComparator(new Comparator<Integer>() {
            @Override
            public int compare(final Integer a, final Integer b) {
                final int result = a.compareTo(b);
                if (result < 0) {
                    return Integer.MIN_VALUE;
                }
                if (result > 0) {
                    return Integer.MAX_VALUE;
                }
                return 0;
            }
        }, true);

        assertTrue(chain.compare(Integer.valueOf(4), Integer.valueOf(5)) > 0);
        assertTrue(chain.compare(Integer.valueOf(5), Integer.valueOf(4)) < 0);
        assertTrue(chain.compare(Integer.valueOf(4), Integer.valueOf(4)) == 0);
    }
}
