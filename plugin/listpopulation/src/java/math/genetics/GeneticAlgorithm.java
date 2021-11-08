package math.genetics;

import java.util.ArrayList;
import java.util.List;

import math.exception.DimensionMismatchException;
import math.exception.MathIllegalArgumentException;
import math.exception.OutOfRangeException;
import math.exception.util.LocalizedFormats;
import math.random.JDKRandomGenerator;
import math.random.RandomGenerator;

/**
 * Implementation of a genetic algorithm. All factors that govern the operation
 * of the algorithm can be configured for a specific problem.
 *
 */
public class GeneticAlgorithm {

    /**
     * Static random number generator shared by GA implementation classes. Set the randomGenerator seed to get
     * reproducible results. Use {@link #setRandomGenerator(RandomGenerator)} to supply an alternative to the default
     * JDK-provided PRNG.
     */
    //@GuardedBy("this")
    private static RandomGenerator randomGenerator = new JDKRandomGenerator();

    /** the crossover policy used by the algorithm. */
    private final CrossoverPolicy crossoverPolicy;

    /** the rate of crossover for the algorithm. */
    private final double crossoverRate;

    /** the mutation policy used by the algorithm. */
    private final MutationPolicy mutationPolicy;

    /** the rate of mutation for the algorithm. */
    private final double mutationRate;

    /** the selection policy used by the algorithm. */
    private final SelectionPolicy selectionPolicy;

    /** the number of generations evolved to reach {@link StoppingCondition} in the last run. */
    private int generationsEvolved = 0;

    /**
     * Create a new genetic algorithm.
     * @param crossoverPolicy The {@link CrossoverPolicy}
     * @param crossoverRate The crossover rate as a percentage (0-1 inclusive)
     * @param mutationPolicy The {@link MutationPolicy}
     * @param mutationRate The mutation rate as a percentage (0-1 inclusive)
     * @param selectionPolicy The {@link SelectionPolicy}
     * @throws OutOfRangeException if the crossover or mutation rate is outside the [0, 1] range
     */
    public GeneticAlgorithm(final CrossoverPolicy crossoverPolicy,
                            final double crossoverRate,
                            final MutationPolicy mutationPolicy,
                            final double mutationRate,
                            final SelectionPolicy selectionPolicy) throws OutOfRangeException {

        if (crossoverRate < 0 || crossoverRate > 1) {
            throw new OutOfRangeException(LocalizedFormats.CROSSOVER_RATE,
                                          crossoverRate, 0, 1);
        }
        if (mutationRate < 0 || mutationRate > 1) {
            throw new OutOfRangeException(LocalizedFormats.MUTATION_RATE,
                                          mutationRate, 0, 1);
        }
        this.crossoverPolicy = crossoverPolicy;
        this.crossoverRate = crossoverRate;
        this.mutationPolicy = mutationPolicy;
        this.mutationRate = mutationRate;
        this.selectionPolicy = selectionPolicy;
    }

    /**
     * Set the (static) random generator.
     *
     * @param random random generator
     */
    public static synchronized void setRandomGenerator(final RandomGenerator random) {
        randomGenerator = random;
    }

    /**
     * Returns the (static) random generator.
     *
     * @return the static random generator shared by GA implementation classes
     */
    public static synchronized RandomGenerator getRandomGenerator() {
        return randomGenerator;
    }

    /**
     * Evolve the given population. Evolution stops when the stopping condition
     * is satisfied. Updates the {@link #getGenerationsEvolved() generationsEvolved}
     * property with the number of generations evolved before the StoppingCondition
     * is satisfied.
     *
     * @param initial the initial, seed population.
     * @param condition the stopping condition used to stop evolution.
     * @return the population that satisfies the stopping condition.
     */
    public Population evolve(final Population initial, final StoppingCondition condition) {
        Population current = initial;
        generationsEvolved = 0;
        while (!condition.isSatisfied(current)) {
            current = nextGeneration(current);
            generationsEvolved++;
        }
        return current;
    }

    /**
     * Evolve the given population into the next generation.
     * <p>
     * <ol>
     *  <li>Get nextGeneration population to fill from <code>current</code>
     *      generation, using its nextGeneration method</li>
     *  <li>Loop until new generation is filled:</li>
     *  <ul><li>Apply configured SelectionPolicy to select a pair of parents
     *          from <code>current</code></li>
     *      <li>With probability = {@link #getCrossoverRate()}, apply
     *          configured {@link CrossoverPolicy} to parents</li>
     *      <li>With probability = {@link #getMutationRate()}, apply
     *          configured {@link MutationPolicy} to each of the offspring</li>
     *      <li>Add offspring individually to nextGeneration,
     *          space permitting</li>
     *  </ul>
     *  <li>Return nextGeneration</li>
     * </ol>
     *
     * @param current the current population.
     * @return the population for the next generation.
     */
    public Population nextGeneration(final Population current) {
        Population nextGeneration = current.nextGeneration();

        RandomGenerator randGen = getRandomGenerator();

        while (nextGeneration.getPopulationSize() < nextGeneration.getPopulationLimit()) {
            // select parent chromosomes
            ChromosomePair pair = getSelectionPolicy().select(current);

            // crossover?
            if (randGen.nextDouble() < getCrossoverRate()) {
                // apply crossover policy to create two offspring
                pair = getCrossoverPolicy().crossover(pair.getFirst(), pair.getSecond());
            }

            // mutation?
            if (randGen.nextDouble() < getMutationRate()) {
                // apply mutation policy to the chromosomes
                pair = new ChromosomePair(
                    getMutationPolicy().mutate(pair.getFirst()),
                    getMutationPolicy().mutate(pair.getSecond()));
            }

            // add the first chromosome to the population
            nextGeneration.addChromosome(pair.getFirst());
            // is there still a place for the second chromosome?
            if (nextGeneration.getPopulationSize() < nextGeneration.getPopulationLimit()) {
                // add the second chromosome to the population
                nextGeneration.addChromosome(pair.getSecond());
            }
        }

        return nextGeneration;
    }

