package de.morten.model.task;


public class IdentifiedTaskConsumer implements TaskConsumer{
	private final CorrelationId correlationId;
	private TaskConsumer delegate;

	public IdentifiedTaskConsumer(final CorrelationId correlationId, final TaskConsumer delegate)
	{
		this.correlationId = correlationId;
		this.delegate = delegate;
	}
	
	@Override
	public boolean consume(String message) {
		return this.delegate.consume(message);
	}

	@Override
	public void reset() {
		this.delegate.reset();
	}

	@Override
	public CorrelationId getCorrelationId() {
		return this.correlationId;
	}
}
