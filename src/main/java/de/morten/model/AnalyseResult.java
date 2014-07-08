
package de.morten.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.annotation.ThreadSafe;
import org.joda.time.DateTime;

import com.google.common.base.Preconditions;


/**
 * Represents the result of ONE log file analysis.
 */
@ThreadSafe
public class AnalyseResult implements Serializable {
	private static final long serialVersionUID = -2983890629567244775L;
	private final String name;
	private final Map<String, List<GCEvent>> events;
	
	/**
	 * Create a analyse result of one log file with all found gc events. 
	 * 
	 * @param nameSuffix the suffix to use for the name of this result. Will be used
	 * 				     in the front end.
	 * @param events all events of this result
	 */
	public AnalyseResult(final String nameSuffix, final Map<String, List<GCEvent>> events) {
		final DateTime current  = new DateTime();
		Preconditions.checkNotNull(nameSuffix);
		Preconditions.checkNotNull(events);
		
		this.events = Collections.unmodifiableMap(events);
		this.name = current.toString() + "-" + nameSuffix;
	}
	
	/**
	 * The unique name of this log file analyse result
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * An unmodifiable view to gc events of one log file
	 * 
	 * @return all found gc events of one log file
	 */
	public Map<String, List<GCEvent>> getEvents() {
		return this.events;
	}
}
