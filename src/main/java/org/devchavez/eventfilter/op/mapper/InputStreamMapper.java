package org.devchavez.eventfilter.op.mapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import org.devchavez.eventfilter.op.InputSource;
import org.devchavez.eventfilter.op.Mapper;

public abstract class InputStreamMapper<I extends InputSource<InputStream>, O> implements Mapper<I, O> {
	
	@Override
	public List<O> apply(Supplier<I> supplier) {
		try (InputSource<InputStream> stream = supplier.get()) {
			Iterator<O> tItr = this.doMap(stream.getData());
			
			List<O> tList = new ArrayList<>();
			
			tItr.forEachRemaining(tList::add);
			
			return Collections.unmodifiableList(tList);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	protected abstract Iterator<O> doMap(InputStream stream) throws Exception;
}
