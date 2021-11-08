package math.genetics;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Before;

import math.exception.NotPositiveException;

public class ListPopulationTest {

	private final int POPULATION_LIMIT = 5;
	private ListPopulation lp;

	@Before
	public void setUp() {
		lp = new ElitisticListPopulation(POPULATION_LIMIT, 0);
		ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
		chromosomes.add(new DummyBinaryChromosome(new Integer[] { 0 }));
		chromosomes.add(new DummyBinaryChromosome(new Integer[] { 1 }));
		lp.setChromosomes(chromosomes);
	}	

	@Test
	public void test() {
		final int maxPopulationSize = 10;
		List<Chromosome> chromosomes = new ArrayList<Chromosome>();
		ListPopulation listPopulation = new ElitisticListPopulation(chromosomes, maxPopulationSize, 0.8);
		Chromosome a = null;
		chromosomes.add(a);
		listPopulation.addChromosome(a);
		List<Chromosome> chromosomes2 = listPopulation.getChromosomes();
		assertEquals(chromosomes, chromosomes2);
	}

}