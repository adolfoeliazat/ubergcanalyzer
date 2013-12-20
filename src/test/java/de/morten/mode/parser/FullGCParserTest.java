package de.morten.mode.parser;

import junit.framework.Assert;

import org.junit.Test;

import de.morten.model.GCTimeStats;
import de.morten.model.MinorGCEvent;
import de.morten.model.legacyparser.MinorGCParser;


public class FullGCParserTest {
	@Test
	public void test() {
		final String line = "2013-03-25T13:53:36.739+0100: 8,434: " +
				"[Full GC (System) [PSYoungGen: 2494K->0K(38208K)] [PSOldGen: 0K->2365K(87424K)] 2494K->2365K(125632K) [PSPermGen: 12394K->12394K(25344K)], 0,0328860 secs] " +
				"[Times: user=0,03 sys=0,00, real=0,04 secs]";
		
//		final MinorGCParser parser = new MinorGCParser(line);
//		final PSYoungGenGCEvent event = parser.parse();
//		
//		final GCTimeStats timeStats = event.getTimeStats();
//		Assert.assertEquals(0.01, timeStats.getDuration());
//		Assert.assertEquals(1.147, timeStats.getElappsedTime());
//		
//		//date
//		Assert.assertEquals(2013, timeStats.getTimestamp().getYear());
//		Assert.assertEquals(3, timeStats.getTimestamp().getMonthOfYear());
//		Assert.assertEquals(23, timeStats.getTimestamp().getDayOfMonth());
//
//		//time
//		Assert.assertEquals(15, timeStats.getTimestamp().getHourOfDay());
//		Assert.assertEquals(6, timeStats.getTimestamp().getMinuteOfHour());
//		Assert.assertEquals(5, timeStats.getTimestamp().getSecondOfMinute());
//		
//		
//		//PSYoungGen
//		Assert.assertEquals(19843, event.getYoungGenChange().occupancyBeforeGc());
//		Assert.assertEquals(2558, event.getYoungGenChange().occupancyAfterGc());
//		Assert.assertEquals(38208, event.getYoungGenChange().totalSize());
//
//
//		//OldGen
//		Assert.assertEquals(19843, event.getOldGenChange().occupancyBeforeGc());
//		Assert.assertEquals(2558, event.getOldGenChange().occupancyAfterGc());
//		Assert.assertEquals(125632, event.getOldGenChange().totalSize());
		
	}
}
