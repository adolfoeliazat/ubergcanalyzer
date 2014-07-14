package de.morten.model.parnew;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;

import de.morten.model.GCEvent;

/**
 * Only for junit testing
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
