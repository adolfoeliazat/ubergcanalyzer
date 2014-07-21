package de.morten.model.parser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.morten.model.gc.GcLogCollector;
import de.morten.model.message.CorrelationId;


@RunWith(Arquillian.class)
public class GCParserTest {

	@Deployment
	public static JavaArchive deployment() {
		return ShrinkWrap.create(JavaArchive.class)
					.addClasses(GCParser.class, GcLogCollector.class)
					.addPackages(true, "de.morten.model.parser")
					.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Inject GCParser gcParser;
	@Inject GcLogCollector gcLogCollector;
	
	@Test
	public void shouldParseGCLogFile() throws IOException {
		final BufferedReader reader = Files.newBufferedReader(Paths.get("src/test/resources/cmsGcLog.log"));
		gcParser.parse(new CorrelationId("junit-test"), reader);
		
		assertThat(this.gcLogCollector.getAllParsedLogFiles().size(), is(Integer.valueOf(1)));
	}

	
	@Test
	public void shouldParseLargeGCLogFile() throws IOException {
		final BufferedReader reader = Files.newBufferedReader(Paths.get("src/test/resources/cmsGcBigLog.log"));
		gcParser.parse(new CorrelationId("junit-test"), reader);
		
		assertThat(this.gcLogCollector.getAllParsedLogFiles().size(), is(Integer.valueOf(1)));
	}
}
