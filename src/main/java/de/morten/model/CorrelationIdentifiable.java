package de.morten.model;

import de.morten.model.task.CorrelationId;

/**
 * A {@link CorrelationId} is used to correlate gc events
 * that are part of the same log file.
 * 
 * @author Christian Bannes
 */
public interface CorrelationIdentifiable {
	public CorrelationId getCorrelationId();
}
