package de.morten.web;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.LineChartSeries;

import de.morten.model.AnalyseResult;
import de.morten.model.GCEvent;




@Named
@SessionScoped
public class Chart implements Serializable {
	private static final long serialVersionUID = 6193940577455506169L;
	//private AnalyseResult result;
	private CartesianChartModel model = new CartesianChartModel();

	public Chart() {
		final LineChartSeries series = new LineChartSeries("empty");
		series.set(0, 0);
		series.set(1, 10);
		series.set(2, 20);
		series.set(3, 30);
		
		final LineChartSeries series2 = new LineChartSeries("second");
		series2.set(0, 0);
		series2.set(1, 15);
		series2.set(2, 25);
		series2.set(3, 10);
		
		
		this.model.addSeries(series);
		this.model.addSeries(series2);
	}
	
	public void show(final AnalyseResult result) {
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
    	
    	this.model = linearModel;
	}
	
	
	public ChartModel getModel() {
		return this.model;
	}
	
}
