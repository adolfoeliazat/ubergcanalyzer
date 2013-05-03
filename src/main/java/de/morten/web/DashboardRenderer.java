package de.morten.web;



import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.renderkit.CoreRenderer;
import org.primefaces.util.WidgetBuilder;

public class DashboardRenderer extends CoreRenderer {
	
	@Override
	public void decode(FacesContext context, UIComponent component) {
        decodeBehaviors(context, component);
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		Dashboard dashboard = (Dashboard) component;
		
		encodeMarkup(context, dashboard);
		encodeScript(context, dashboard);
	}

	protected void encodeMarkup(FacesContext contextr, Dashboard dashboard) throws IOException {
		ResponseWriter writer = contextr.getResponseWriter();
		String clientId = dashboard.getClientId(contextr);
		
		writer.startElement("div", dashboard);
		writer.writeAttribute("id", clientId, "id");
		String styleClass = dashboard.getStyleClass() != null ? Dashboard.CONTAINER_CLASS + " " + dashboard.getStyleClass() : Dashboard.CONTAINER_CLASS;
		writer.writeAttribute("class", styleClass, "styleClass");
		if(dashboard.getStyle() != null) 
            writer.writeAttribute("style", dashboard.getStyle(), "style");
		
		DashboardModel model = dashboard.getModel();
		if(model != null) {
			for(DashboardColumn column : model.getColumns()) {
				writer.startElement("div", null);
				writer.writeAttribute("class", Dashboard.COLUMN_CLASS, null);
				
				for(String widgetId : column.getWidgets()) {
					UIComponent widget = findWidget(widgetId, dashboard);
					
					if(widget != null)
						renderChild(contextr, widget);
				}
				
				writer.endElement("div");
			}
		}

		writer.endElement("div");
	}
	
	protected void encodeScript(FacesContext context, Dashboard dashboard) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String clientId = dashboard.getClientId(context);
        WidgetBuilder wb = getWidgetBuilder(context);
        wb.widget("Dashboard", dashboard.resolveWidgetVar(), clientId, false)
            .attr("disabled", dashboard.isDisabled(), false);
        
        encodeClientBehaviors(context, dashboard, wb);
        
        startScript(writer, clientId);
        writer.write(wb.build());
        endScript(writer);
	}
	
	   protected UIComponent findWidget(String id, Dashboard dashboard) {
		      for(UIComponent child : dashboard.getChildren()) {
		         Panel panel = null;
		         
		         if (child instanceof UINamingContainer) {
		            UINamingContainer childNamingContainer = (UINamingContainer) child;
		            
		            Iterator<UIComponent> iter = childNamingContainer.getFacetsAndChildren();
		            while (iter.hasNext()) {
		               UIComponent component = iter.next();
		               if (component instanceof UIPanel) {
		                  for (UIComponent secondLevelComponent : component.getChildren()) {
		                     if (secondLevelComponent instanceof Panel) {
		                        panel = (Panel) secondLevelComponent;
		                        if (panel.getId().equals(id)) {
		                           return panel.getParent().getParent(); 
		                        }
		                     }
		                  }
		               }
		            }
		         } else {
		            panel = (Panel) child;
		            
		            if(panel.getId().equals(id))
		               return panel;
		         }
		         
		      }
		      
		      return null;
		   }

    @Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		//Rendering happens on encodeEnd
	}

    @Override
	public boolean getRendersChildren() {
		return true;
	}
}