package de.morten.model.task;

import javax.annotation.concurrent.Immutable;

/**
 * An identifier to correlate {@link Message}s. 
 * 
 * All messages (singe line of a gc log file is represented as message)
 * from the same log file should have the same correlation id. 
 * 
 * @author christian Bannes
 */
@Immutable
public class CorrelationId {
	private final String id;

	
	public CorrelationId(final String id) { this.id = id; }
	
	@Override public String toString() {
		return this.id;
	}
}
