package math.genetics;

import math.exception.MathIllegalArgumentException;

/**
 * Algorithm used to select a chromosome pair from a population.
 *
 */
public interface SelectionPolicy {
    /**
     * Select two chromosomes from the population.
     * @param population the population from which the chromosomes are choosen.
     * @return the selected chromosomes.
     * @throws MathIllegalArgumentException if the population is not compatible with this {@link SelectionPolicy}
     */
    ChromosomePair select(Population population) throws MathIllegalArgumentException;
}
