package de.morten.model.parser.cms;

import org.junit.Test;

import de.java.regexdsl.model.Regex;
import de.java.regexdsl.model.RegexBuilder;
import de.morten.model.parser.Patterns;

public class Garbage {

	@Test
	public void singleLineShouldMatch() {
		final String i1 = "2012-11-14T21:01:37.171+0100: 1256.127:";
		
		//pattern: [tenured capacity start K (t-capacity after gc)] total capacity before (total capacity after)
		
		//2012-11-14T20:43:18.599+0100: 157.554: [GC 157.555: [ParNew : 2519259K->430351K(2621440K), 0.3845220 secs] 2519259K->507304K(8912896K), 0.3846240 secs] [Times: user=2.33 sys=0.26, real=0.38 secs] 
		final String i2 = "2012-11-14T21:01:37.171+0100: 1256.127: [GC [1 CMS-initial-mark: 3166449K(6291456K)] 3644014K(8912896K), 0.3643600 secs] [Times: user=0.36 sys=0.01, real=0.37secs]";
		final String i3 = "2012-11-14T21:01:37.171+0100: 1256.127: [GC [1 CMS-initial-mark: 3166449K(6291456K)] 3644014K(8912896K), 0.3643600 secs] [Times: user=0.36 sys=0.01, real=0.37secs]";
		final String i4 = "2012-11-14T21:01:37.171+0100: 1256.127: [GC [1 CMS-initial-mark: 3166449K(6291456K)] 3644014K(8912896K), 0.3643600 secs] [Times: user=0.36 sys=0.01, real=0.37secs]";
		final String i5 = "2012-11-14T21:01:37.171+0100: 1256.127: [GC [1 CMS-initial-mark: 3166449K(6291456K)] 3644014K(8912896K), 0.3643600 secs] [Times: user=0.36 sys=0.01, real=0.37secs]";

		
		final Regex regex = RegexBuilder.create()
		.optional("#timestamp")
			.regex("#date", Patterns.date()) 
			.constant("T")
			.regex("#time", Patterns.time())
			.constant("+0100: ")
		.end()
		.number("#timeSinceStartup").any()
		.regex("#tenured", Patterns.memStatOccupancyBeforeAndTotal()).any()
		.regex("#heap", Patterns.memStatOccupancyBeforeAndTotal()).any()
		.pattern("[^\\d\\.]").number("#duration").constant("secs]")
		.any()
		.build();
		
		System.out.println(regex.match(i2).empty());
	}
}
