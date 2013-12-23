/**
 * jqPlot extender function to configure primefaces charts
 */
function chartExtender() {
    this.cfg.seriesDefaults = {
        shadowDepth: 5,
        barWidth:1,
        lineWidth:1,
        renderer:$.jqplot.BarRenderer,
        rendererOptions: {
        	barWidth: 3,
        	highlightMouseOver: true,
        	shadowOffset:1,
        	waterfall: false
        }
    };
    
    this.cfg.axes = {
        xaxis: {
        	max: 1200,
        	numberTicks: 11
        }
      };
    
    this.cfg.axesDefaults = {
    		min : 0
    };
}