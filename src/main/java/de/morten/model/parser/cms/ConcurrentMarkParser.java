package de.morten.model.parser.cms;

import de.morten.model.message.Message;
import de.morten.model.parser.ActiveGCParser;

/**
 * Example line: 2012-11-14T21:01:38.684+0100: 1257.639: [CMS-concurrent-mark: 1.142/1.148 secs] [Times: user=6.76 sys=0.15, real=1.14 secs]
 * @author Christian Bannes
 */
@ActiveGCParser
public class ConcurrentMarkParser extends ConcurrentPhaseParser {

	@Override
	protected String getName() {
		return "CMS-concurrent-Mark";
	}

	@Override
	protected boolean startParsing(Message message) {
		return message.text().contains("CMS-concurrent-mark:");
	}

}
