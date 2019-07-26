package org.devchavez.eventfilter.op.mapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.devchavez.eventfilter.op.InputSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * This is a factory class for deserializing CSV data input stream based input source 
 */
@Component("csvInputMappingStrategy")
public class CSVInputStreamMapperBuilder<I extends InputSource<InputStream>> {
	
	private final CsvMapper mapper;
	
	@Autowired
	public CSVInputStreamMapperBuilder(@Qualifier("csvMapper") CsvMapper csvMapper) {
		this.mapper = csvMapper;
	}
	
	/**
	 * This returns a mapper to deserialize data into the given class
	 */
	public <O> CSVInputStreamMappingStrategy<O> forClass(Class<O> clazz) {
		return new CSVInputStreamMappingStrategy<>(clazz);
	}
	
	/**
	 * This returns a mapper to deserialize data into the given class with a specified CSV data format
	 */
	public <O> CSVInputStreamMappingStrategy<O> forClass(Class<O> clazz, CsvSchema schema) {
		return new CSVInputStreamMappingStrategy<>(clazz);
	}
	
	/**
	 * This inner class is generates a CSV mapper for a particular class instance
	 */
	public class CSVInputStreamMappingStrategy<O> extends InputStreamMapper<I, O> {
		private final ObjectReader reader;

		private CSVInputStreamMappingStrategy(Class<O> targetClass) {
			// Using is the default CSV schema
			this(targetClass, CSVInputStreamMapperBuilder.this.mapper
				.typedSchemaFor(targetClass)
				.withHeader()
				.withColumnSeparator(','));
		}
		
		private CSVInputStreamMappingStrategy(Class<O> targetClass, CsvSchema schema) {
			this.reader = CSVInputStreamMapperBuilder.this.mapper.reader()
					.forType(targetClass)
					.with(schema);
		}
		
		@Override
		protected Iterator<O> doMap(InputStream stream) throws IOException {
			Iterator<O> tItr = this.reader.readValues(stream);
			
			return tItr;
		}
	}
}
