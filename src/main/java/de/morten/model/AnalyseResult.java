
package de.morten.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.annotation.ThreadSafe;
import org.joda.time.DateTime;



@ThreadSafe
public class AnalyseResult implements Serializable {
	private static final long serialVersionUID = -2983890629567244775L;
	private final String name;
	private final Map<String, List<GCEvent>> events;
	
	public AnalyseResult(final String nameSuffix, final Map<String, List<GCEvent>> events) {
		final DateTime current  = new DateTime();
		this.events = Collections.unmodifiableMap(events);
		this.name = current.toString() + "-" + nameSuffix;
	}
	
	public String getName() {
		return this.name;
	}
	
	/**
	 * An unmodifiable view to gc events
	 * @return never null
	 */
	public Map<String, List<GCEvent>> getEvents() {
		return this.events;
	}
}
