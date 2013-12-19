import junit.framework.Assert;

import org.junit.Test;

import de.java.regexdsl.model.Match;
import de.java.regexdsl.model.Regex;
import de.java.regexdsl.model.RegexBuilder;






public class RegexTest {

	@Test
	public void debug() {
		final Regex regex = RegexBuilder
				.create()
				.group("#c")
					.number()
					.constant("K(")
				.end()
				.build();
	
		final Match match = regex.match("12345K(");
		Assert.assertEquals("12345K(", match.getByName("c"));
	}
	
	@Test
	public void memstat() {
		final String s = "2285353K->285767K(2621440K)";
		
		final Regex memstat = RegexBuilder.create()
				.pattern("\\b")
				.number("#occupancyPriorGc")
				.constant("K->")
				.number("#occupancyAfterGc")
				.pattern("K\\(")
				.number("#totalSize")
				.constant("K)")
				.build();
		
		final Match match = memstat.match(s);
		
		Assert.assertEquals("2285353", match.getByName("occupancyPriorGc"));
		Assert.assertEquals("285767", match.getByName("occupancyAfterGc"));
		Assert.assertEquals("2621440", match.getByName("totalSize"));
	}
	
	
	@Test
	public  void parNew() {
		
		final String gcLog = "2012-11-14T20:43:06.188+0100: 145.144: [GC 145.144: [ParNew"+
				"Desired survivor size 268435456 bytes, new threshold 15 (max 15)"+
				"- age   1:   86678936 bytes,   86678936 total"+
				"- age   2:   54729488 bytes,  141408424 total"+
				"- age   3:   70542472 bytes,  211950896 total"+
				": 2285353K->285767K(2621440K), 0.0784930 secs] 2285353K->285767K(8912896K), 0.0785990 secs] [Times: user=1.10 sys=0.06, real=0.08 secs]"; 

		final String gcLog2 = "145.144: [GC 145.144: [ParNew: 2285353K->285767K(2621440K), 0.2314667 secs] 2285353K->285767K(8912896K), 0.08 secs]";
			
		final Regex date = RegexBuilder.create()
				.number("#year").constant("-").number("#month").constant("-").number("#day")
				.build();
		
		final Regex time = RegexBuilder.create()
				.number("#hour").constant(":").number("#minute").constant(":").number("#secs")
				.build();
		
		final Regex memstat = RegexBuilder.create()
				.pattern("\\b")
				.number("#occupancyPriorGc").constant("K->").number("#occupancyAfterGc").pattern("K\\(").number("#totalSize").constant("K)")
				.build();
		
		
		
		final Regex regex = RegexBuilder.create()
				.optional("#timestamp")
					.regex("#date", date) 
					.constant("T")
					.regex("#time", time)
					.constant("+0100: ")
				.end()
				.number("#timeSinceStartup").any()
				.regex("#eden", memstat).any()
				.regex("#heap", memstat).any()
				.pattern("[^\\d\\.]").number("#duration").constant(" secs]")
				.build();
		
		
		final Match match = regex.match(gcLog);
		
		Assert.assertEquals("2285353K->285767K(2621440K)", match.getByName("eden"));
		Assert.assertEquals("2285353K->285767K(8912896K)", match.getByName("heap"));
		Assert.assertEquals("0.08", match.getByName("duration"));
		
		final Match match2 = regex.match(gcLog2);
		Assert.assertEquals("145.144", match2.getByName("timeSinceStartup"));		
		
		Assert.assertEquals("2285353K->285767K(2621440K)", match2.getByName("eden"));
		Assert.assertEquals("2285353K->285767K(8912896K)", match2.getByName("heap"));
		Assert.assertEquals("0.08", match2.getByName("duration"));
			
		
		
	}
	
}
