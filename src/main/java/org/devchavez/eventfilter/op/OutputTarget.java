package org.devchavez.eventfilter.op;

/**
 * Defines an output target for specific data
 */
public interface OutputTarget<I> extends AutoCloseable {
	
	void write(I input);
	
	boolean hasWritten();
	
	@Override
	default void close() {}
}
