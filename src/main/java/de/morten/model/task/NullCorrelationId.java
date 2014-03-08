package de.morten.model.task;

/**
 * Reprensents an non existing correlation id
 * 
 * @author Christian Bannes
 */
public class NullCorrelationId extends CorrelationId {

	public NullCorrelationId() {
		super("null");
	}

}
