package de.morten.model.task;

/**
 * An identifier to correlate events that are part of the same
 * analysis.
 * 
 * @author christian Bannes
 */
public class CorrelationId {
	private final String id;

	
	public CorrelationId(final String id) { this.id = id; }
	
	@Override public String toString() {
		return this.id;
	}
}
