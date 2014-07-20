package de.morten.model.parser.cms;

import de.java.regexdsl.model.Match;
import de.morten.model.message.Message;

public class ConcurrentMarkParser extends ConcurrentPhaseParser {

	@Override
	protected void publishEventFor(Match match, Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean startParsing(Message message) {
		return message.text().contains("CMS-concurrent-mark");
	}

}
