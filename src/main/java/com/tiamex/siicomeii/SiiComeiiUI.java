package com.tiamex.siicomeii;

import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.vista.FabricaVista;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import javax.servlet.annotation.WebServlet;

/** @author cerimice **/

@SpringUI
@PreserveOnRefresh
@Viewport("width=device-width, initial-scale=1")
@StyleSheet({"https://fonts.googleapis.com/css?family=Montserrat&display=swap"})
public class SiiComeiiUI extends UI{
    
    private FabricaVista fabricaVista;
    public FabricaVista getFabricaVista(){
        if(fabricaVista == null){fabricaVista = new FabricaVista();}
        return fabricaVista;
    }
    
    private Usuario usuario;
    public Usuario getUsuario(){return usuario;}
    public void setUsuario(Usuario valor){usuario = valor;}
    
    @Override
    protected void init(VaadinRequest request){
        
        /*ResponsiveLayout contenido = new ResponsiveLayout();
            contenido.setWidth("100%");
        
        TextField nombre = new TextField();
            nombre.setCaption("Nombre");
        TextField correo = new TextField();
            correo.setCaption("Correo");
        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(nombre);
            row1.addColumn().withDisplayRules(12,12,12,12).withComponent(correo);*/
        
        setContent(getFabricaVista().getMainPanel());
    }
    
    @WebServlet(urlPatterns = "/*", name = "SiiComeiiUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = SiiComeiiUI.class, productionMode = true)
    public static class EsafMorelos2UIServlet extends VaadinServlet{
    }
}
