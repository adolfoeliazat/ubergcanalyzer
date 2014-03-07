package de.morten.model.task;

import javax.annotation.Nonnull;

public interface  TaskConsumer {
	boolean consume(@Nonnull final String message);
	void reset();
	@Nonnull CorrelationId getCorrelationId();
	void setCorrelationId(@Nonnull CorrelationId id);
}
