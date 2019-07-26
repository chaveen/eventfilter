package org.devchavez.eventfilter.op.mapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.devchavez.eventfilter.op.InputSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * This is a factory class for deserializing XML data input stream based input source 
 */
@Component("xmlInputMappingStrategy")
public class XMLInputStreamMapperBuilder<I extends InputSource<InputStream>> {
	
	private final XmlMapper mapper;
	
	@Autowired
	public XMLInputStreamMapperBuilder(@Qualifier("xmlMapper") XmlMapper xmlMapper) {
		this.mapper = xmlMapper;
	}
	
	/**
	 * This returns a mapper to deserialize data into the given class
	 */
	public <O> XMLInputStreamMappingStrategy<O> forClass(Class<O> clazz) {
		return new XMLInputStreamMappingStrategy<>(clazz);
	}
	
	/**
	 * This inner class is generates a XML mapper for a particular class instance
	 */
	public class XMLInputStreamMappingStrategy<O> extends InputStreamMapper<I, O> {
		private final ObjectReader reader;

		private XMLInputStreamMappingStrategy(Class<O> clazz) {
			this.reader = XMLInputStreamMapperBuilder.this.mapper.reader().forType(clazz);
		}
		
		@Override
		protected Iterator<O> doMap(InputStream stream) throws IOException {
			Iterator<O> tItr = this.reader.readValues(stream);
			
			return tItr;
		}
	}


}
