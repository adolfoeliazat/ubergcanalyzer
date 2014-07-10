package de.morten.model.task;



public class TaskChain {
	private final Task root = new Task(new NullTaskConsumer());
	public TaskChain(final CorrelationId correlationId, final Iterable<? extends MessageConsumer> list)
	{
		for(final MessageConsumer t : list)
		{
			t.registerWith(correlationId);
			this.root.add(t);
		}
	}
	
	public boolean consume(final String message)
	{
		return this.root.consume(message);
	}
	
	public static class NullTaskConsumer implements MessageConsumer {
		@Override
		public boolean consume(String message) {
			return false;
		}

		@Override
		public void reset() {
		}

		@Override
		public CorrelationId getCorrelationId() {
			return new NullCorrelationId();
		}

		@Override
		public void registerWith(CorrelationId id) {
		}
	}
}
