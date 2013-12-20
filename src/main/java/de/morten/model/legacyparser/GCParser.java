package de.morten.model.legacyparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.morten.model.GCEvent;



public class GCParser {
	
	public Map<String, List<GCEvent>> parse(final BufferedReader reader) throws IOException
	{
		final Map<String, List<GCEvent>> eventNameToEvents = Maps.newHashMap();
		String line = null;
		while((line = reader.readLine()) != null)
		{
			final MinorGCParser parser = new MinorGCParser(line);
			if(parser.matches())
			{
				final GCEvent event = parser.parse();
				add(eventNameToEvents, event);
			}
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
