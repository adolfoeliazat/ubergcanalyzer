package de.morten.model.task;

public class CorrelationId {
	private final String id;

	public CorrelationId(final String id) { this.id = id; }
	
	@Override public String toString() {
		return this.id;
	}
}
