package de.morten.integration;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;

import de.morten.model.GCEvent;

/**
 * 
 * @author Christian Bannes
 */
@Singleton
public class GCEventObserverForJunitTests {
	List<GCEvent> events = new ArrayList<>();
	
	public void observeEvemts(@Observes final GCEvent event) {
		this.events.add(event);
	}
	
	public void clear() {
		this.events.clear();
	}
	
	
}
