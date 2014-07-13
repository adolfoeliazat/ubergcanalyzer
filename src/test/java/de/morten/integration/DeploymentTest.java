package de.morten.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.jboss.shrinkwrap.resolver.api.maven.Maven;
//import org.jboss.shrinkwrap.resolver.impl.maven.coordinate.MavenDependencyImpl;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.morten.model.MinorGCEvent;
import de.morten.model.parnew.ParNewParser;
import de.morten.model.parser.ActiveGCParser;
import de.morten.model.task.Message;

@RunWith(Arquillian.class)
public class DeploymentTest {

	@Deployment
	public static WebArchive createDeployment() {
		
		//File[] files = Maven.configureResolver().loadPomFromFile("pom.xml")
        //        .importRuntimeDependencies().resolve().withTransitivity().asFile();
		
		return ShrinkWrap.create(WebArchive.class, "sample.war")
			//	.addAsLibraries(files)
				.setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
				.addClass(ParNewParser.class)
			.addClass(MinorGCEvent.class)
			.addAsWebInfResource(new StringAsset("<faces-config version=\"2.0\"/>"), "faces-config.xml")             
			.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
			
			
	}
	
	@Inject @ActiveGCParser ParNewParser parser;
	
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

		final ParNewParser parser = new ParNewParser();
		
		for(final String line : lines)
		{
			parser.consume(new Message(line));
		}
		
	}
}
