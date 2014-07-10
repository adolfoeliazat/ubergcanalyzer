package de.morten.model.task;



public class TaskChain {
	private final Task root = new Task(new NullTaskConsumer());
	public TaskChain(final CorrelationId correlationId, final Iterable<? extends MessageConsumer> list)
	{
		for(final MessageConsumer t : list)
		{
			this.root.add(t);
		}
	}
	
	public boolean consume(final Message message)
	{
		return this.root.consume(message);
	}
	
	public static class NullTaskConsumer implements MessageConsumer {
		@Override
		public boolean consume(final Message message) {
			return false;
		}

		@Override
		public void reset() {
		}

	}
}
