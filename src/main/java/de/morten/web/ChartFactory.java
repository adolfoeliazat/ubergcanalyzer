package de.morten.web;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

import de.morten.model.AnalyseResult;
import de.morten.model.GCEvent;

public class ChartFactory 
{
	public static CartesianChartModel createModel(final AnalyseResult result)
	{
		
	   	final CartesianChartModel linearModel = new CartesianChartModel();
    	final Map<String, List<GCEvent>> events = result.getEvents();
    	
    	for(final Map.Entry<String, List<GCEvent>> entry : events.entrySet()) {
    		final LineChartSeries series = new LineChartSeries(entry.getKey());
    		
    		
    		final TreeMap<Integer, Integer> stats = new TreeMap<Integer, Integer>();
            for(final GCEvent event : entry.getValue()) {
            	final int duration = (int)(event.getTimeStats().getDuration()*1000);
            	if(stats.get(duration) == null)
            		stats.put(duration, 0);
            		
            	stats.put(duration, stats.get(duration)+1);
            }
            
            for(int i = 1; i < stats.lastKey(); i++)
            {
            	int val = stats.get(i) == null? 0 : stats.get(i);
            	series.set(i, val);
            }
    		
            linearModel.addSeries(series);
    	}
    	
    	return linearModel;
	}
}

