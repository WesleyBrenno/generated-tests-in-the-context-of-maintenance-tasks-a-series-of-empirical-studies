package math.genetics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import math.exception.util.LocalizedFormats;
import math.exception.NotPositiveException;
import math.exception.NullArgumentException;
import math.exception.NumberIsTooLargeException;
import math.exception.NumberIsTooSmallException;
public abstract class ListPopulation implements Population {
    private List<Chromosome> chromosomes;
    private int populationLimit;
    public ListPopulation(final int populationLimit) throws NotPositiveException {
        this(Collections.<Chromosome> emptyList(), populationLimit);
    }
    public ListPopulation(final List<Chromosome> chromosomes, final int populationLimit)
        throws NullArgumentException, NotPositiveException, NumberIsTooLargeException {
        if (chromosomes == null) {
            throw new NullArgumentException();
        }
        if (populationLimit <= 0) {
            throw new NotPositiveException(LocalizedFormats.POPULATION_LIMIT_NOT_POSITIVE, populationLimit);
        }
        if (chromosomes.size() > populationLimit) {
            throw new NumberIsTooLargeException(LocalizedFormats.LIST_OF_CHROMOSOMES_BIGGER_THAN_POPULATION_SIZE,
                                                chromosomes.size(), populationLimit, false);
        }
        this.populationLimit = populationLimit;
        this.chromosomes = new ArrayList<Chromosome>(populationLimit);
        this.chromosomes.addAll(chromosomes);
    }
    public void setChromosomes(final List<Chromosome> chromosomes)
        throws NullArgumentException, NumberIsTooLargeException {
        if (chromosomes == null) {
            throw new NullArgumentException();
        }
        if (chromosomes.size() > populationLimit) {
            throw new NumberIsTooLargeException(LocalizedFormats.LIST_OF_CHROMOSOMES_BIGGER_THAN_POPULATION_SIZE,
                                                chromosomes.size(), populationLimit, false);
        }
        this.chromosomes.clear();
        this.chromosomes.addAll(chromosomes);
    }
    public void addChromosomes(final Collection<Chromosome> chromosomeColl) throws NumberIsTooLargeException {
        if (chromosomes.size() + chromosomeColl.size() > populationLimit) {
            throw new NumberIsTooLargeException(LocalizedFormats.LIST_OF_CHROMOSOMES_BIGGER_THAN_POPULATION_SIZE,
                                                chromosomes.size(), populationLimit, false);
        }
        this.chromosomes.addAll(chromosomeColl);
    }
    public List<Chromosome> getChromosomes() {
        return Collections.unmodifiableList(chromosomes);
    }
    protected List<Chromosome> getChromosomeList() {
        return chromosomes;
    }
    public void addChromosome(final Chromosome chromosome) throws NumberIsTooLargeException {
        if (chromosomes.size() >= populationLimit) {
            throw new NumberIsTooLargeException(LocalizedFormats.LIST_OF_CHROMOSOMES_BIGGER_THAN_POPULATION_SIZE,
                                                chromosomes.size(), populationLimit, false);
        }
        this.chromosomes.add(chromosome);
    }
    public Chromosome getFittestChromosome() {
        Chromosome bestChromosome = this.chromosomes.get(0);
        for (Chromosome chromosome : this.chromosomes) {
            if (chromosome.compareTo(bestChromosome) > 0) {
                bestChromosome = chromosome;
            }
        }
        return bestChromosome;
    }
    public int getPopulationLimit() {
        return this.populationLimit;
    }
    public void setPopulationLimit(final int populationLimit) throws NotPositiveException, NumberIsTooSmallException {
        if (populationLimit <= 0) {
            throw new NotPositiveException(LocalizedFormats.POPULATION_LIMIT_NOT_POSITIVE, populationLimit);
        }
        if (populationLimit < chromosomes.size()) {
            throw new NumberIsTooSmallException(populationLimit, chromosomes.size(), true);
        }
        this.populationLimit = populationLimit;
    }
    public int getPopulationSize() {
        return this.chromosomes.size();
    }
    @Override
    public String toString() {
        return this.chromosomes.toString();
    }
    public Iterator<Chromosome> iterator() {
        return getChromosomes().iterator();
    }
}