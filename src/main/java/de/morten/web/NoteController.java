package de.morten.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

@Named("noteController")
@SessionScoped
public class NoteController implements Serializable {
	private static final long serialVersionUID = 7199060576374141490L;
	String text = "predefined text";

	public String getText() {
		System.out.println("get text");
		return this.text;
	}
	
	public void setText(String text) {
		System.out.println("setting text");
		this.text = text;
	}
	
	public void textChanged(ValueChangeEvent event) {
		System.out.println("text changed");
		this.text = (String)event.getNewValue();
	}
}
