package org.devchavez.eventfilter.op.mapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.devchavez.eventfilter.op.InputSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

/**
 * This is a factory class for deserializing JSON data input stream based input source 
 */
@Component("jsonInputMappingStrategy")
public class JSONInputStreamMapperBuilder<I extends InputSource<InputStream>> {
	
	private final ObjectMapper mapper;
	
	@Autowired
	public JSONInputStreamMapperBuilder(@Qualifier("jsonMapper") ObjectMapper jsonMapper) {
		this.mapper = jsonMapper;
	}
	
	/**
	 * This returns a mapper to deserialize data into the given class
	 */
	public <O> JSONInputStreamMappingStrategy<O> forClass(Class<O> clazz) {
		return new JSONInputStreamMappingStrategy<>(clazz);
	}
	
	/**
	 * This inner class is generates a JSON mapper for a particular class instance
	 */
	public class JSONInputStreamMappingStrategy<O> extends InputStreamMapper<I, O> {
		private final ObjectReader reader;

		private JSONInputStreamMappingStrategy(Class<O> clazz) {
			this.reader = JSONInputStreamMapperBuilder.this.mapper.reader().forType(clazz);
		}
		
		@Override
		protected Iterator<O> doMap(InputStream stream) throws IOException {
			Iterator<O> tItr = this.reader.readValues(stream);
			
			return tItr;
		}
	}
}
