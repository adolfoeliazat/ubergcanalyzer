package de.morten.identityaccess;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@ApplicationScoped
@Named
public class Users {
	
	public User getCurrentUser() {
		final String name = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
		return name != null? new User(name) : new User("anonymous");
	}
	

	public String logout() {
		final HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if(session != null)session.invalidate();
		return "dashboard.xhtml?faces-redirect=true";
	}
}
