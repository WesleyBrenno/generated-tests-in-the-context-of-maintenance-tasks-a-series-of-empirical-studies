package collections.comparators;

import static org.junit.Assert.fail;

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
    	 ComparatorChain<String> comparatorChain0 = new ComparatorChain<String>((Comparator<String>) null, true);
         // Undeclared exception!
         try {
           comparatorChain0.compare("S6jQ9HA[==\"e", "T <]IB");
           fail("Expecting exception: NullPointerException");

         } catch(NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            
         }
    }
}
