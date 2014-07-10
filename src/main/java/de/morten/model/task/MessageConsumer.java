package de.morten.model.task;

import javax.annotation.Nonnull;


public interface  MessageConsumer {
	boolean consume(@Nonnull final Message message);
	void reset();
}
