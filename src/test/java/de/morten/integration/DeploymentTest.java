package de.morten.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
//import org.jboss.shrinkwrap.resolver.api.maven.Maven;
//import org.jboss.shrinkwrap.resolver.impl.maven.coordinate.MavenDependencyImpl;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.morten.model.parnew.ParNewParser;
import de.morten.model.parser.ActiveGCParser;
import de.morten.model.task.Message;

@RunWith(Arquillian.class)
public class DeploymentTest {

	@Deployment
	public static JavaArchive createDeployment() {
	      return ShrinkWrap.create(JavaArchive.class, "sample.jar")
	              .addClasses(ParNewParser.class, GCEventObserverForJunitTests.class)
	              .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Inject @ActiveGCParser ParNewParser parser;
	@Inject GCEventObserverForJunitTests observer;
	
	@After
	public void clearObserver() {
		this.observer.clear();
	}
	
	@Test
	public void testParser() {
		assertThat(parser, not(nullValue()));
		
		final List<String> lines = Arrays.asList("2012-11-14T20:43:18.599+0100: 157.554: [GC 157.555: [ParNew",
        "Desired survivor size 268435456 bytes, new threshold 4 (max 15)",
        "- age   1:   87070344 bytes,   87070344 total",
        "- age   2:   85107184 bytes,  172177528 total",
        "- age   3:   73421224 bytes,  245598752 total",
        "- age   4:   54715768 bytes,  300314520 total",
        ": 2519259K->430351K(2621440K), 0.3845220 secs] 2519259K->507304K(8912896K), 0.3846240 secs] [Times: user=2.33 sys=0.26, real=0.38 secs] "); 

		
		for(final String line : lines)
		{
			parser.consume(new Message(line));
		}
		
		assertThat(observer.events.size(), is(Integer.valueOf(1)));
	}
	
	@Test
	public void shouldPublishGCEventWhenAgeIsNotPresent()
	{
		final List<String> lines = Arrays.asList("2012-11-14T20:43:06.188+0100: 145.144: [GC 145.144: [ParNew",
		": 2285353K->285767K(2621440K), 0.0784930 secs] 2285353K->285767K(8912896K), 0.0785990 secs] [Times: user=1.10 sys=0.06, real=0.08 secs]");
		
		for(final String line : lines)
		{
			parser.consume(new Message(line));
		}
		
		assertThat(observer.events.size(), is(Integer.valueOf(1)));
	}
	
	@Test
	public void shouldPublishGCEventWithLinesBeforeAndAfterGCEntry()
	{
		final List<String> lines = Arrays.asList(
		"This line is not part of the ParNew entry",
		"2012-11-14T20:43:06.188+0100: 145.144: [GC 145.144: [ParNew",
		"Desired survivor size 268435456 bytes, new threshold 15 (max 15)",
		"- age   1:   86678936 bytes,   86678936 total",
		"- age   2:   54729488 bytes,  141408424 total",
		"- age   3:   70542472 bytes,  211950896 total",
		": 2285353K->285767K(2621440K), 0.0784930 secs] 2285353K->285767K(8912896K), 0.0785990 secs] [Times: user=1.10 sys=0.06, real=0.08 secs]",
		"Also this line is not part of the ParNew entry");
		
		for(final String line : lines)
		{
			parser.consume(new Message(line));
		}
		
		assertThat(observer.events.size(), is(Integer.valueOf(1)));
	}
	
	
	
	
	@Test
	public void shouldNotPublishGCEvent()
	{
		
		//Entries are not ParNew logs
		final List<String> lines = Arrays.asList("2012-11-14T20:43:06.188+0100: 145.144: [GC 145.144: [XYZ",
		"Desired survivor size 268435456 bytes, new threshold 15 (max 15)",
		": 2285353K->285767K(2621440K), 0.0784930 secs] 2285353K->285767K(8912896K), 0.0785990 secs] [Times: user=1.10 sys=0.06, real=0.08 secs]");
		
		for(final String line : lines)
		{
			parser.consume(new Message(line));
		}
		
		assertThat(observer.events.size(), is(Integer.valueOf(0)));
	}
	
	
}
