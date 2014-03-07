package de.morten.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;

import org.apache.http.annotation.ThreadSafe;

import de.morten.model.task.CorrelationId;



@SessionScoped
@ThreadSafe
@Named
public class AnalyseResults implements Serializable {
	private static final long serialVersionUID = 1L;
	final Map<CorrelationId, Map<String, List<GCEvent>>> results = new HashMap<>();
	//final Map<String, List<GCEvent>> eventNameToEvents = new HashMap<>();
	//private List<AnalyseResult> list = Collections.synchronizedList(new ArrayList<AnalyseResult>());
	
	
//	public void add(final AnalyseResult result) {
//		this.list.add(result);
//	}
	
	public List<AnalyseResult> getAll() {
		//return this.list;
		
		final List<AnalyseResult> list = new ArrayList<>();
		for(final Map.Entry<CorrelationId, Map<String, List<GCEvent>>> entry : this.results.entrySet())
		{
			final AnalyseResult analyseResult = new AnalyseResult(entry.getKey().toString(), entry.getValue());
			list.add(analyseResult);
		}
		return list;
	}
	
	public void handleEvent(@Observes final GCEvent event)
	{
		if(results.get(event.getCorrelationId()) == null)
			results.put(event.getCorrelationId(), new HashMap<String, List<GCEvent>>());
		
		final Map<String, List<GCEvent>> eventNameToEvents = results.get(event.getCorrelationId());
		if(eventNameToEvents.get(event.getName()) == null)
		{
			final List<GCEvent> list = new ArrayList<>();
			eventNameToEvents.put(event.getName(), list);
		}
		eventNameToEvents.get(event.getName()).add(event);
	}
	
	
}
