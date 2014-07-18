package de.morten.model.parser.cms;

import de.morten.model.gc.AbstractGCEvent;
import de.morten.model.gc.GCTimeStats;
import de.morten.model.message.CorrelationId;

public class ConcurrentPhaseEvent extends AbstractGCEvent {

	public ConcurrentPhaseEvent(final String name, final GCTimeStats timeStats, final CorrelationId correlationId) {
		super(name, timeStats, correlationId);
	}

	@Override
	public boolean isStopTheWorld() {
		return false;
	}

}
