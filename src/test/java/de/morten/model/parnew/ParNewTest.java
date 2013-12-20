package de.morten.model.parnew;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.eventbus.Subscribe;

import de.morten.infrastructure.EventPublisher;
import de.morten.model.GCEvent;

public class ParNewTest {
	
	
	
	@Test
	public void shouldPublishGCEvent()
	{
		
		final Handler handler = new Handler();
		EventPublisher.instance().register(handler);
		
		final List<String> lines = Arrays.asList("2012-11-14T20:43:06.188+0100: 145.144: [GC 145.144: [ParNew",
		"Desired survivor size 268435456 bytes, new threshold 15 (max 15)",
		"- age   1:   86678936 bytes,   86678936 total",
		"- age   2:   54729488 bytes,  141408424 total",
		"- age   3:   70542472 bytes,  211950896 total",
		": 2285353K->285767K(2621440K), 0.0784930 secs] 2285353K->285767K(8912896K), 0.0785990 secs] [Times: user=1.10 sys=0.06, real=0.08 secs]");
		
		final ParNewParser parser = new ParNewParser();
		
		for(final String line : lines)
		{
			parser.consume(line);
		}
		
		Assert.assertNotNull(handler.event);
	}
	
	@Test
	public void shouldPublishGCEventWhenAgeIsNotPresent()
	{
		final Handler handler = new Handler();
		EventPublisher.instance().register(handler);
		
		final List<String> lines = Arrays.asList("2012-11-14T20:43:06.188+0100: 145.144: [GC 145.144: [ParNew",
		": 2285353K->285767K(2621440K), 0.0784930 secs] 2285353K->285767K(8912896K), 0.0785990 secs] [Times: user=1.10 sys=0.06, real=0.08 secs]");
		
		final ParNewParser parser = new ParNewParser();
		
		for(final String line : lines)
		{
			parser.consume(line);
		}
		
		Assert.assertNotNull(handler.event);
	}
	
	@Test
	public void shouldPublishGCEventWithLinesBeforeAndAfterGCEntry()
	{
		final Handler handler = new Handler();
		EventPublisher.instance().register(handler);
		
		
		final List<String> lines = Arrays.asList(
		"This line is not part of the ParNew entry",
		"2012-11-14T20:43:06.188+0100: 145.144: [GC 145.144: [ParNew",
		"Desired survivor size 268435456 bytes, new threshold 15 (max 15)",
		"- age   1:   86678936 bytes,   86678936 total",
		"- age   2:   54729488 bytes,  141408424 total",
		"- age   3:   70542472 bytes,  211950896 total",
		": 2285353K->285767K(2621440K), 0.0784930 secs] 2285353K->285767K(8912896K), 0.0785990 secs] [Times: user=1.10 sys=0.06, real=0.08 secs]",
		"Also this line is not part of the ParNew entry");
		
		final ParNewParser parser = new ParNewParser();
		
		for(final String line : lines)
		{
			parser.consume(line);
		}
		
		Assert.assertNotNull(handler.event);
	}
	
	
	
	@Test
	public void shouldNotPublishGCEvent()
	{
		final Handler handler = new Handler();
		EventPublisher.instance().register(handler);

		
		//Entries are not ParNew logs
		final List<String> lines = Arrays.asList("2012-11-14T20:43:06.188+0100: 145.144: [GC 145.144: [XYZ",
		"Desired survivor size 268435456 bytes, new threshold 15 (max 15)",
		": 2285353K->285767K(2621440K), 0.0784930 secs] 2285353K->285767K(8912896K), 0.0785990 secs] [Times: user=1.10 sys=0.06, real=0.08 secs]");
		
		final ParNewParser parser = new ParNewParser();
		
		for(final String line : lines)
		{
			parser.consume(line);
		}
		
		Assert.assertNull(handler.event);
	}
	
	public static class Handler {
		GCEvent event = null;
		
		@Subscribe public void handleEvent(final GCEvent event)
		{
			this.event = event;
		}
	};
}
