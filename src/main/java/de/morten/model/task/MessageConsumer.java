package de.morten.model.task;

import javax.annotation.Nonnull;


public interface  MessageConsumer {
	boolean consume(@Nonnull final String message);
	void reset();
	@Nonnull CorrelationId getCorrelationId();
	void registerWith(@Nonnull CorrelationId id);
}
