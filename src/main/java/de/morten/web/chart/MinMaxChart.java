package de.morten.web.chart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;

import de.morten.model.gc.GCEvent;
import de.morten.model.statistics.Stats;
import de.morten.web.CheckedResult;




@Named
@RequestScoped
public class MinMaxChart implements  Chart {
	@Inject private CheckedResult checkedResult;
	
	public ChartModel getModel() { 

	   	final CartesianChartModel linearModel = new CartesianChartModel();
	   	
    	final Map<String, List<GCEvent>> events = this.checkedResult.get().getEvents();
    	
    	if(events.isEmpty()) {
    		final LineChartSeries empty = new LineChartSeries("empty");
    		empty.set("min", 0);
    		empty.set("max", 0);
    		empty.set("median",0);
    		linearModel.addSeries(empty);
    	}
	   	
    	for(final Map.Entry<String, List<GCEvent>> entry : events.entrySet()) {
    		
    		final List<Long> durations = new ArrayList<>();
            for(final GCEvent event : entry.getValue()) {
            	final long duration = (long)(event.getTimeStats().getDuration()*1000);
            	durations.add(duration);
            }
            
            
            final ChartSeries series = new ChartSeries(entry.getKey());
            series.set("min", Collections.min(durations));
            series.set("max", Collections.max(durations));
            series.set("median", Stats.median(durations));
            
    		
            linearModel.addSeries(series);
    	}
    	
    	return linearModel;
	}
	
	
}
