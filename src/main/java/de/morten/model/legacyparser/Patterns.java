package de.morten.model.legacyparser;

import de.java.regexdsl.model.Regex;
import de.java.regexdsl.model.RegexBuilder;


public class Patterns {
	
	public static Regex date() {
		return RegexBuilder.create()
				.number("#year").constant("-").number("#month").constant("-").number("#day")
				.build();
	}
	
	public static Regex time() {
		return RegexBuilder.create()
				.number("#hour").constant(":").number("#minute").constant(":").number("#secs")
				.build();
	}
	
	public static Regex memstat() {
		return RegexBuilder.create()
				.pattern("\\b")
				.number("#occupancyPriorGc").constant("K->").number("#occupancyAfterGc").pattern("K\\(").number("#totalSize").constant("K)")
				.build();
	}
	
	public static Regex parNew() {
		return RegexBuilder.create()
				.optional("#timestamp")
					.regex("#date", date()) 
					.constant("T")
					.regex("#time", time())
					.constant("+0100: ")
				.end()
				.number("#timeSinceStartup").any()
				.regex("#eden", memstat()).any()
				.regex("#heap", memstat()).any()
				.pattern("[^\\d\\.]").number("#duration").constant(" secs]")
				.build();
	}
	
	
}
