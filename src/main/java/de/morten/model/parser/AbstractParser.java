package de.morten.model.parser;


import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import de.java.regexdsl.model.Match;
import de.java.regexdsl.model.Regex;
import de.morten.model.task.CorrelationId;
import de.morten.model.task.NullCorrelationId;
import de.morten.model.task.MessageConsumer;

public abstract class AbstractParser implements MessageConsumer {

	private String buffer = new String();
	private CorrelationId correlationId = new NullCorrelationId();
	
	public boolean consume(final @Nonnull String message)
	{
		Preconditions.checkNotNull(message);
		
		return isMultiLine()? consumeMultiLine(message) : consumeSingleLine(message);
	}
	
	private boolean consumeSingleLine(final String message)
	{
		if(startParsing(message))
			return match(message);
		
		return false;
	}

	private boolean consumeMultiLine(final String message) {
		
		final boolean startDetected = startParsing(message);
		if(startDetected && alreadyStarted())
			this.buffer = new String();
		
		if(alreadyStarted() || startParsing(message))
		{
			if(!buffer.isEmpty()) this.buffer += "\n";
			this.buffer += message;
			
			if(inlineDetected(message)) return false;
			
			final boolean success = match(this.buffer);
			if(success) reset();
			
			return true;
		}
		
		return false;
	}

	@Override public @Nonnull CorrelationId getCorrelationId()
	{
		return this.correlationId;
	}
	

	private boolean match(String message) {
		final Regex pattern = pattern();
		double d = System.currentTimeMillis();
		final Match match = pattern.match(message);
		System.out.println("duration: " + (System.currentTimeMillis() - d));
		if(match.empty()) return false;
		
		publishEventFor(match);
		return true;
	}
	
	
	protected abstract void publishEventFor(Match match);
	protected abstract boolean isMultiLine();
	protected abstract boolean startParsing(String message);
	protected abstract Regex pattern();
	protected abstract boolean inlineDetected(String message);
	
	

	public void registerWith(final CorrelationId correlationId) {
		Preconditions.checkNotNull(correlationId);
		this.correlationId  = correlationId;
	}
//	private boolean startDetected(final String message) {
//		return message.contains(startSequence());
//	}


	private boolean alreadyStarted() {
		return this.buffer.length() > 0;
	}

	@Override
	public void reset() {
		this.buffer = new String();
	}

}
