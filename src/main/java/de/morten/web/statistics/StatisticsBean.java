package de.morten.web.statistics;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.collect.Lists;

import de.morten.model.AnalyseResult;
import de.morten.model.GCEvent;
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
		for(final Map.Entry<String, List<GCEvent>> entry : result.getEvents().entrySet()) {
			final Statistics s = new Statistics(entry.getKey(), entry.getValue());
			stats.add(s);
		}
		return stats;
	}
	
	/**
	 * Collection of statistic information provided to the front end.
	 * 
	 * @author Christian Bannes
	 */
	public static class Statistics {
		private final String name;
		private final List<GCEvent> events;
		
		/**
		 * @param name the name of the gc events
		 * @param events all occurrences of the event of this type
		 */
		public Statistics(final String name, final List<GCEvent> events) {
			this.name = name;
			this.events = events;
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
			return 0;
		}
		
		/**
		 * Returns the accumulated seconds of the gc event of this type
		 * 
		 * @return the total secs of this gc event
		 */
		public int getTotalGCSecs() {
			return 0;
		}
		
		/**
		 * Returns the accumulated seconds of the gc event of this type in percent
		 * (compared to all other gc events)
		 * 
		 * @return the total secs of this gc event in percent
		 */
		public double getTotalGCSecsPercent() {
			return 0;
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
			return 0;
		}
		
		/**
		 * Returns the time in secs of the max gc event
		 * @return max in secs
		 */
		public int getMax() {
			return 0;
		}
		
		/**
		 * Returns average  time in secs of all gc events of this type
		 * 
		 * @return avg in secs
		 */
		public int getAvg() {
			return 0;
		}
		
		/**
		 * Returns median  time in secs of all gc events of this type
		 * 
		 * @return median in secs
		 */
		public int getMedian() {
			return 0;
		}
		
		/**
		 * Returns the standard deviation of all gc events of this type
		 * 
		 * @return the standard deviation in secs
		 */
		public double getStandardDeviation() {
			return 0;
		}
		
	}
}
