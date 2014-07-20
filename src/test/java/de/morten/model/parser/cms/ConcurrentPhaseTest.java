package de.morten.model.parser.cms;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(Arquillian.class)
public class ConcurrentPhaseTest {

	@Deployment
	public static JavaArchive deploy() {
		return ShrinkWrap.create(JavaArchive.class)
			//	.addClass(ConcurrentPhaseParser.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	
	
	@Test
	public void testConcurrentMark() {
			final String line = "2012-11-14T21:01:38.684+0100: 1257.639: [CMS-concurrent-mark: 1.142/1.148 secs] [Times: user=6.76 sys=0.15, real=1.14 secs]";	
	}
	
	@Test
	public void testPreclean() {
		
		final String line = "2012-11-14T21:01:38.704+0100: 1257.660: [CMS-concurrent-preclean: 0.021/0.021 secs] [Times: user=0.02 sys=0.00, real=0.02 secs]";
	}
	
	@Test
	public void testConcurrentSweep() {
		final String line = "2012-11-14T21:01:54.081+0100: 1273.037: [CMS-concurrent-sweep: 3.260/3.382 secs] [Times: user=5.65 sys=0.10, real=3.38 secs]";
	}
	
	public void concurrentReset() {
		final String line = "2012-11-14T21:01:54.115+0100: 1273.071: [CMS-concurrent-reset: 0.034/0.034 secs] [Times: user=0.02 sys=0.03, real=0.04 secs]";
	}
}
