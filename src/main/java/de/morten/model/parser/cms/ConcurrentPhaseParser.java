package de.morten.model.parser.cms;

import de.java.regexdsl.model.Match;
import de.java.regexdsl.model.Regex;
import de.java.regexdsl.model.RegexBuilder;
import de.morten.model.message.Message;
import de.morten.model.parser.AbstractParser;
import de.morten.model.parser.Patterns;

public class ConcurrentPhaseParser extends AbstractParser{

	@Override
	protected void publishEventFor(Match match, Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isMultiLine() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean startParsing(Message message) {
		// TODO Auto-generated method stub
		return false;
	}

	//2012-11-14T21:01:38.704+0100: 1257.660: [CMS-concurrent-preclean: 0.021/0.021 secs] [Times: user=0.02 sys=0.00, real=0.02 secs]
	
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
