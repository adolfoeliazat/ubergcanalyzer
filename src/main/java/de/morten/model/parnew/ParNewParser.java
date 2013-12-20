package de.morten.model.parnew;

import javax.annotation.RegEx;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import de.java.regexdsl.model.Match;
import de.java.regexdsl.model.Regex;
import de.java.regexdsl.model.RegexBuilder;
import de.morten.infrastructure.EventPublisher;
import de.morten.model.GCMemStats;
import de.morten.model.GCTimeStats;
import de.morten.model.MinorGCEvent;
import de.morten.model.legacyparser.Patterns;
import de.morten.model.parser.AbstractParser;

/**
 * 
 * @author Christian Bannes
 */
public class ParNewParser extends AbstractParser {

	private final static Regex PAR_NEW = createParNewPattern();
	private static final String START_PATTERN = "[ParNew";
	
	@Override
	public boolean isMultiLine() {
		return true;
	}

	@Override
	public String startSequence() {
		return START_PATTERN;
	}

	@Override
	protected Regex pattern() {
		return PAR_NEW;
	}
	
	/**
	 * Creates the pattern that matches a ParNew entry
	 * @return the created {@link RegEx}
	 */
	private static Regex createParNewPattern() {
		return RegexBuilder.create()
				.optional("#timestamp")
					.regex("#date", Patterns.date()) 
					.constant("T")
					.regex("#time", Patterns.time())
					.constant("+0100: ")
				.end()
				.number("#timeSinceStartup").any()
				.regex("#eden", Patterns.memstat()).any()
				.regex("#heap", Patterns.memstat()).any()
				.pattern("[^\\d\\.]").number("#duration").constant(" secs]")
				.build();
	}

	/**
	 * This method is called when a match was created. The values can now be extracted 
	 * from the match. 
	 * 
	 * This method creates a gc event and publishes the event using the
	 * {@link EventPublisher}.
	 */
	@Override
	protected void publishEventFor(final Match match) {
		
		final GCMemStats youngGenChange = readYoungGenChange(match);
		final GCMemStats oldGenChange = readOldGenChange(match);
		final GCTimeStats timeStats = readTimeStats(match);

		final MinorGCEvent minorGCEvent = new MinorGCEvent("ParNew", timeStats, youngGenChange, oldGenChange);
		
		EventPublisher.instance().publishEvent(minorGCEvent);
	}
	
	/**
	 * Reads the younggen change from the match
	 * @param match 
	 * @return
	 */
	private GCMemStats readYoungGenChange(final Match match)
	{
		final int edenBefore = Integer.valueOf(match.getByName("eden->occupancyPriorGc"));
		final int edenAfter = Integer.valueOf(match.getByName("eden->occupancyAfterGc"));
		final int edenTotal = Integer.valueOf(match.getByName("eden->totalSize"));
		final GCMemStats youngGenChange = new GCMemStats(edenBefore, edenAfter, edenTotal);	
		return youngGenChange;
	}
	
	private GCMemStats readOldGenChange(final Match match)
	{
		final int oldBefore = Integer.valueOf(match.getByName("heap->occupancyPriorGc"));
		final int oldAfter = Integer.valueOf(match.getByName("heap->occupancyAfterGc"));
		final int heapTotal = Integer.valueOf(match.getByName("heap->totalSize"));
		final GCMemStats oldGenChange = new GCMemStats(oldBefore, oldAfter, heapTotal);	
		return oldGenChange;
	}

	private GCTimeStats readTimeStats(final Match match)
	{
		DateTime startup = null;
		if(match.getByName("timestamp") != null)
		{
			final String date = match.getByName("timestamp->date");
			final String time = match.getByName("timestamp->time");
			DateTimeFormatter parser = ISODateTimeFormat.dateTime();
			startup = parser.parseDateTime(date+"T"+time + "+0100");
		}
		
		final double ellapsedTimeInSecs = Double.valueOf(match.getByName("timeSinceStartup"));
		final double durationInSecs = Double.valueOf(match.getByName("duration"));
		final GCTimeStats timeStats = new GCTimeStats(startup, ellapsedTimeInSecs, durationInSecs);
		return timeStats;
	}

	
}