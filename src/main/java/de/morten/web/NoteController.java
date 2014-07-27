package de.morten.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;





@Named("noteController")
@SessionScoped
public class NoteController implements Serializable {
	private static final long serialVersionUID = 7199060576374141490L;
	String text = "Enter your notes...";
	private String fontFamily = "Satisfy";
	private int fontSize = 12;

	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	
	public String getFontFamily()
	{
		return this.fontFamily;
	}

	public void setFontFamily(final String fontFamily)
	{
		this.fontFamily = fontFamily;
	}
	
	public int getFontSize()
	{
		return this.fontSize;
	}

	public void setFontSize(final int fontSize)
	{
		this.fontSize = fontSize;
	}
}
