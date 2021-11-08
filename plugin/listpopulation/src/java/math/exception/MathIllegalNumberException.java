package math.exception;

import math.exception.util.Localizable;

/**
 * Base class for exceptions raised by a wrong number.
 * This class is not intended to be instantiated directly: it should serve
 * as a base class to create all the exceptions that are raised because some
 * precondition is violated by a number argument.
 *
 */
public class MathIllegalNumberException extends MathIllegalArgumentException {
    /** Serializable version Id. */
    private static final long serialVersionUID = -7447085893598031110L;
    /** Requested. */
    private final Number argument;

    /**
     * Construct an exception.
     *
     * @param pattern Localizable pattern.
     * @param wrong Wrong number.
     * @param arguments Arguments.
     */
    protected MathIllegalNumberException(Localizable pattern,
                                         Number wrong,
                                         Object ... arguments) {
        super(pattern, wrong, arguments);
        argument = wrong;
    }

    /**
     * @return the requested value.
     */
    public Number getArgument() {
        return argument;
    }
}
