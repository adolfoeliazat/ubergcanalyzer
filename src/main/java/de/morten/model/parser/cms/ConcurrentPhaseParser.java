package de.morten.model.parser.cms;

import de.java.regexdsl.model.Regex;
import de.java.regexdsl.model.RegexBuilder;
import de.morten.model.message.Message;
import de.morten.model.parser.AbstractParser;
import de.morten.model.parser.Patterns;

public abstract class ConcurrentPhaseParser extends AbstractParser {

	@Override
	protected boolean isMultiLine() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Regex pattern() {
		return RegexBuilder.create()
				.optional("#timestamp")
				.regex("#date", Patterns.date()) 
				.constant("T")
				.regex("#time", Patterns.time())
				.constant("+0100: ")
			.end()
			.number("#timeSinceStartup").any()
			.number("#elapsedTime").constant("/").number("#wallClockTime").constant(" secs]").any()
			.pattern("[^\\d\\.]").number("#duration").pattern("\\s+").constant("secs]").any()
			.build();
	}

	@Override
	protected boolean inlineDetected(Message message) {
		return false;
	}



}
