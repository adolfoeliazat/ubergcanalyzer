package de.morten.model;

import de.morten.model.task.CorrelationId;

public abstract class  AbstractGCEvent implements GCEvent{

	/** time statistics of this gc event (timestamp, duration, ...) */
	private GCTimeStats timeStats;
	/** the name of the garbage collector (e.g. PSYoungGen, OldGC, ...) */
	private final String name;
	private CorrelationId correlationId;

	public AbstractGCEvent(final String name, final GCTimeStats timeStats, final CorrelationId correlationId) {
		this.name = name;
		this.timeStats = timeStats;
		this.correlationId = correlationId;
	}
	
	public GCTimeStats getTimeStats() {
		return this.timeStats;
	}
	
	public String getName() {
		return this.name;
	}
	
	public CorrelationId getCorrelationId() {
		return this.correlationId;
	}
	
}
