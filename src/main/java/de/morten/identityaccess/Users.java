package de.morten.identityaccess;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@ApplicationScoped
@Named
public class Users {
	
	@Produces
	public User getCurrentUser() {
		final String name = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
		return name != null? new User(name) : new User("anonymous");
	}
}
