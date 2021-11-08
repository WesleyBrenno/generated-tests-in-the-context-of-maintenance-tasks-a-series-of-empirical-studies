package math.genetics;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import junit.framework.TestCase;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListPopulationTest {

        @Test()
	public void test_1() {
		math.genetics.ElitisticListPopulation elitisticListPopulation2 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		java.lang.String str3 = elitisticListPopulation2.toString();
		org.junit.Assert.assertTrue("'" + str3 + "' != '" + "[]" + "'", str3.equals("[]"));
	}

	@Test()
	public void test_0() {
		math.genetics.ElitisticListPopulation elitisticListPopulation2 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		java.lang.String str3 = elitisticListPopulation2.toString();
		try {
			elitisticListPopulation2.setElitismRate((double) (byte) -1);
			org.junit.Assert
					.fail("Expected exception of type math.exception.OutOfRangeException; message: elitism rate (-1)");
		} catch (math.exception.OutOfRangeException e) {
		}
	}
}

