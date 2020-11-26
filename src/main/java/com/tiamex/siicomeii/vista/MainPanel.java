package com.tiamex.siicomeii.vista;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.Main;
import com.tiamex.siicomeii.SiiComeiiUI;
import com.tiamex.siicomeii.vista.utils.Element;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/** @author fred **/

public class MainPanel extends Panel{
    
    private SiiComeiiUI ui;
    
    private VerticalLayout contenidoPrincipal;
    public void setContenidoPrincipal(Panel contenido){
        contenidoPrincipal.removeAllComponents();
        contenidoPrincipal.addComponent(contenido);
    }

    public MainPanel() throws FileNotFoundException, IOException{
        init();
    }
    
    private void init() throws FileNotFoundException, IOException{
        ui = Element.getUI();
        
        ResponsiveLayout header = new ResponsiveLayout();
            Element.cfgLayoutComponent(header);
        ResponsiveRow headerRow1 = header.addRow().withAlignment(Alignment.TOP_CENTER);
            headerRow1.addColumn().withDisplayRules(12,12,12,12).withComponent(crearMenuPrincipal());
        
        contenidoPrincipal = new VerticalLayout();
            Element.cfgLayoutComponent(contenidoPrincipal,true,false);
        
        //ResponsiveLayout footer = new ResponsiveLayout();
            //Element.cfgLayoutComponent(footer);
        CustomLayout footer = new CustomLayout(new FileInputStream(new File(Main.getBaseDir()+"/footer.html")));
        footer.setSizeFull();
        footer.setResponsive(true);
        
        FileResource iconLogo = new FileResource(new File(Main.getBaseDir() +"/logoTiamex2.png"));
        GridLayout topbar = new GridLayout(2,2);
        topbar.setSizeFull();
        topbar.setResponsive(true);
        Link linkLogo = new Link();
        linkLogo.setIcon(iconLogo);
        linkLogo.setResource(new ExternalResource("https://www.tiamex.com.mx/"));
        linkLogo.setTargetName("_blank");
        topbar.addComponent(linkLogo);
        topbar.setComponentAlignment(linkLogo, Alignment.TOP_LEFT);
        
        
        Label userLbl = new Label();
        userLbl.setCaptionAsHtml(true);
        userLbl.setValue("Has iniciado sesión como: "+ui.getUsuario().getNombre());
        topbar.addComponent(userLbl);
        topbar.setComponentAlignment(userLbl, Alignment.MIDDLE_RIGHT);
        
        MenuBar menuCuenta = new MenuBar();
        MenuBar.MenuItem perfil = menuCuenta.addItem("Cuenta",VaadinIcons.USER,null);
        MenuBar.MenuItem cerrarSesion = perfil.addItem("Cerrar sesión",VaadinIcons.POWER_OFF,
                comando -> {ui.getSession().close();ui.getPage().reload();});
        
        topbar.addComponent(menuCuenta,1,1);
        topbar.setComponentAlignment(menuCuenta, Alignment.TOP_RIGHT);
        
        VerticalLayout contenido = new VerticalLayout();
            Element.cfgLayoutComponent(contenido,true,true);
            contenido.addComponent(topbar);
            contenido.addComponent(header);
            contenido.addComponent(contenidoPrincipal);
            contenido.addComponent(footer);
        
        setSizeFull();
        setContent(contenido);
    }
    
    private MenuBar crearMenuPrincipal(){
        MenuBar menuPrincipal = new MenuBar();
        menuPrincipal.setWidth("100%");
        
        MenuBar.MenuItem administracion = menuPrincipal.addItem("Administración",VaadinIcons.TOOLBOX,null);
        MenuBar.MenuItem catalogos = administracion.addItem("Catálogos",VaadinIcons.CALC_BOOK,null);
        MenuBar.MenuItem gradoEstudio = catalogos.addItem("Grado de estudios",VaadinIcons.USERS,comando -> {setContenidoPrincipal(ui.getFabricaVista().getGradoEstudioDlg());});
        MenuBar.MenuItem usuarioGrupo = catalogos.addItem("Grupo de usuario",VaadinIcons.USERS,comando -> {setContenidoPrincipal(ui.getFabricaVista().getUsuarioGrupoDlg());});
        MenuBar.MenuItem usuario = catalogos.addItem("Usuarios",VaadinIcons.USER,comando -> {setContenidoPrincipal(ui.getFabricaVista().getUsuarioDlg());});
        MenuBar.MenuItem pais = catalogos.addItem("Paises",VaadinIcons.USERS,comando -> {setContenidoPrincipal(ui.getFabricaVista().getPaisDlg());});
        
        MenuBar.MenuItem proximosWebinar = menuPrincipal.addItem("Próximos Webinars",VaadinIcons.CALENDAR_USER,comando -> {setContenidoPrincipal(ui.getFabricaVista().getProximoWebinarDlg());});
        
        MenuBar.MenuItem tutorial = menuPrincipal.addItem("Tutoriales",VaadinIcons.DESKTOP,comando -> {setContenidoPrincipal(ui.getFabricaVista().getTutorialDlg());});
        
        MenuBar.MenuItem evento = menuPrincipal.addItem("Próximos eventos",VaadinIcons.USER, comando -> {setContenidoPrincipal(ui.getFabricaVista().getProximoEventoDlg());});
        
        MenuBar.MenuItem agremiado = menuPrincipal.addItem("Agremiados",VaadinIcons.USER, comando -> {setContenidoPrincipal(ui.getFabricaVista().getAgremiadoDlg());});

        MenuBar.MenuItem webinarRealizado = menuPrincipal.addItem("Webinars realizados",VaadinIcons.USER, comando -> {setContenidoPrincipal(ui.getFabricaVista().getWebinarRealizadoDlg());});
        
        
        return menuPrincipal;
    }
    

    
    
}
