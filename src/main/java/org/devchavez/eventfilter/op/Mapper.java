package org.devchavez.eventfilter.op;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This defines a retrieval and mapping of data from a input source to a list of output type
 */
public interface Mapper<I extends InputSource<?>, O> extends Function<Supplier<I>, List<O>> {

}
