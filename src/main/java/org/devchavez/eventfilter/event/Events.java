package org.devchavez.eventfilter.event;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.devchavez.eventfilter.event.op.EventMapperStrategyDecider;
import org.devchavez.eventfilter.event.op.EventSource;
import org.devchavez.eventfilter.event.op.EventTarget;
import org.devchavez.eventfilter.event.vo.Event;
import org.devchavez.eventfilter.op.MapperDecider;
import org.devchavez.eventfilter.op.Writer;
import org.devchavez.eventfilter.op.writer.CSVOutputStreamWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * This is the Component that handle IO related tasks for Events
 *
 */
@Service
public class Events {
	private final MapperDecider<EventSource, Event> decider;
	private final Writer<Event, EventTarget> writer;

	@Autowired
	public Events(
			@Qualifier("eventMapperDecider") EventMapperStrategyDecider decider,
			@Qualifier("csvOutputStreamWriter") CSVOutputStreamWriterBuilder<EventTarget> writerBuilder) {
		this.decider = decider;
		this.writer = writerBuilder.forClass(Event.class);
	}
	
	/**
	 * Reads Events from a list of EventSources. 
	 * This also filters out Events zero Packets Serviced
	 * and orders them by RequestTime
	 */
	public List<Event> getServicedEvents(List<EventSource> eventSources) {
		List<Event> events = eventSources.stream()
				.flatMap(x -> this.decider.decide(x).apply(() -> x).stream())
				.filter(x -> Optional.ofNullable(x.getPacketsServiced()).map(y -> y > 0).orElse(false))
				.sorted(Comparator.nullsLast(Comparator.comparing(Event::getRequestTime)))
				.collect(Collectors.toList());
		
		return events;
	}
	
	/**
	 * This writes a list of Events into a EventTarget
	 */
	public void writeEvents(List<Event> events, EventTarget target) {
		events.stream().collect(this.writer.asCollector(target));
	}
	
	/**
	 * This computes a summary of counts by Service Guids from given list of Events
	 */
	public Map<UUID, Long> getServiceSummary(List<Event> events) {
		Map<UUID, Long> serviceSummary = events.stream()
				.collect(Collectors.groupingBy(Event::getServiceGuid, Collectors.counting()));
		
		return serviceSummary;
	}
}
