package de.morten.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.primefaces.component.chart.bar.BarChart;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import de.morten.model.gc.AnalyseResult;

@Named("myDashboard")
@SessionScoped
public class MyDashBoard implements Serializable {

	private static final long serialVersionUID = 5416472181361202579L;
	private DashboardModel model;
	
	public MyDashBoard() {
		this.model = new DefaultDashboardModel();
		DashboardColumn column1 = new DefaultDashboardColumn();
		DashboardColumn column2 = new DefaultDashboardColumn();
		
		column1.addWidget("fileupload");
		column1.addWidget("chart");
		column2.addWidget("ccnote");
		column2.addWidget("data");
		
		this.model.addColumn(column1);
		this.model.addColumn(column2);
		
	}
	
	public DashboardModel getModel()
	{
		return this.model;
	}
	
	public void createNewPanel(final ActionEvent actionEvent)
	{
		
		final UIComponent source = actionEvent.getComponent();
		
		final Panel panel = new Panel();
		final String id = "panel-" + String.valueOf((int)(Math.random()*100));
		panel.setId(id);
		panel.setHeader((String)source.getAttributes().get("value"));
		panel.setClosable(true);
		
		final BarChart barChart = new BarChart();
		final AnalyseResult result = (AnalyseResult)source.getAttributes().get("result");
		barChart.setValue(ChartFactory.createModel(result));
		barChart.setExtender("chartExtender");
		
		panel.getChildren().add(barChart);
		
		final UIComponent component = actionEvent.getComponent().findComponent(":main:dashboard");
		component.getChildren().add(panel);
		
		this.model.getColumn(0).addWidget(id);
	}
	

	


}
			