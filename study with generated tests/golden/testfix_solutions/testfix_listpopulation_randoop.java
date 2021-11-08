package math.genetics;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListPopulationTest {

    public static boolean debug = false;

    @Test
    public void test() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest2.test001");
        math.genetics.ElitisticListPopulation elitisticListPopulation2 = new math.genetics.ElitisticListPopulation(100, 0.0d);
        java.lang.String str3 = elitisticListPopulation2.toString();
        math.genetics.Chromosome[] chromosomeArray4 = new math.genetics.Chromosome[] {};
        java.util.ArrayList<math.genetics.Chromosome> chromosomeList5 = new java.util.ArrayList<math.genetics.Chromosome>();
        boolean boolean6 = java.util.Collections.addAll((java.util.Collection<math.genetics.Chromosome>) chromosomeList5, chromosomeArray4);
        elitisticListPopulation2.setChromosomes((java.util.List<math.genetics.Chromosome>) chromosomeList5);
        elitisticListPopulation2.setElitismRate(0.0d);
        try {
            math.genetics.Chromosome chromosome10 = elitisticListPopulation2.getFittestChromosome();
            org.junit.Assert.fail("Expected exception of type java.lang.IndexOutOfBoundsException; message: Index: 0, Size: 0");
        } catch (java.lang.IndexOutOfBoundsException e) {
        }
        org.junit.Assert.assertTrue("'" + str3 + "' != '" + "[]" + "'", str3.equals("[]"));
        org.junit.Assert.assertNotNull(chromosomeArray4);
        org.junit.Assert.assertTrue("'" + boolean6 + "' != '" + false + "'", boolean6 == false);
    }
}

