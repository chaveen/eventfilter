package org.devchavez.eventfilter.op;

import java.util.function.BiConsumer;
import java.util.stream.Collector;

/**
 * This defines a writer of input data to output target
 */
public interface Writer<I, O extends OutputTarget<?>> extends BiConsumer<O, I> {

	/**
	 * This method creates a sink logic to write all input data into a single output target
	 * and finally safely closing it
	 */
	default Collector<I, O, Object> asCollector(O target) {
		return Collector.of(() -> target, this, (a, b) -> a, a -> { target.close(); return a; });
	}
}
