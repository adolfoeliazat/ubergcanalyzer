package de.morten.model.gc;

import de.morten.model.message.CorrelationId;

/**
 * A {@link CorrelationId} is used to correlate gc events
 * that are part of the same log file.
 * 
 * @author Christian Bannes
 */
public interface CorrelationIdentifiable {
	public CorrelationId getCorrelationId();
}
