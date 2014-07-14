package de.morten.model.message;

class MessageConsumerNode {

	private MessageConsumer data;
	private MessageConsumerNode next;
	
	public MessageConsumerNode(MessageConsumer data)
	{
		this.data = data;
		this.next = null;
	}
	
	public void add(MessageConsumer data)
	{
		final MessageConsumerNode task = new MessageConsumerNode(data);
		final MessageConsumerNode lastNode = getLastNode();
		lastNode.next = task;
	}
	
	private MessageConsumerNode getLastNode() {
		MessageConsumerNode t = this;
		
		while(t.next != null)
			t = t.next;
		
		return t;
	}

	public void moveToEnd() {
		if(next == null)
			return;
		
		final MessageConsumerNode lastNode = getLastNode();
		lastNode.next = this;
	}
	
	public void resetTail()
	{
		MessageConsumerNode t = this.next;
		while(t != null) {
			t.data.reset();
			t = t.next;
		}
	}
	
	public boolean consume(final Message message) {
		MessageConsumerNode t = this;
		boolean consumed = false;
		while(t != null) {
			consumed = t.data.consume(message);
			if(consumed) {
				t.resetTail();
				t.moveToEnd();
				break;
			}
			t = t.next;
		}
		return consumed;
	}
}
