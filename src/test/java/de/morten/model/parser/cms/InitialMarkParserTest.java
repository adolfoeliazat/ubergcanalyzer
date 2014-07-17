package de.morten.model.parser.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.morten.model.message.Message;
import de.morten.model.parser.ActiveGCParser;
import de.morten.model.parser.GCEventObserverForJunitTests;


@RunWith(Arquillian.class)
public class InitialMarkParserTest {

	@Deployment
	public static JavaArchive deploy() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClasses(InitialMarkParser.class, GCEventObserverForJunitTests.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Inject @ActiveGCParser InitialMarkParser parser;
	@Inject GCEventObserverForJunitTests  observer;
	
	@After
	public void after() {
		this.observer.clear();
	}
	
	@Test
	public void singleLineShouldMatch() {
		final String initialMark = "2012-11-14T21:01:37.171+0100: 1256.127: [GC [1 CMS-initial-mark: 3166449K(6291456K)] 3644014K(8912896K), 0.3643600 secs] [Times: user=0.36 sys=0.01, real=0.37secs]";
		this.parser.consume(new Message(initialMark));
		
		assertThat(this.observer.events().size(), is(Integer.valueOf(1)));
	}
	
	
	@Test
	public void multiLineShouldMatch() {
		final List<String> initialMark = Arrays.asList(
				"some line before",
				"2012-11-14T21:01:37.171+0100: 1256.127: [GC [1 CMS-initial-mark: 3166449K(6291456K)] 3644014K(8912896K), 0.3643600 secs] [Times: user=0.36 sys=0.01, real=0.37secs]",
				"some line after");
			
		for(final String line : initialMark) {
			parser.consume(new Message(line));
		}
		
		assertThat(observer.events().size(), is(Integer.valueOf(1)));
	}
}
