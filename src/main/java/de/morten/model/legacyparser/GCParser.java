package de.morten.model.legacyparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import de.morten.infrastructure.EventPublisher;
import de.morten.model.GCEvent;
import de.morten.model.parser.ActiveGCParser;
import de.morten.model.task.TaskChain;
import de.morten.model.task.TaskConsumer;



public class GCParser {

	@Inject @Any @ActiveGCParser private Instance<TaskConsumer> parser;

	
	public void parse(final BufferedReader reader) throws IOException
	{
		final TaskChain consumer = new TaskChain(this.parser);
//		final Map<String, List<GCEvent>> eventNameToEvents = new HashMap<>();
//		final Object handler = new Object() {
//			@Subscribe public void handle(final GCEvent event) {
//				GCParser.this.add(eventNameToEvents, event);
//			}
//		};
//		EventPublisher.instance().register(handler);
	 	
		String line = null;
		while((line = reader.readLine()) != null)
		{
			consumer.consume(line);
		}
	}

//	private void add(final Map<String, List<GCEvent>> eventNameToEvents, final GCEvent event) {
//		final List<GCEvent> events = eventNameToEvents.get(event.getName());
//		if(events == null)
//		{
//			final List<GCEvent> list = new ArrayList<>();
//			eventNameToEvents.put(event.getName(), list);
//		}
//		
//		eventNameToEvents.get(event.getName()).add(event);
//	}
}
