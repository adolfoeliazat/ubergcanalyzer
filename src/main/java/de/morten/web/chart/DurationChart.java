package de.morten.web.chart;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.IntStream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.LineChartSeries;

import de.morten.model.gc.GCEvent;
import de.morten.web.CheckedResult;




@Named
@RequestScoped
public class DurationChart implements  Chart {
	@Inject private CheckedResult checkedResult;
	private boolean showOnlyStopTheWorld = false;
	
	public ChartModel getModel() {
	   	final CartesianChartModel linearModel = new CartesianChartModel();
	   	
    	final Map<String, List<GCEvent>> events = this.checkedResult.get().getEvents();
    	
    	if(events.isEmpty()) {
    		final LineChartSeries empty = new LineChartSeries("empty");
    		empty.set(0, 0);
    		linearModel.addSeries(empty);
    	}
    	
    	events.entrySet()
    		.stream()
    		.filter(entry -> filterStopTheWorldEntries(entry))
    		.forEach(entry -> {
    			final LineChartSeries series = new LineChartSeries(entry.getKey());

        		final TreeMap<Integer, Integer> stats = new TreeMap<Integer, Integer>();
        		
        		entry.getValue().forEach(event -> {
        			final int duration = (int)(event.getTimeStats().getDuration()*1000);
        			stats.putIfAbsent(duration, 0);
        			stats.put(duration, stats.get(duration)+1);
        		});
        		
        		IntStream.range(1, stats.lastKey()).forEach( i -> {
                	int val = stats.get(i) == null? 0 : stats.get(i);
                	series.set(i, val);
        		});
        		
                linearModel.addSeries(series);
    	});
    	
    	return linearModel;
	}


	private boolean filterStopTheWorldEntries(Entry<String, List<GCEvent>> entry) {
		return !showOnlyStopTheWorld 	 //if not active, don't filter
		|| entry.getValue().isEmpty()    
		|| entry.getValue().iterator().next().isStopTheWorld(); //sufficient to only check the first entry because each list
																//contains the same type of gc events
	}

	public void setShowOnlyStopTheWorld(final boolean showOnlyStopTheWorld) {
		this.showOnlyStopTheWorld = showOnlyStopTheWorld;
	}
	
	
	public boolean isShowOnlyStopTheWorld() {
		return this.showOnlyStopTheWorld;
	}
	
	
}
