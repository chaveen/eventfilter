package org.devchavez.eventfilter.event.op;

import org.devchavez.eventfilter.event.vo.Event;
import org.devchavez.eventfilter.op.Mapper;
import org.devchavez.eventfilter.op.MapperDecider;
import org.devchavez.eventfilter.op.mapper.CSVInputStreamMapperBuilder;
import org.devchavez.eventfilter.op.mapper.JSONInputStreamMapperBuilder;
import org.devchavez.eventfilter.op.mapper.NullMapper;
import org.devchavez.eventfilter.op.mapper.XMLInputStreamMapperBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * This is responsible of deciding which Mapper instance should be used based on the EventSource 
 *
 */
@Component("eventMapperDecider")
public class EventMapperStrategyDecider implements MapperDecider<EventSource, Event> {
	
	private final JSONInputStreamMapperBuilder<EventSource>
			.JSONInputStreamMappingStrategy<Event> jsonInputStreamMapper;
	private final CSVInputStreamMapperBuilder<EventSource>
			.CSVInputStreamMappingStrategy<Event> csvInputStreamMapper;
	private final XMLInputStreamMapperBuilder<EventSource>
			.XMLInputStreamMappingStrategy<Event> xmlInputStreamMapper;
	private final NullMapper<EventSource, Event> nullMappingStrategy = new NullMapper<>();

	@Autowired
	public EventMapperStrategyDecider(
			@Qualifier("jsonInputMappingStrategy") JSONInputStreamMapperBuilder<EventSource> jsonInputStreamMapperBuilder,
			@Qualifier("csvInputMappingStrategy") CSVInputStreamMapperBuilder<EventSource> csvInputStreamMapperBuilder,
			@Qualifier("xmlInputMappingStrategy") XMLInputStreamMapperBuilder<EventSource> xmlInputStreamMapperBuilder) {
		
		// We have to create mappers for Event class specifically
		this.jsonInputStreamMapper = jsonInputStreamMapperBuilder.forClass(Event.class);
		this.csvInputStreamMapper = csvInputStreamMapperBuilder.forClass(Event.class);
		this.xmlInputStreamMapper = xmlInputStreamMapperBuilder.forClass(Event.class);
	}
	
	/**
	 * We decide which Mapper to use based on the EventSource file extension
	 */
	@Override
	public Mapper<EventSource, Event> decide(EventSource eventSource) {
		String fileName = eventSource.getFileName();
		
		int fileExtensionIndex = fileName.lastIndexOf(".") + 1;
		
		if (fileExtensionIndex > 0) {
			String fileExtension = fileName.substring(fileExtensionIndex);
			
			switch (fileExtension) {
				case "json":
					return this.jsonInputStreamMapper;
				case "csv":
					return this.csvInputStreamMapper;
				case "xml":
					return this.xmlInputStreamMapper;
			}
		}
		
		return this.nullMappingStrategy;
	}
}
