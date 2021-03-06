package de.morten.model.parser;

import java.io.BufferedReader;
import java.io.IOException;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import de.morten.model.gc.GCEvent;
import de.morten.model.message.CorrelationId;
import de.morten.model.message.Message;
import de.morten.model.message.MessageConsumer;
import de.morten.model.message.MessageConsumerChain;


/**
 * The entry point of gc parsing. It delegates to gc parsers that are
 * annotated with {@link ActiveGCParser}.
 * 
 * @author Christian Bannes
 */
public class GCParser {

	@Inject @Any @ActiveGCParser private Instance<MessageConsumer> parser;

	/**
	 * Parses the lines of the given buffered reader. During the parse operation every recognised
	 * garbace collection event (like a minor gc or full gc) will result in a fired {@link GCEvent} that
	 * can be observed by any CDI enabled object. Every fired event is correlated with the given correlation id.
	 * 
	 * @param correlationId the correlation id that will be passed to every fired gc event
	 * @param reader usually contains the content of a gc file written by the JVM.
	 * 
	 * @throws IOException
	 */
	public void parse(final CorrelationId correlationId, final BufferedReader reader) throws IOException
	{
		final MessageConsumerChain consumer = new MessageConsumerChain(correlationId, this.parser);
	 	
		String line = null;
		while((line = reader.readLine()) != null)
		{
			consumer.consume(new Message(line, correlationId));
		}
	}
}
