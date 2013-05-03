package de.morten.web;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

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
		DashboardColumn column3 = new DefaultDashboardColumn();
		
		column1.addWidget("sports");
		column1.addWidget("finance");
		
		column2.addWidget("lifestyle");
		column2.addWidget("weather");
		
		column3.addWidget("politics");
		column3.addWidget("note");
		column3.addWidget("ccnote");

		model.addColumn(column1);
		model.addColumn(column2);
		model.addColumn(column3);
	}
	
	public DashboardModel getModel()
	{
		return this.model;
	}

}
			