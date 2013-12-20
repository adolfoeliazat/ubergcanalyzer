package de.morten.model.parser;


import de.java.regexdsl.model.Match;
import de.java.regexdsl.model.Regex;
import de.morten.model.task.TaskConsumer;

public abstract class AbstractParser implements TaskConsumer {

	private String buffer = new String();
	
	public boolean consume(final String message)
	{
		return isMultiLine()? consumeMultiLine(message) : consumeSingleLine(message);
	}
	
	private boolean consumeSingleLine(final String message)
	{
		if(startDetected(message))
			return match(message);
		
		return false;
	}

	private boolean consumeMultiLine(final String message) {
		
		
		if(alreadyStarted() || startDetected(message))
		{
			if(!buffer.isEmpty()) this.buffer += "\n";
			this.buffer += message;
			final boolean success = match(this.buffer);
			if(success) reset();
			
			return true;
		}
		
		return false;
	}

	private boolean match(String message) {
		final Regex pattern = pattern();
		final Match match = pattern.match(message);
		if(match.empty()) return false;
		
		publishEventFor(match);
		return true;
	}
	
	
	protected abstract void publishEventFor(Match match);
	protected abstract boolean isMultiLine();
	protected abstract String startSequence();
	protected abstract Regex pattern();
	
	

	private boolean startDetected(final String message) {
		return message.contains(startSequence());
	}


	private boolean alreadyStarted() {
		return this.buffer.length() > 0;
	}

	@Override
	public void reset() {
		this.buffer = new String();
	}

}
