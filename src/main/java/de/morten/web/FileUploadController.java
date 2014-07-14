package de.morten.web;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import de.morten.model.message.CorrelationId;
import de.morten.model.parser.GCParser;

/**
 * Controller to upload and parse a gc log file
 * 
 * @author Christian Bannes
 */
@RequestScoped
@Named
public class FileUploadController {
	@Inject GCParser parser;
	
    public void upload(FileUploadEvent event) {
    	try {
			final UploadedFile file = event.getFile();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputstream()));
			
			this.parser.parse(new CorrelationId(file.getFileName()), reader);
			
			FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		FacesMessage msg = new FacesMessage();
    		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    		msg.setSummary("Unable to upload file " + event.getFile().getFileName());
    		msg.setDetail(e.getMessage());
    		FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
	}
    
}
                    