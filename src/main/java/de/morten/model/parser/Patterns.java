package de.morten.model.parser;

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
	
	public static Regex memStatOccupancyBeforeAfterAndTotal() {
		return RegexBuilder.create()
				.pattern("\\b")
				.number("#occupancyPriorGc").constant("K->").number("#occupancyAfterGc").pattern("K\\(").number("#totalSize").constant("K)")
				.build();
	}
	
	/**
	 * Example: 3166449K(6291456K)
	 * @return
	 */
	public static Regex memStatOccupancyBeforeAndTotal() {
		return RegexBuilder.create()
				.pattern("\\b")
				.number("#occupancyPriorGc").constant("K(").number("#occupancyAfterGc").constant("K)")
				.build();
	}
	
	
}
