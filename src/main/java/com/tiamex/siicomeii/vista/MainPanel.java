package com.tiamex.siicomeii.vista;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.Main;
import com.tiamex.siicomeii.SiiComeiiUI;
import com.tiamex.siicomeii.vista.administracion.GradoEstudio.GradoEstudioDlg;
import com.tiamex.siicomeii.vista.reportes.ChartExportDemo;
import com.tiamex.siicomeii.vista.reportes.agremiadosChart;
//import com.tiamex.siicomeii.vista.utils.ChartExportDemo;
import com.tiamex.siicomeii.vista.utils.Element;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.batik.gvt.flow.MarginInfo;

/**
 * @author fred *
 */
@Theme("siiComeiiTheme")
public class MainPanel extends Panel {

    private SiiComeiiUI ui;

    private HorizontalLayout contenidoPrincipal;

    public void setContenidoPrincipal(Panel contenido) {
        contenidoPrincipal.removeAllComponents();
        contenidoPrincipal.addComponent(contenido);
    }
    
    public void setContenidoPrincipal(VerticalLayout contenido) {
        contenidoPrincipal.removeAllComponents();
        contenidoPrincipal.addComponent(contenido);
    }
    
    public MainPanel() throws FileNotFoundException, IOException {
        init();
    }

    private void init() throws FileNotFoundException, IOException {
        ui = Element.getUI();

        ResponsiveLayout header = new ResponsiveLayout();
        Element.cfgLayoutComponent(header);
        ResponsiveRow headerRow1 = header.addRow().withAlignment(Alignment.TOP_CENTER);
        headerRow1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(crearMenuPrincipal());

        contenidoPrincipal = new HorizontalLayout();
        //Element.cfgLayoutComponent(contenidoPrincipal,false,false);
        contenidoPrincipal.setWidth("100%");
        contenidoPrincipal.setResponsive(true);
        contenidoPrincipal.setSpacing(false);
        contenidoPrincipal.setMargin(false);
        contenidoPrincipal.setHeight("550px");
        contenidoPrincipal.setCaptionAsHtml(true);
        contenidoPrincipal.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);

        BrowserFrame home = new BrowserFrame("", new ExternalResource("https://www.tiamex.com.mx/"));
        home.setWidthFull();
        home.setHeightFull();
        home.setSizeFull();

        try {
            setContenidoPrincipal(new Inicio());
        } catch (Exception ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        //ResponsiveLayout footer = new ResponsiveLayout();
        //Element.cfgLayoutComponent(footer);
        CustomLayout footer = new CustomLayout(new FileInputStream(new File(Main.getBaseDir() + "/footer.html")));
        Link linkAbout = new Link();
        linkAbout.setCaption("Acerca de nosotros");
        linkAbout.setTargetName("_blank");
        linkAbout.setResource(new ExternalResource("https://www.tiamex.com.mx/#about-us"));
        Link linkContact = new Link();
        linkContact.setResource(new ExternalResource("https://www.tiamex.com.mx/#contact"));
        linkContact.setTargetName("_blank");
        linkContact.setCaption("Contacto");
        footer.addComponent(linkAbout, "divAbout");
        footer.addComponent(linkContact, "divContact");
        footer.setSizeFull();
        footer.setResponsive(true);

        FileResource iconLogo = new FileResource(new File(Main.getBaseDir() + "/logoTiamex2.png"));
        GridLayout topbar = new GridLayout(2, 2);
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
        userLbl.setValue("<b>Has iniciado sesión como: " + ui.getUsuario().getNombre() + "</b>");
        userLbl.setContentMode(ContentMode.HTML);
        topbar.addComponent(userLbl);
        topbar.setComponentAlignment(userLbl, Alignment.MIDDLE_RIGHT);

        MenuBar menuCuenta = new MenuBar();
        MenuBar.MenuItem perfil = menuCuenta.addItem("Cuenta", VaadinIcons.USER, null);
        MenuBar.MenuItem cerrarSesion = perfil.addItem("Cerrar sesión", VaadinIcons.POWER_OFF,
                comando -> {
                    ui.getSession().close();
                    ui.getPage().reload();
                });

        topbar.addComponent(menuCuenta, 1, 1);
        topbar.setComponentAlignment(menuCuenta, Alignment.TOP_RIGHT);

        VerticalLayout contenido = new VerticalLayout();
        Element.cfgLayoutComponent(contenido, true, true);
        contenido.addComponent(topbar);
        contenido.addComponent(header);
        contenido.addComponent(contenidoPrincipal);
        contenido.addComponent(footer);

        setSizeFull();
        setContent(contenido);
    }

    private MenuBar crearMenuPrincipal() {
        MenuBar menuPrincipal = new MenuBar();
        menuPrincipal.setWidth("100%");
        
        MenuBar.MenuItem homeTab = menuPrincipal.addItem("Inicio", VaadinIcons.HOME, comando -> {
            try {
                setContenidoPrincipal(new Inicio());
            } catch (Exception ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MenuBar.MenuItem administracion = menuPrincipal.addItem("Administración", VaadinIcons.TOOLBOX, null);
        MenuBar.MenuItem Nuevo = administracion.addItem("Catálogos", VaadinIcons.CALC_BOOK, c->{
            try {
                Panel p = new Panel(); p.setSizeFull(); p.setResponsive(true); p.setCaption("Catalogos");
                GridLayout grid = new GridLayout();
                FabricaVista viewFactory = ui.getFabricaVista();
                grid.setColumns(3);
                grid.setSizeFull();
                grid.setSpacing(true);
                grid.addComponents(viewFactory.getGradoEstudioDlg(),viewFactory.getUsuarioGrupoDlg(),viewFactory.getPaisDlg());
                p.setContent(grid);
                setContenidoPrincipal(p);
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        MenuBar.MenuItem usuario = administracion.addItem("Usuarios", VaadinIcons.USERS, comando -> {
            try {
                setContenidoPrincipal(ui.getFabricaVista().getUsuarioDlg());
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        

        MenuBar.MenuItem proximosWebinar = menuPrincipal.addItem("Próximos Webinars", VaadinIcons.CALENDAR_USER, comando -> {
            try {
                setContenidoPrincipal(ui.getFabricaVista().getProximoWebinarDlg());
            } catch (Exception ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MenuBar.MenuItem tutorial = menuPrincipal.addItem("Tutoriales", VaadinIcons.DESKTOP, comando -> {
            try {
                setContenidoPrincipal(ui.getFabricaVista().getTutorialDlg());
            } catch (Exception ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MenuBar.MenuItem evento = menuPrincipal.addItem("Próximos eventos", VaadinIcons.CALENDAR_CLOCK, comando -> {
            try {
                setContenidoPrincipal(ui.getFabricaVista().getProximoEventoDlg());
            } catch (Exception ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MenuBar.MenuItem agremiado = menuPrincipal.addItem("Agremiados", VaadinIcons.USERS, comando -> {
            try {
                setContenidoPrincipal(ui.getFabricaVista().getAgremiadoDlg());
            } catch (Exception ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MenuBar.MenuItem webinarRealizado = menuPrincipal.addItem("Webinars realizados", VaadinIcons.DIPLOMA, comando -> {
            try {
                setContenidoPrincipal(ui.getFabricaVista().getWebinarRealizadoDlg());
            } catch (Exception ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        return menuPrincipal;
    }

}
