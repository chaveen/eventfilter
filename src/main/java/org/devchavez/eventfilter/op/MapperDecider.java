package org.devchavez.eventfilter.op;

/**
 * This defines a decider logic for mapper based on the input source
 */
public interface MapperDecider<I extends InputSource<?>, O> {

	Mapper<I, O> decide(I input);
}
