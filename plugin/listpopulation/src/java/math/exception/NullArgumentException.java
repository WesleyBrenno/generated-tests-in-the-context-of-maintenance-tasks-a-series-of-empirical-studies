package math.exception;

import math.exception.util.Localizable;
import math.exception.util.LocalizedFormats;

/**
 * All conditions checks that fail due to a {@code null} argument must throw
 * this exception.
 * This class is meant to signal a precondition violation ("null is an illegal
 * argument") and so does not extend the standard {@code NullPointerException}.
 * Propagation of {@code NullPointerException} from within Commons-Math is
 * construed to be a bug.
 *
 */
public class NullArgumentException extends MathIllegalArgumentException {
    /** Serializable version Id. */
    private static final long serialVersionUID = -6024911025449780478L;

    /**
     * Default constructor.
     */
    public NullArgumentException() {
        this(LocalizedFormats.NULL_NOT_ALLOWED);
    }
    /**
     * @param pattern Message pattern providing the specific context of
     * the error.
     * @param arguments Values for replacing the placeholders in {@code pattern}.
     */
    public NullArgumentException(Localizable pattern,
                                 Object ... arguments) {
        super(pattern, arguments);
    }
}
