package de.morten.model;

import java.util.List;
import java.util.Map;


public class GCStatistics {
	private int min;
	private int max;

	public GCStatistics(final List<GCEvent> gcEvents) {
	}
	
	public int getMin() {
		return this.min;
	}
	
	public int getMax() {
		return this.max;
	}
	
}