    /**
     * Returns the crossover policy.
     * @return crossover policy
     */
    public CrossoverPolicy getCrossoverPolicy() {
        return crossoverPolicy;
    }

    /**
     * Returns the crossover rate.
     * @return crossover rate
     */
    public double getCrossoverRate() {
        return crossoverRate;
    }

    /**
     * Returns the mutation policy.
     * @return mutation policy
     */
    public MutationPolicy getMutationPolicy() {
        return mutationPolicy;
    }

    /**
     * Returns the mutation rate.
     * @return mutation rate
     */
    public double getMutationRate() {
        return mutationRate;
    }

    /**
     * Returns the selection policy.
     * @return selection policy
     */
    public SelectionPolicy getSelectionPolicy() {
        return selectionPolicy;
    }

    /**
     * Returns the number of generations evolved to reach {@link StoppingCondition} in the last run.
     *
     * @return number of generations evolved
     * @since 2.1
     */
    public int getGenerationsEvolved() {
        return generationsEvolved;
    }

}

class UniformCrossover<T> implements CrossoverPolicy {

    /** The mixing ratio. */
    private final double ratio;

    /**
     * Creates a new {@link UniformCrossover} policy using the given mixing ratio.
     *
     * @param ratio the mixing ratio
     * @throws OutOfRangeException if the mixing ratio is outside the [0, 1] range
     */
    public UniformCrossover(final double ratio) throws OutOfRangeException {
        if (ratio < 0.0d || ratio > 1.0d) {
            throw new OutOfRangeException(LocalizedFormats.CROSSOVER_RATE, ratio, 0.0d, 1.0d);
        }
        this.ratio = ratio;
    }

    /**
     * Returns the mixing ratio used by this {@link CrossoverPolicy}.
     *
     * @return the mixing ratio
     */
    public double getRatio() {
        return ratio;
    }

    /**
     * {@inheritDoc}
     *
     * @throws MathIllegalArgumentException iff one of the chromosomes is
     *   not an instance of {@link AbstractListChromosome}
     * @throws DimensionMismatchException if the length of the two chromosomes is different
     */
    @SuppressWarnings("unchecked")
    public ChromosomePair crossover(final Chromosome first, final Chromosome second)
        throws DimensionMismatchException, MathIllegalArgumentException {

        if (!(first instanceof AbstractListChromosome<?> && second instanceof AbstractListChromosome<?>)) {
            throw new MathIllegalArgumentException(LocalizedFormats.INVALID_FIXED_LENGTH_CHROMOSOME);
        }
        return mate((AbstractListChromosome<T>) first, (AbstractListChromosome<T>) second);
    }

    /**
     * Helper for {@link #crossover(Chromosome, Chromosome)}. Performs the actual crossover.
     *
     * @param first the first chromosome
     * @param second the second chromosome
     * @return the pair of new chromosomes that resulted from the crossover
     * @throws DimensionMismatchException if the length of the two chromosomes is different
     */
    private ChromosomePair mate(final AbstractListChromosome<T> first,
                                final AbstractListChromosome<T> second) throws DimensionMismatchException {
        final int length = first.getLength();
        if (length != second.getLength()) {
            throw new DimensionMismatchException(second.getLength(), length);
        }

        // array representations of the parents
        final List<T> parent1Rep = first.getRepresentation();
        final List<T> parent2Rep = second.getRepresentation();
        // and of the children
        final List<T> child1Rep = new ArrayList<T>(first.getLength());
        final List<T> child2Rep = new ArrayList<T>(second.getLength());

        final RandomGenerator random = GeneticAlgorithm.getRandomGenerator();

        for (int index = 0; index < length; index++) {

            if (random.nextDouble() < ratio) {
                // swap the bits -> take other parent
                child1Rep.add(parent2Rep.get(index));
                child2Rep.add(parent1Rep.get(index));
            } else {
                child1Rep.add(parent1Rep.get(index));
                child2Rep.add(parent2Rep.get(index));
            }
        }

        return new ChromosomePair(first.newFixedLengthChromosome(child1Rep),
                                  second.newFixedLengthChromosome(child2Rep));
    }
}
