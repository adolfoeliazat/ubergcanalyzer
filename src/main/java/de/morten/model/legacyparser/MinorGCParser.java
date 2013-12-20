package de.morten.model.legacyparser;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.JodaTimePermission;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import de.morten.model.GCMemStats;
import de.morten.model.GCTimeStats;
import de.morten.model.MinorGCEvent;

public class MinorGCParser {

	private final String line;
	private final String[] split;
	
	public MinorGCParser(final String line)
	{
		this.line = line;
		this.split = line.split("\\s");
	}
	
	public boolean matches() {
		return this.split.length == 13 && this.split[3].equals("[PSYoungGen:");
	}
	
	public MinorGCEvent parse()
	{
		final GCTimeStats timeStats = parseTimeStats(this.split);
		final GCMemStats youngGenChange = parseYoungGenChange(this.split);
		final GCMemStats oldGenChange = parseOldGenChange(this.split);
		
		final MinorGCEvent event = new MinorGCEvent("minor", timeStats, youngGenChange, oldGenChange);
		return event;
		
	}
	
	private GCMemStats parseYoungGenChange(final String[] split) {
		final String memStatStr = split[4]; //19843K->2558K(38208K)]
		return createMemStat(memStatStr.substring(0, memStatStr.length()-1));
	}

	
	private  GCMemStats createMemStat(String memStat) {
		// 19843K->2558K(38208K)
		final String[] parts = memStat.split("->");
		final int before = Integer.valueOf(removeLast(parts[0]));
		
		final String[] right = parts[1].split("\\(");
		final int after = Integer.valueOf(removeLast(right[0]));
		final int total = Integer.valueOf(removeLast(removeLast(right[1])));//right[1].substring(0,  right[1].length()-2);
		return new GCMemStats(before, after, total);
	}
	

	private GCMemStats parseOldGenChange(final String[] split) {
		final String memStatStr = removeLast(split[5]); //19843K->2558K(38208K)
		return createMemStat(memStatStr);
	}
	
	private String removeLast(final String s) {
		return s.substring(0, s.length()-1);
	}
//[2013-03-23T15:06:05.424+0100:, 1,147:, [GC, [PSYoungGen:, 19843K->2558K(38208K)], 19843K->2558K(125632K),, 0,0121400, secs], [Times:, user=0,03, sys=0,00,, real=0,01, secs]]

	private GCTimeStats parseTimeStats(final String[] split) {
		final String timestr = removeLast(split[0]); //2013-03-23T15:06:05.424+0100:
		final String ellapsedStr = removeLast(split[1]); //1,147:
		final String durationStr = split[split.length-2]; //real=0,01

		DateTimeFormatter parser = ISODateTimeFormat.dateTime();
		DateTime time = parser.parseDateTime(timestr);
		
		final double ellapsed = Double.parseDouble(ellapsedStr.replace(',', '.'));

		final double duration = Double.parseDouble(durationStr.split("=")[1].replace(',', '.'));
		
		return new GCTimeStats(time, ellapsed, duration);
	}

}
