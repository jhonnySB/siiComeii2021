package com.tiamex.siicomeii;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorUsuario;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.FabricaVista;
import com.tiamex.siicomeii.vista.utils.Element;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;


import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author cerimice *
 */
@Theme("siiComeiiTheme")
@SpringUI
@PreserveOnRefresh
@Viewport("width=device-width, initial-scale=1")
@StyleSheet({"https://fonts.googleapis.com/css?family=Montserrat&display=swap"})
public class SiiComeiiUI extends UI {

    private FabricaVista fabricaVista;

    public FabricaVista getFabricaVista() {
        if (fabricaVista == null) {
            fabricaVista = new FabricaVista();
        }
        return fabricaVista;
    }

    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario valor) {
        usuario = valor;
    }

    private TextField user;
    private PasswordField pass;

    @Override
    protected void init(VaadinRequest request) {
        
        ResponsiveLayout contenido = new ResponsiveLayout();
        Panel loginPanel = new Panel("Iniciar sesión");
        //CustomLayout content = new CustomLayout("layoutname");

        loginPanel.setContent(contenido);
        loginPanel.setSizeFull();
        
        user = new TextField();
        pass = new PasswordField();
        Button accesar = new Button("Iniciar sesión");
        //accesar.setSizeFull();
        
        Element.cfgComponent(user, "Correo de usuario");
        Element.cfgComponent(pass, "Contraseña");
        Element.cfgComponent(accesar);

        accesar.addClickListener(event -> {eventoBotonAccesar();});
        
        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 10, 7).withComponent(user);
        row1.addColumn().withDisplayRules(12, 12, 10, 7).withComponent(pass);
        row1.addColumn().withDisplayRules(12, 12, 10, 7).withComponent(accesar);
        setContent(loginPanel);

        try {
            CustomLayout footer = new CustomLayout(new FileInputStream(new File(Main.getBaseDir()+"/mylayout.html")));
            footer.addComponent(user, "user");
            footer.addComponent(pass, "pass");
            footer.addComponent(accesar, "accesar");
            accesar.setClickShortcut(13);
            setContent(footer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void eventoBotonAccesar() {
        try {
            String usuario = user.getValue();
            String password = pass.getValue();
            this.usuario = ControladorUsuario.getInstance().login(usuario, password);
            setContent(getFabricaVista().getMainPanel());
        } catch (Exception ex) {
            //Element.makeNotification("Datos incorrectos", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(UI.getCurrent().getPage());
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
        

    @WebServlet(urlPatterns = "/*", name = "SiiComeiiUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = SiiComeiiUI.class, productionMode = false)
    public static class SiiComeiiUIServlet extends VaadinServlet {
    }
}
