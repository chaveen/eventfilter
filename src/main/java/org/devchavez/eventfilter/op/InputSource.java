package org.devchavez.eventfilter.op;

/**
 * Defines an input source for specific data
 */
public interface InputSource<I> extends AutoCloseable {
	I getData();
	
	@Override
	default void close() {}
}
