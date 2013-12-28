package de.morten.web.statistics;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.collect.Lists;

import de.morten.model.AnalyseResult;
import de.morten.model.GCEvent;
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
		
		final List<Statistics> stats = Lists.newArrayList();
		final List<GCEvent> allEvents = flatten(result.getEvents().values());
		for(final Map.Entry<String, List<GCEvent>> entry : result.getEvents().entrySet()) {
			final Statistics s = new Statistics(entry.getKey(), entry.getValue(), allEvents);
			stats.add(s);
		}
		return stats;
	}
	
	
	
	private List<GCEvent> flatten(Collection<List<GCEvent>> values) {
		final List<GCEvent> flatList = Lists.newArrayList();
		for(final Collection<GCEvent> events : values) {
			flatList.addAll(events);
		}
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
		private final List<Integer> secs = Lists.newArrayList();
		private final int totalSecs;
		
		
		/**
		 * @param name the name of the gc events
		 * @param events all occurrences of the event of this type
		 * @param allEvents occurences of all events
		 */
		public Statistics(final String name, final List<GCEvent> events, final List<GCEvent> allEvents) {
			this.name = name;
			this.events = events;
			this.allEvents = allEvents;
			
			final List<Integer> secs = Lists.newArrayList();
			for(final GCEvent event : this.events)
			{
				secs.add((int)event.getTimeStats().getDuration());
			}
			
			this.totalSecs = sumSecs(events);
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
			return this.allEvents.isEmpty()? 0 :this.events.size() / (double)this.allEvents.size();
		}
		
		/**
		 * Returns the accumulated seconds of the gc event of this type
		 * 
		 * @return the total secs of this gc event
		 */
		public int getTotalGCSecs() {
			return this.totalSecs;
			
		}
		
		private int sumSecs(final List<GCEvent> vals)
		{
			int total = 0;
			for(final GCEvent val : vals) {
				total += val.getTimeStats().getDuration();
			}
			
			return total;
		}
		
		/** 
		 * Returns the accumulated seconds of the gc event of this type in percent
		 * (compared to all other gc events)
		 * 
		 * @return the total secs of this gc event in percent
		 */
		public double getTotalGCSecsPercent() {
			final double secsAll = sumSecs(allEvents);
			
			return secsAll == 0? 0 : this.totalSecs / secsAll;
		}
		
		/**
		 * Returns the percentage of this gc event runs compared to the total
		 * application time
		 * 
		 * @return the overhead in percent
		 */
		public double getOverhead() {
			return 0;
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
		public int getMin() {
			return Collections.min(secs);
		}
		
		/**
		 * Returns the time in secs of the max gc event
		 * @return max in secs
		 */
		public int getMax() {
			return Collections.max(secs);
		}
		
		/**
		 * Returns average  time in secs of all gc events of this type
		 * 
		 * @return avg in secs
		 */
		public double getAvg() {
			return Stats.average(secs);
		}
		
		/**
		 * Returns median  time in secs of all gc events of this type
		 * 
		 * @return median in secs
		 */
		public int getMedian() {
			return Stats.median(secs);
		}
		
		/**
		 * Returns the standard deviation of all gc events of this type
		 * 
		 * @return the standard deviation in secs
		 */
		public double getStandardDeviation() {
			return Stats.standardDeviation(secs);
		}
		
	}
}
