package com.tiamex.siicomeii;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.vista.FabricaVista;
import com.tiamex.siicomeii.vista.utils.Element;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
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
        
        ResponsiveLayout contenido = new ResponsiveLayout();
            contenido.setWidth("100%");
        
        TextField user = new TextField();
            Element.cfgComponent(user,"Usuario");
        TextField pass = new PasswordField();
            Element.cfgComponent(pass,"Password");
        Button accesar = new Button("Accesar");
            Element.cfgComponent(accesar);
            accesar.addClickListener(event -> {setContent(getFabricaVista().getMainPanel());});
        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
            row1.addColumn().withDisplayRules(12,12,10,8).withComponent(user);
            row1.addColumn().withDisplayRules(12,12,10,8).withComponent(pass);
            row1.addColumn().withDisplayRules(12,12,10,8).withComponent(accesar);
        
        //setContent(getFabricaVista().getMainPanel());
        setContent(contenido);
    }
    
    @WebServlet(urlPatterns = "/*", name = "SiiComeiiUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = SiiComeiiUI.class, productionMode = true)
    public static class EsafMorelos2UIServlet extends VaadinServlet{
    }
}
