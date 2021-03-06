package de.morten.model.gc;

import de.morten.model.message.CorrelationId;


/**
 * Represents one garbage collection event of the  full gc collector. The FULL GC collector
 * is single threaded and runs in a stop-the-world fashion i.e. all application threads 
 * are stopped while it is running.
 * 
 * @author Christian Bannes
 */
public class FullGCEvent extends AbstractGCEvent {
	/** the unique name of this garbage collector */
	private final static String NAME = "FULL GC";
	
	public FullGCEvent(final GCTimeStats timeStats, final CorrelationId correlationId) {
		super(NAME, timeStats, correlationId);
	}

	/**
	 * The FULL GC collector uses stop-the-world, i.e. all application
	 * threads are stopped while this gc was running
	 * @return always true
	 */
	@Override
	public boolean isStopTheWorld() {
		return true;
	}

}
