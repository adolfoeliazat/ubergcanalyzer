package de.morten.model.parser.cms;

import de.morten.model.message.Message;
import de.morten.model.parser.ActiveGCParser;

@ActiveGCParser
public  class ConcurrentResetParser extends ConcurrentPhaseParser {

	@Override
	protected String getName() {
		return "CMS-concurrent-reset";
	}

	@Override
	protected boolean startParsing(Message message) {
		return message.text().contains("CMS-concurrent-reset:");
	}

}
