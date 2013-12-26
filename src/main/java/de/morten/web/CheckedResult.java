package de.morten.web;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.google.common.collect.Maps;

import de.morten.model.AnalyseResult;
import de.morten.model.GCEvent;

@Named
@SessionScoped
public class CheckedResult implements Serializable {
	private static final long serialVersionUID = -8041075812869720011L;
	private AnalyseResult analyseResult;
	
	public CheckedResult() {
		final Map<String, List<GCEvent>> empty = Maps.newHashMap();
		this.analyseResult = new AnalyseResult("empty", empty);
	}
	
	public void set(final AnalyseResult analyseResult) {
		this.analyseResult = analyseResult;
	}
	
	
	public AnalyseResult get() {
		return this.analyseResult;
	}
	
}
