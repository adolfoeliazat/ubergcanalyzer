package de.morten.web;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.primefaces.component.chart.bar.BarChart;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

@Named("myDashboard")
@RequestScoped
public class MyDashBoard implements Serializable {

	private DashboardModel model;
	
	public MyDashBoard() {
		model = new DefaultDashboardModel();
		DashboardColumn column1 = new DefaultDashboardColumn();
		DashboardColumn column2 = new DefaultDashboardColumn();
		
		column1.addWidget("fileupload");
		//column1.addWidget("chart");
		column2.addWidget("ccnote");
		//column2.addWidget("data");
		
//		column3.addWidget("politics");
//		column3.addWidget("note");
//		column3.addWidget("ccnote");

		model.addColumn(column1);
		model.addColumn(column2);
		
	}
	
	public DashboardModel getModel()
	{
		return this.model;
	}
	
	public void createNewPanel(final ActionEvent actionEvent)
	{
		final Panel panel = new Panel();
		final String id = "panel-" + String.valueOf((int)(Math.random()*100));
		panel.setId(id);
		panel.setHeader("test");
		panel.setClosable(true);
		
		ChartBean chartBean = new ChartBean();
		final BarChart barChart = new BarChart();
		barChart.setValue(chartBean.getLinearModel());
		
		panel.getChildren().add(barChart);
		
		final UIComponent component = actionEvent.getComponent().findComponent("dashboard");
		component.getChildren().add(panel);
		
		this.model.getColumn(0).addWidget(id);
	}
	

	


}
			