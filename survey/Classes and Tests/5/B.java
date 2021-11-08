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
		elitisticListPopulation2.setPopulationLimit((int) '4');
		java.util.Iterator<math.genetics.Chromosome> chromosomeItor5 = elitisticListPopulation2.iterator();
		java.lang.Class<?> wildcardClass6 = chromosomeItor5.getClass();
		org.junit.Assert.assertNotNull(chromosomeItor5);
	}

	@Test()
	public void test_0() {
		math.genetics.ElitisticListPopulation elitisticListPopulation2 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		elitisticListPopulation2.setPopulationLimit((int) '4');
		java.util.Iterator<math.genetics.Chromosome> chromosomeItor5 = elitisticListPopulation2.iterator();
		java.lang.Class<?> wildcardClass6 = chromosomeItor5.getClass();
		org.junit.Assert.assertNotNull(wildcardClass6);
	}
}

