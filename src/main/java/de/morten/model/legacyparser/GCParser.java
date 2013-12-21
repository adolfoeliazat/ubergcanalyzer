package de.morten.model.legacyparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;

import de.morten.infrastructure.EventPublisher;
import de.morten.model.GCEvent;
import de.morten.model.parnew.ParNewParser;
import de.morten.model.task.TaskChain;



public class GCParser {

	private final TaskChain consumer;
	
	public GCParser() {
		final TaskChain taskChain = new TaskChain(Arrays.asList(new ParNewParser()));
		this.consumer = taskChain;
	}
	
	
	
	public Map<String, List<GCEvent>> parse(final BufferedReader reader) throws IOException
	{
		
		final Map<String, List<GCEvent>> eventNameToEvents = Maps.newHashMap();
		final Object handler = new Object() {
			@Subscribe public void handle(final GCEvent event) {
				GCParser.this.add(eventNameToEvents, event);
			}
		};
		EventPublisher.instance().register(handler);
		
		String line = null;
		while((line = reader.readLine()) != null)
		{
			this.consumer.consume(line);
		}
		return eventNameToEvents;
	}

	private void add(final Map<String, List<GCEvent>> eventNameToEvents, final GCEvent event) {
		final List<GCEvent> events = eventNameToEvents.get(event.getName());
		if(events == null)
		{
			final List<GCEvent> list = Lists.newArrayList();
			eventNameToEvents.put(event.getName(), list);
		}
		
		eventNameToEvents.get(event.getName()).add(event);
	}
}
