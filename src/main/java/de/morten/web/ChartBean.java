package de.morten.web;


import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;

import com.google.common.collect.Maps;

import de.morten.model.AnalyseResult;
import de.morten.model.GCEvent;


@Named
@RequestScoped
public class ChartBean implements Serializable {

	private static final long serialVersionUID = -1676780208576356392L;
	private CartesianChartModel linearModel;
	@Inject
	private Chart chart;
    
	public ChartBean() {
        //createLinearModel();
	}
	
	
	
	

    public CartesianChartModel getLinearModel() {
    	if(this.linearModel == null)
    		createLinearModel();
        return linearModel;
    }

    public  void createLinearModel() {
    	final LineChartSeries series = new LineChartSeries("test");
    	series.set(0, 1);
    	series.set(1, 10);
    	series.set(2, 3);
    	series.set(3, 20);
    	series.set(4, 5);
    	series.set(5, 30);
    	
    	this.linearModel = new CartesianChartModel();
    	this.linearModel.addSeries(series);
    	
    }
    public  void createLinearModel_() {
    	this.linearModel = new CartesianChartModel();
    	final AnalyseResult result = chart.getResult();
    	final Map<String, List<GCEvent>> events = result.getEvents();
    	
    	System.out.println("====>" + events.keySet().size());
    	for(final Map.Entry<String, List<GCEvent>> entry : events.entrySet()) {
    		System.out.println("creating new series " + entry.getKey());
    		final LineChartSeries series = new LineChartSeries(entry.getKey());
    		
    		
    		//i want time -> occurences
    		final TreeMap<Integer, Integer> stats = new TreeMap<Integer, Integer>();
            for(final GCEvent event : entry.getValue()) {
            	final int duration = (int)(event.getTimeStats().getDuration()*1000);
            	if(stats.get(duration) == null)
            		stats.put(duration, 0);
            		
            	stats.put(duration, stats.get(duration)+1);
            }
            
            
            
           // for(final Map.Entry<Integer, Integer> e : stats.entrySet())
            for(int i = 1; i < stats.lastKey(); i++)
            {
            	int val = stats.get(i) == null? 0 : stats.get(i);
            	series.set(i, val);
            }
    		
            /*
            for(final GCEvent event : entry.getValue()) {
            	final double duration =  event.getTimeStats().getDuration()*1000;
            	final int d = (int)duration;
            	System.out.println("adding: (" + i + "," + d + ")");
            	series.set(i, d);
            	i++;
            }
            */
    		
    		
//    		for(int i = 0; i < 10000; i++)
//    		{
//    			double d  = Math.random()*10;
//    			System.out.println(d);
//    			
//    			series.set(i, (int)d);
//    		}
    		/*
    		series.set(1, 10);
    		series.set(2, 5);
    		series.set(10, 10);
    		series.set(20, 30);
    		*/
    		
            System.out.println("adding new series " + series.getLabel());
            this.linearModel.addSeries(series);
    	}
    }
    

    
    /*
    private void createLinearModel_() {
        linearModel = new CartesianChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");

        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(3, 3);
        series1.set(4, 6);
        series1.set(5, 8);

        final AnalyseResult result = this.chart.getResult();
        linearModel.addSeries(series1);

        
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Series 2");
        series2.setMarkerStyle("diamond");

        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);
        linearModel.addSeries(series2);
    }
    */
}
                    