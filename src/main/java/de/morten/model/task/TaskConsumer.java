package de.morten.model.task;

public interface  TaskConsumer {
	public boolean consume(final String message);
	public void reset();
	public CorrelationId getCorrelationId();
}
