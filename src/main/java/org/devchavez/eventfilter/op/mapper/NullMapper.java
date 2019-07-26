package org.devchavez.eventfilter.op.mapper;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import org.devchavez.eventfilter.op.InputSource;
import org.devchavez.eventfilter.op.Mapper;
import org.springframework.stereotype.Component;

@Component
public class NullMapper<I extends InputSource<?>, O> implements Mapper<I, O> {

	@Override
	public List<O> apply(Supplier<I> t) {
		return Collections.emptyList();
	}

}
