package math.genetics;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import junit.framework.TestCase;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListPopulationTest {

        @Test()
	public void test_5() {
		math.genetics.ElitisticListPopulation elitisticListPopulation14 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		int int15 = elitisticListPopulation14.getPopulationLimit();
		org.junit.Assert.assertTrue("'" + int15 + "' != '" + 100 + "'", int15 == 100);
	}

	@Test()
	public void test_4() {
		math.genetics.Chromosome[] chromosomeArray19 = new math.genetics.Chromosome[] {};
		java.util.ArrayList<math.genetics.Chromosome> chromosomeList20 = new java.util.ArrayList<math.genetics.Chromosome>();
		boolean boolean21 = java.util.Collections
				.addAll((java.util.Collection<math.genetics.Chromosome>) chromosomeList20, chromosomeArray19);
		org.junit.Assert.assertNotNull(chromosomeArray19);
	}

	@Test()
	public void test_3() {
		math.genetics.Chromosome[] chromosomeArray19 = new math.genetics.Chromosome[] {};
		java.util.ArrayList<math.genetics.Chromosome> chromosomeList20 = new java.util.ArrayList<math.genetics.Chromosome>();
		boolean boolean21 = java.util.Collections
				.addAll((java.util.Collection<math.genetics.Chromosome>) chromosomeList20, chromosomeArray19);
		org.junit.Assert.assertTrue("'" + boolean21 + "' != '" + false + "'", boolean21 == false);
	}

	@Test()
	public void test_2() {
		math.genetics.ElitisticListPopulation elitisticListPopulation2 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		int int3 = elitisticListPopulation2.getPopulationLimit();
		math.genetics.ElitisticListPopulation elitisticListPopulation6 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		math.genetics.Chromosome[] chromosomeArray7 = new math.genetics.Chromosome[] {};
		java.util.ArrayList<math.genetics.Chromosome> chromosomeList8 = new java.util.ArrayList<math.genetics.Chromosome>();
		boolean boolean9 = java.util.Collections
				.addAll((java.util.Collection<math.genetics.Chromosome>) chromosomeList8, chromosomeArray7);
		elitisticListPopulation6.setChromosomes((java.util.List<math.genetics.Chromosome>) chromosomeList8);
		elitisticListPopulation2.setChromosomes((java.util.List<math.genetics.Chromosome>) chromosomeList8);
		math.genetics.ElitisticListPopulation elitisticListPopulation14 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		int int15 = elitisticListPopulation14.getPopulationLimit();
		math.genetics.ElitisticListPopulation elitisticListPopulation18 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		math.genetics.Chromosome[] chromosomeArray19 = new math.genetics.Chromosome[] {};
		java.util.ArrayList<math.genetics.Chromosome> chromosomeList20 = new java.util.ArrayList<math.genetics.Chromosome>();
		boolean boolean21 = java.util.Collections
				.addAll((java.util.Collection<math.genetics.Chromosome>) chromosomeList20, chromosomeArray19);
		elitisticListPopulation18.setChromosomes((java.util.List<math.genetics.Chromosome>) chromosomeList20);
		elitisticListPopulation14.setChromosomes((java.util.List<math.genetics.Chromosome>) chromosomeList20);
		elitisticListPopulation2.addChromosomes((java.util.Collection<math.genetics.Chromosome>) chromosomeList20);
		math.genetics.Chromosome chromosome25 = elitisticListPopulation2.getFittestChromosome();
		org.junit.Assert.assertNull(chromosome25);
	}

	@Test()
	public void test_1() {
		math.genetics.ElitisticListPopulation elitisticListPopulation2 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		int int3 = elitisticListPopulation2.getPopulationLimit();
		math.genetics.ElitisticListPopulation elitisticListPopulation6 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		math.genetics.Chromosome[] chromosomeArray7 = new math.genetics.Chromosome[] {};
		java.util.ArrayList<math.genetics.Chromosome> chromosomeList8 = new java.util.ArrayList<math.genetics.Chromosome>();
		boolean boolean9 = java.util.Collections
				.addAll((java.util.Collection<math.genetics.Chromosome>) chromosomeList8, chromosomeArray7);
		elitisticListPopulation6.setChromosomes((java.util.List<math.genetics.Chromosome>) chromosomeList8);
		elitisticListPopulation2.setChromosomes((java.util.List<math.genetics.Chromosome>) chromosomeList8);
		math.genetics.ElitisticListPopulation elitisticListPopulation14 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		int int15 = elitisticListPopulation14.getPopulationLimit();
		math.genetics.ElitisticListPopulation elitisticListPopulation18 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		math.genetics.Chromosome[] chromosomeArray19 = new math.genetics.Chromosome[] {};
		java.util.ArrayList<math.genetics.Chromosome> chromosomeList20 = new java.util.ArrayList<math.genetics.Chromosome>();
		boolean boolean21 = java.util.Collections
				.addAll((java.util.Collection<math.genetics.Chromosome>) chromosomeList20, chromosomeArray19);
		elitisticListPopulation18.setChromosomes((java.util.List<math.genetics.Chromosome>) chromosomeList20);
		elitisticListPopulation14.setChromosomes((java.util.List<math.genetics.Chromosome>) chromosomeList20);
		elitisticListPopulation2.addChromosomes((java.util.Collection<math.genetics.Chromosome>) chromosomeList20);
		math.genetics.Chromosome chromosome25 = elitisticListPopulation2.getFittestChromosome();
		double double26 = elitisticListPopulation2.getElitismRate();
		org.junit.Assert.assertTrue("'" + double26 + "' != '" + 0.0d + "'", double26 == 0.0d);
	}

	@Test()
	public void test_0() {
		math.genetics.ElitisticListPopulation elitisticListPopulation2 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		int int3 = elitisticListPopulation2.getPopulationLimit();
		math.genetics.ElitisticListPopulation elitisticListPopulation6 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		math.genetics.Chromosome[] chromosomeArray7 = new math.genetics.Chromosome[] {};
		java.util.ArrayList<math.genetics.Chromosome> chromosomeList8 = new java.util.ArrayList<math.genetics.Chromosome>();
		boolean boolean9 = java.util.Collections
				.addAll((java.util.Collection<math.genetics.Chromosome>) chromosomeList8, chromosomeArray7);
		elitisticListPopulation6.setChromosomes((java.util.List<math.genetics.Chromosome>) chromosomeList8);
		elitisticListPopulation2.setChromosomes((java.util.List<math.genetics.Chromosome>) chromosomeList8);
		math.genetics.ElitisticListPopulation elitisticListPopulation14 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		int int15 = elitisticListPopulation14.getPopulationLimit();
		math.genetics.ElitisticListPopulation elitisticListPopulation18 = new math.genetics.ElitisticListPopulation(100,
				0.0d);
		math.genetics.Chromosome[] chromosomeArray19 = new math.genetics.Chromosome[] {};
		java.util.ArrayList<math.genetics.Chromosome> chromosomeList20 = new java.util.ArrayList<math.genetics.Chromosome>();
		boolean boolean21 = java.util.Collections
				.addAll((java.util.Collection<math.genetics.Chromosome>) chromosomeList20, chromosomeArray19);
		elitisticListPopulation18.setChromosomes((java.util.List<math.genetics.Chromosome>) chromosomeList20);
		elitisticListPopulation14.setChromosomes((java.util.List<math.genetics.Chromosome>) chromosomeList20);
		elitisticListPopulation2.addChromosomes((java.util.Collection<math.genetics.Chromosome>) chromosomeList20);
		math.genetics.Chromosome chromosome25 = elitisticListPopulation2.getFittestChromosome();
		double double26 = elitisticListPopulation2.getElitismRate();
		try {
			elitisticListPopulation2.setPopulationLimit((int) (byte) 0);
			org.junit.Assert.fail(
					"Expected exception of type math.exception.NotPositiveException; message: population limit has to be positive");
		} catch (math.exception.NotPositiveException e) {
		}
	}
}

