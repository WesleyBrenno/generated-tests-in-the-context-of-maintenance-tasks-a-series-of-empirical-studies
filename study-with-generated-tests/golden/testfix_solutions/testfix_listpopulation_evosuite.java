package math.genetics;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import math.exception.NumberIsTooLargeException;

public class ListPopulationTest {

	@Test
	public void test() throws Throwable {
		ElitisticListPopulation elitisticListPopulation0 = new ElitisticListPopulation(1, 1);
		List<Chromosome> list0 = elitisticListPopulation0.getChromosomes();
		LinkedList<Integer> linkedList0 = new LinkedList<Integer>();
		DummyListChromosome dummyListChromosome0 = new DummyListChromosome((List<Integer>) linkedList0);
		elitisticListPopulation0.setChromosomes(list0);
		elitisticListPopulation0.addChromosome(dummyListChromosome0);
		// Undeclared exception!
		try {
			elitisticListPopulation0.addChromosome(dummyListChromosome0);
			fail("Expecting exception: NumberIsTooLargeException");
		
		} catch(NumberIsTooLargeException e) {
			//
			// no message in exception (getMessage() returned null)
			//
		}
	}

}