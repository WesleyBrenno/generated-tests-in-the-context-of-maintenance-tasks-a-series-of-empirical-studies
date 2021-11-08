package math.genetics;

import math.exception.MathIllegalArgumentException;
import math.exception.util.Localizable;

/**
 * Exception indicating that the representation of a chromosome is not valid.
 *
 */
public class InvalidRepresentationException extends MathIllegalArgumentException {

    /** Serialization version id */
    private static final long serialVersionUID = 1L;

    /**
     * Construct an InvalidRepresentationException with a specialized message.
     *
     * @param pattern Message pattern.
     * @param args Arguments.
     */
    public InvalidRepresentationException(Localizable pattern, Object ... args) {
       super(pattern, args);
    }

}
