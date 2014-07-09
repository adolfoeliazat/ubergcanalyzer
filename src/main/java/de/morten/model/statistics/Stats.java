package de.morten.model.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;



/**
 * Provides methods for statistics calculation
 * 
 * @author Christian Bannes
 */
public class Stats {
	
	
	/**
	 * Private default constructor
	 */
	private Stats() {
		//prevent instantiation
	}
	
	/**
	 * Returns the median of the given collection
	 * 
	 * @param values the values 
	 * @return the median
	 */
	public static int median(final Collection<Integer> values)
	{
		if(values.isEmpty()) return 0;
		
		final List<Integer> list = new ArrayList<Integer>(values);
		Collections.sort(list);
		
		final int mid = values.size() / 2;
		return list.get(mid);
	}
	
	/**
	 * Calculates the variance over the given values
	 * 
	 * @param values the values
	 * @return the variance
	 */
	public static double variance(final Collection<Integer> values) {
		final double avg = Stats.average(values);
		
		if(values.isEmpty()) return 0;
		
		double sum = 0;
		for(final int val : values)
		{
			sum += Math.pow((val - avg), 2);
			
		}
		return sum / values.size();
	}
	
	/**
	 * Calculates the standard deviation over the given values
	 * 
	 * @param values thee values
	 * @return the standard deviation
	 */
	public static double standardDeviation(final Collection<Integer> values) {
		return Math.sqrt(Stats.variance(values));
	}
	
	/**
	 * Calculates the average value over the given values
	 * 
	 * @param values the values
	 * @return the average
	 */
	public static double average(final Collection<Integer> values)
	{
		if(values.isEmpty()) return 0;
		
		int sum = 0;
		for(final int value : values)
		{
			sum += value;
		}
		
		return sum / values.size();
	}
}
