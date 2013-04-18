package de.morten.web;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;





@Named("noteController")
@SessionScoped
public class NoteController implements Serializable {
	private static final long serialVersionUID = 7199060576374141490L;
	String text = "Enter your notes...";
	private String fontFamily = "Satisfy";
	private int fontSize = 16;

	public String getText() {
		System.out.println("get text");
		return this.text;
	}
	
	public void setText(String text) {
		System.out.println("setting text");
		this.text = text;
	}
	
	
	public String getFontFamily()
	{
		return this.fontFamily;
	}

	public void setFontFamily(final String fontFamily)
	{
		System.out.println("setting fontfamily:" + fontFamily);
		this.fontFamily = fontFamily;
	}
	
	public int getFontSize()
	{
		System.out.println("get fontsize");
		return this.fontSize;
	}

	public void setFontSize(final int fontSize)
	{
	//	UIComponent component = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance());
	//	org.primefaces.component.selectonelistbox.SelectOneListbox box = (org.primefaces.component.selectonelistbox.SelectOneListbox) component;
	//	box.getSubmittedValue();
		System.out.println("setting fontSize:" + fontSize);
		this.fontSize = fontSize;
	}
}
