package de.morten.web.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.morten.model.gc.AnalyseResult;
import de.morten.model.gc.GCEvent;
import de.morten.model.statistics.Stats;
import de.morten.web.CheckedResult;


/**
 * Provides statistics information of a gc log for the front end.
 *  
 * @author Christian Bannes
 */
@Named
@RequestScoped
public class StatisticsBean {
	@Inject private CheckedResult checkedResult;
	
	/**
	 * Returns the statistics for a gc logs. Every {@link Statistics} element of the
	 * list represents one type of gc event.
	 * 
	 * @return a list of statistics
	 */
	public List<Statistics> getStats() {
		final AnalyseResult result = checkedResult.get();
		
		final List<GCEvent> allEvents = flatten(result.getEvents().values());
		
		final List<Statistics> stats = result.getEvents().entrySet().stream()
				.map(entry -> new Statistics(entry.getKey(), entry.getValue(), allEvents))
				.collect(Collectors.toList());
		
		return stats;
	}
	
	
	
	private List<GCEvent> flatten(Collection<List<GCEvent>> values) {
		
		final List<GCEvent> flatList = new ArrayList<>();
		values.forEach(flatList::addAll);
		
		return flatList;
	}



	/**
	 * Collection of statistic information provided to the front end.
	 * 
	 * @author Christian Bannes
	 */
	public static class Statistics {
		private final String name;
		private final List<GCEvent> events;
		private final List<GCEvent> allEvents;
		private final List<Long> secs;
		private final double totalSecs;
		private final double totalElapsedTimeSinceMeasurement;
		
		
		/**
		 * @param name the name of the gc events
		 * @param events all occurrences of the event of this type
		 * @param allEvents occurences of all events
		 */
		public Statistics(final String name, final List<GCEvent> events, final List<GCEvent> allEvents) {
			this.name = name;
			this.events = events;
			this.allEvents = allEvents;
			
			this.secs = this.events.stream()
							.map(e -> Math.round(e.getTimeStats().getDuration()))
							.collect(Collectors.toList());
			
			this.totalSecs = sumSecs(events);
			this.totalElapsedTimeSinceMeasurement = calcTotalTime(allEvents);
		}

		private double calcTotalTime(List<GCEvent> events) {
			
			final List<Double> times = events.stream()
					.map(event -> event.getTimeStats().getElappsedTime())
					.collect(Collectors.toList());
			
			return  Collections.max(times)-Collections.min(times);
		}

		/**
		 * Returns the name of this gc event
		 * 
		 * @return the name of this gc event
		 */
		public String getName() {
			return this.name;
		}
		
		/**
		 * Returns the total number of gc events of this gc type
		 * 
		 * @return the number of gc events
		 */
		public int getNum() {
			return this.events.size();
		}
		
		/**
		 * Returns the portion of the occurrences of this gc events compared to all gc events 
		 * 
		 * @return the total number of gc events of this type in percent
		 */
		public double  getNumPercent() {
			return this.allEvents.isEmpty()? 0 :this.events.size() / (double)this.allEvents.size() * 100;
		}
		
		/**
		 * Returns the accumulated seconds of the gc event of this type
		 * 
		 * @return the total secs of this gc event
		 */
		public double getTotalGCSecs() {
			return this.totalSecs;
			
		}
		
		private double sumSecs(final List<GCEvent> vals)
		{
			return vals.stream()
				.map(v -> v.getTimeStats().getDuration())
				.mapToDouble(Double::doubleValue)
				.sum();
		}
		
		/** 
		 * Returns the accumulated seconds of the gc event of this type in percent
		 * (compared to all other gc events)
		 * 
		 * @return the total secs of this gc event in percent
		 */
		public double getTotalGCSecsPercent() {
			final double secsAll = sumSecs(allEvents);
			
			return secsAll == 0? 0 : this.totalSecs / secsAll * 100;
		}
		
		/**
		 * Returns the percentage of this gc event runs compared to the total
		 * application time
		 * 
		 * @return the overhead in percent
		 */
		public double getOverhead() {
			return this.totalElapsedTimeSinceMeasurement == 0? 0 
					: this.totalSecs / this.totalElapsedTimeSinceMeasurement * 100;
		}
		
		/**
		 * Returns the throughput of this gc event runs compared to the total
		 * application time
		 * 
		 * @return the overhead in percent
		 */
		public double getThroughput() {
			return 100-getOverhead();
		}
		
		/**
		 * Returns the time in secs of the min gc event
		 * @return min in secs
		 */
		public long getMin() {
			return this.secs.isEmpty()? 0 : Collections.min(this.secs);
		}
		
		/**
		 * Returns the time in secs of the max gc event
		 * @return max in secs
		 */
		public long getMax() {
			return this.secs.isEmpty()? 0 : Collections.max(this.secs);
		}
		
		/**
		 * Returns average  time in secs of all gc events of this type
		 * 
		 * @return avg in secs
		 */
		public double getAvg() {
			return this.secs.isEmpty()? 0 : Stats.average(this.secs);
		}
		
		/**
		 * Returns median  time in secs of all gc events of this type
		 * 
		 * @return median in secs
		 */
		public long getMedian() {
			return this.secs.isEmpty()? 0 : Stats.median(this.secs);
		}
		
		/**
		 * Returns the standard deviation of all gc events of this type
		 * 
		 * @return the standard deviation in secs
		 */
		public double getStandardDeviation() {
			return this.secs.isEmpty()? 0 : Stats.standardDeviation(this.secs);
		}
		
	}
}
