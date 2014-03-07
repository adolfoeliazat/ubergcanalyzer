package de.morten.model.task;



public class TaskChain {
	private final Task root = new Task(new NullTaskConsumer());
	public TaskChain(final CorrelationId correlationId, final Iterable<? extends TaskConsumer> list)
	{
		for(final TaskConsumer t : list)
		{
			t.setCorrelationId(correlationId);
			this.root.add(t);
		}
	}
	
	public boolean consume(final String message)
	{
		return this.root.consume(message);
	}
	
	public static class NullTaskConsumer implements TaskConsumer {
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
		public void setCorrelationId(CorrelationId id) {
		}
	}
}
