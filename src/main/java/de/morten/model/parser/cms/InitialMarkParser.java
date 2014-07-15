package de.morten.model.parser.cms;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import de.java.regexdsl.model.Match;
import de.java.regexdsl.model.Regex;
import de.java.regexdsl.model.RegexBuilder;
import de.morten.model.gc.MinorGCEvent;
import de.morten.model.message.Message;
import de.morten.model.parser.AbstractParser;
import de.morten.model.parser.ActiveGCParser;
import de.morten.model.parser.Patterns;

//2012-11-14T21:01:37.171+0100: 1256.127: [GC [1 CMS-initial-mark: 3166449K(6291456K)] 3644014K(8912896K), 0.3643600 secs] [Times: user=0.36 sys=0.01, real=0.37
//secs]

@ActiveGCParser
public class InitialMarkParser extends AbstractParser {
	@Inject private Event<MinorGCEvent> event;
	
	private final static Regex INITIAL_MARK_PATTERM = createParNewPattern();
	
	@Override
	protected void publishEventFor(Match match, Message message) {
		// TODO Auto-generated method stub
		
	}

	private static Regex createParNewPattern() {
		return RegexBuilder.create()
				.optional("#timestamp")
					.regex("#date", Patterns.date()) 
					.constant("T")
					.regex("#time", Patterns.time())
					.constant("+0100: ")
				.end()
				.number("#timeSinceStartup").any()
				.regex("#eden", Patterns.memstat()).any()
				.regex("#heap", Patterns.memstat()).any()
				.pattern("[^\\d\\.]").number("#duration").constant(" secs]").any()
				.build();
	}

	@Override
	protected boolean isMultiLine() {
		return false;
	}

	@Override
	protected boolean startParsing(Message message) {
		return message.text().contains("CMS-initial-mark:");	
	}

	@Override
	protected Regex pattern() {
		return INITIAL_MARK_PATTERM;
	}

	@Override
	protected boolean inlineDetected(Message message) {
		return false;
	}

}
