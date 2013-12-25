package de.morten.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.http.annotation.ThreadSafe;

import de.morten.model.AnalyseResult;




@Named
@ThreadSafe
@SessionScoped
public class Chart implements Serializable {
	private static final long serialVersionUID = 6193940577455506169L;
	private AnalyseResult result;

	public String show(final AnalyseResult result) {
		this.result = result;
		
		return "showChart?faces-redirect=true";
	}

	public AnalyseResult getResult() {
		return this.result;
	}
	
}
