package de.morten.model.parser;


import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import de.java.regexdsl.model.Match;
import de.java.regexdsl.model.Regex;
import de.morten.model.task.Message;
import de.morten.model.task.MessageConsumer;

public abstract class AbstractParser implements MessageConsumer {

	private String buffer = new String();
	
	public boolean consume(final @Nonnull Message message)
	{
		Preconditions.checkNotNull(message);
		
		return isMultiLine()? consumeMultiLine(message) : consumeSingleLine(message);
	}
	
	private boolean consumeSingleLine(final Message message)
	{
		if(startParsing(message))
			return match(message);
		
		return false;
	}

	private boolean consumeMultiLine(final Message message) {
		
		final boolean startDetected = startParsing(message);
		if(startDetected && alreadyStarted())
			this.buffer = new String();
		
		if(alreadyStarted() || startParsing(message))
		{
			if(!buffer.isEmpty()) this.buffer += "\n";
			this.buffer += message.text();
			
			if(inlineDetected(message)) return false;
			
			final boolean success = match(message.createCorrelatedMessage(this.buffer));
			if(success) reset();
			
			return true;
		}
		
		return false;
	}
	

	private boolean match(Message message) {
		final Regex pattern = pattern();
		double d = System.currentTimeMillis();
		final Match match = pattern.match(message.text());
		System.out.println("duration: " + (System.currentTimeMillis() - d));
		if(match.empty()) return false;
		
		publishEventFor(match, message);
		return true;
	}
	
	
	protected abstract void publishEventFor(Match match, Message message);
	protected abstract boolean isMultiLine();
	protected abstract boolean startParsing(Message message);
	protected abstract Regex pattern();
	protected abstract boolean inlineDetected(Message message);
	
	

	private boolean alreadyStarted() {
		return this.buffer.length() > 0;
	}

	@Override
	public void reset() {
		this.buffer = new String();
	}

}
