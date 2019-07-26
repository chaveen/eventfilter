package org.devchavez.eventfilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.devchavez.eventfilter.event.Events;
import org.devchavez.eventfilter.event.op.EventSource;
import org.devchavez.eventfilter.event.op.EventTarget;
import org.devchavez.eventfilter.event.vo.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entrypoint into the application
 *
 */
@SpringBootApplication(scanBasePackages = { "org.devchavez.eventfilter" })
public class Application implements CommandLineRunner {
	private Events events;

	@Autowired
	public void setEvents(Events events) {
		this.events = events;
	}

	/**
	 * We break and send commandline arguments to the main Events service
	 */
	@Override
	public void run(String... args) throws Exception {
		
		if (args.length >= 2) {
			this.process(args[0], Arrays.copyOfRange(args, 1, args.length));
		} else {
			System.out.println("At least 2 arguments are required!");
		}
	}
	
	/**
	 * 
	 * This reads Event source files, process them, writes them to and output and
	 * finally prints a summary on the Stdout
	 * 
	 */
	private void process(String outputFile, String... eventFiles) {
		List<EventSource> eventSources = Arrays.stream(eventFiles)
				.map(x -> new EventSource(new File(x)))
				.collect(Collectors.toList());
		List<Event> events = this.events.getServicedEvents(eventSources);
		
		EventTarget evenetTarget = new EventTarget(new File(outputFile));
		this.events.writeEvents(events, evenetTarget);
		
		this.events.printEvents(events, System.out, false);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
