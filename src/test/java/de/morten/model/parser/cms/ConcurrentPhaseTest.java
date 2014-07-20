package de.morten.model.parser.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
public class ConcurrentPhaseTest {

	@Deployment
	public static JavaArchive deploy() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClasses(ConcurrentMarkParser.class, PreCleanParser.class, ConcurrentSweepParser.class, ConcurrentResetParser.class,
						ConcurrentPhaseEvent.class, GCEventObserverForJunitTests.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Inject @ActiveGCParser ConcurrentMarkParser concurrentMarkParser;
	@Inject @ActiveGCParser PreCleanParser precleanParser;
	@Inject @ActiveGCParser ConcurrentSweepParser concurrentSweepParser;
	@Inject @ActiveGCParser ConcurrentResetParser concurrentResetParser;
	@Inject GCEventObserverForJunitTests observer;
	
	@After
	public void clearObserver() {
		this.observer.clear();
	}
	
	@Test
	public void testConcurrentMark() {
			final String line = "2012-11-14T21:01:38.684+0100: 1257.639: [CMS-concurrent-mark: 1.142/1.148 secs] [Times: user=6.76 sys=0.15, real=1.14 secs]";	
			concurrentMarkParser.consume(new Message(line));
			assertThat(observer.events().size(), is(Integer.valueOf(1)));
	}
	
	@Test
	public void testPreclean() {
		
		final String line = "2012-11-14T21:01:38.704+0100: 1257.660: [CMS-concurrent-preclean: 0.021/0.021 secs] [Times: user=0.02 sys=0.00, real=0.02 secs]";
		precleanParser.consume(new Message(line));
		assertThat(observer.events().size(), is(Integer.valueOf(1)));
	}
	
	@Test
	public void testConcurrentSweep() {
		final String line = "2012-11-14T21:01:54.081+0100: 1273.037: [CMS-concurrent-sweep: 3.260/3.382 secs] [Times: user=5.65 sys=0.10, real=3.38 secs]";
		concurrentSweepParser.consume(new Message(line));
		assertThat(observer.events().size(), is(Integer.valueOf(1)));
	}
	
	@Test
	public void concurrentReset() {
		final String line = "2012-11-14T21:01:54.115+0100: 1273.071: [CMS-concurrent-reset: 0.034/0.034 secs] [Times: user=0.02 sys=0.03, real=0.04 secs]";
		concurrentResetParser.consume(new Message(line));
		assertThat(observer.events().size(), is(Integer.valueOf(1)));
	}
}
