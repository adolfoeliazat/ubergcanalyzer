package de.morten.model.parser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.morten.model.gc.AnalyseResult;
import de.morten.model.gc.GCEvent;
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
		final CorrelationId id = new CorrelationId("test1id");
		gcParser.parse(id, reader);
		
		assertThat(this.gcLogCollector.getParsedLogFile(id), is(notNullValue()));
	}

	
	@Test
	public void shouldParseLargeGCLogFile() throws IOException {
		final BufferedReader reader = Files.newBufferedReader(Paths.get("src/test/resources/cmsGcBigLog.log"));
		final CorrelationId id = new CorrelationId("test2id");
		gcParser.parse(id, reader);
		
		assertThat(this.gcLogCollector.getParsedLogFile(id), is(notNullValue()));
	}
	
	@Test
	public void shouldContain2InitialMarkEvents() throws IOException {
		final BufferedReader reader = Files.newBufferedReader(Paths.get("src/test/resources/2initialMarkGCs.log"));
		final CorrelationId id = new CorrelationId("test3id");
		gcParser.parse(id, reader);
		
		assertThat(this.gcLogCollector.getParsedLogFile(id), is(notNullValue()));
	
		final AnalyseResult analyzedLogFile = this.gcLogCollector.getParsedLogFile(id);
		final List<GCEvent> initialMarkEvents = analyzedLogFile.getEvents().get("InitialMark");
		
		assertThat(initialMarkEvents.size(), is(Integer.valueOf(2)));
	}
	
	@Test
	public void shouldContain2CMSMarkEvents() throws IOException {
		final BufferedReader reader = Files.newBufferedReader(Paths.get("src/test/resources/2cmsPhases.log"));
		final CorrelationId id = new CorrelationId("test5id");
		gcParser.parse(id, reader);
		
		assertThat(this.gcLogCollector.getParsedLogFile(id), is(notNullValue()));
	
		final AnalyseResult analyzedLogFile = this.gcLogCollector.getParsedLogFile(id);
		final List<GCEvent> initialMarkEvents = analyzedLogFile.getEvents().get("InitialMark");
		
		assertThat(initialMarkEvents.size(), is(Integer.valueOf(2)));
	}
}
