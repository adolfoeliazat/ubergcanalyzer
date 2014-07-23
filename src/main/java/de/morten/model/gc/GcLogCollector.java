package de.morten.model.gc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;

import org.apache.http.annotation.ThreadSafe;

import de.morten.model.message.CorrelationId;



/**
 * Collector class that catches (observes) all gc parsing events while 
 * log files are parsed. All {@link GCEvent}s that correlate (are from the same log file)
 * have the same {@link CorrelationId}. This id is used to group the events together
 * to create a {@link AnalyseResult} from it. 
 * 
 * When multiple gc log files have been parsed a call to {@link #getAllLogAnalyseResults()} will return multiple
 * {@link AnalyseResult} objects, one for each parsed log file.
 * 
 * @author Christian Bannes
 */
@SessionScoped
@ThreadSafe
@Named
public class GcLogCollector implements Serializable {
	private static final long serialVersionUID = 1L;
	final Map<CorrelationId, Map<String, List<GCEvent>>> resultsPerLogFile = new ConcurrentHashMap<>();
	
	/**
	 * Get all parsing results. The result of one log file is represented by a {@link AnalyseResult}.
	 * 
	 * @return all parsing results from all log files.
	 */
	public List<AnalyseResult> getAllParsedLogFiles() {
		
		return resultsPerLogFile.entrySet().stream()
			.map(entry -> new AnalyseResult(entry.getKey().toString(), entry.getValue()))
			.collect(Collectors.toList());
	}
	
	public AnalyseResult getParsedLogFile(final CorrelationId correlationId) {
		return new AnalyseResult(correlationId.toString(), resultsPerLogFile.get(correlationId));
	}
	
	/**
	 * Saves the given event into this result. This method is usually called by the container
	 * when an {@link GCEvent} was triggered. 
	 * 
	 * The event will be triggered while parsing a gc log file. If the parser identifies
	 * an event it will trigger the event. All events from the same log file have
	 * the same correlation id. 
	 * 
	 * @param event the event to save
	 */
	public void collectEvent(@Observes final GCEvent event)
	{
		Objects.requireNonNull(event);
		resultsPerLogFile.putIfAbsent(event.getCorrelationId(), new ConcurrentHashMap<>());
		
		final Map<String, List<GCEvent>> eventNameToEvents = resultsPerLogFile.get(event.getCorrelationId());
		eventNameToEvents.putIfAbsent(event.getName(), Collections.synchronizedList(new ArrayList<GCEvent>()));
		eventNameToEvents.get(event.getName()).add(event);
	}
	
	
}
