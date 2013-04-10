package de.morten.web;


import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;

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
    	this.linearModel = new CartesianChartModel();
    	final AnalyseResult result = chart.getResult();
    	final Map<String, List<GCEvent>> events = result.getEvents();
    	
    	System.out.println("====>" + events.keySet().size());
    	for(final Map.Entry<String, List<GCEvent>> entry : events.entrySet()) {
    		System.out.println("creating new series " + entry.getKey());
    		final LineChartSeries series = new LineChartSeries(entry.getKey());
    		/*
    		int i = 0;
            for(final GCEvent event : entry.getValue()) {
            	series.set(i + (int)(3*Math.random()), event.getTimeStats().getDuration()*1000);
            }*/
    		series.set(1, 10);
    		series.set(2, 5);
    		series.set(10, 10);
    		series.set(20, 30);
    		
    		
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
                    