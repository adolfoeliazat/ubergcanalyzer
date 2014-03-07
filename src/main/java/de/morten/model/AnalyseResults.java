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



@SessionScoped
@ThreadSafe
@Named
public class AnalyseResults implements Serializable {
	private static final long serialVersionUID = 7474880936605699044L;
	final Map<String, List<GCEvent>> eventNameToEvents = new HashMap<>();
	//private List<AnalyseResult> list = Collections.synchronizedList(new ArrayList<AnalyseResult>());
	
	
//	public void add(final AnalyseResult result) {
//		this.list.add(result);
//	}
	
	public List<AnalyseResult> getAll() {
		//return this.list;
		final List<AnalyseResult> r = new ArrayList<>();
		r.add(new AnalyseResult("test", eventNameToEvents));
		return r;
	}
	
	public void handleEvent(@Observes final GCEvent event)
	{
		if(eventNameToEvents.get(event.getName()) == null)
		{
			final List<GCEvent> list = new ArrayList<>();
			eventNameToEvents.put(event.getName(), list);
		}
		eventNameToEvents.get(event.getName()).add(event);
	}
	
	
}
