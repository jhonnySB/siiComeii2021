package com.tiamex.siicomeii.vista.administracion.usuario;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.tiamex.siicomeii.controlador.ControladorUsuario;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * @author cerimice *
 */
public class UsuarioDlg extends TemplateDlg<Usuario> {

    private Button descargarSQL;

    public UsuarioDlg() {
        init();
    }

    private void init() {
        grid.addColumn(Usuario::getNombre).setCaption("Nombre");
        grid.addColumn(Usuario::getCorreo).setCaption("Correo");
        grid.addColumn(Usuario::getObjUsuarioGrupo).setCaption("Grupo");

        descargarSQL = new Button();
        descargarSQL.setResponsive(true);
        descargarSQL.setCaption("Descargar SQL");
        descargarSQL.setDescription("Descargar respaldo de la base de datos");
        descargarSQL.setIcon(VaadinIcons.FILE_TEXT_O);
        descargarSQL.addClickListener(event -> {
            downlaodSQL();
        });

        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);
        contenido.setResponsive(true);
        contenido.setWidth("100%");
        contenido.addRow().withComponents(descargarSQL).withAlignment(Alignment.TOP_RIGHT).withSpacing(true).setSizeFull();
        contentLayout.addComponent(contenido);

        setCaption("<b>Usuarios</b>");
        buttonSearchEvent();
    }

    @Override
    protected void buttonSearchEvent() {
        try {
            //grid.setItems(ControladorUsuario.getInstance().getAll());
            grid.setItems(ControladorUsuario.getInstance().getByName(searchField.getValue()));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent() {
        ui.addWindow(new UsuarioDlgModalWin());
    }

    @Override
    protected void gridEvent() {
    }

    @Override
    protected void eventEditButtonGrid(Usuario obj) {
        ui.addWindow(new UsuarioDlgModalWin(obj.getId()));
    }

    @Override
    protected void eventAsistenciaButton(Usuario obj, String idBtn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventListaAsistentes(Usuario obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected void downlaodSQL() {
        System.out.println("Mensja de prueba para descargar SQL");
        Locale varRegional = new Locale("es", "MX");
        LocalDate fechaActual = LocalDate.now(ZoneId.of("America/Mexico_City"));
        String fechaFormateada = fechaActual.format(DateTimeFormatter.ofPattern("dd_MM_yyyy", varRegional));

        try {
            Process p = Runtime.getRuntime().exec("mysqldump -u siiComeii -psiiComeii.2020 siiComeii");
            //Siguiente linea para compilar la base de datos en windows con xampp
            //Process p = Runtime.getRuntime().exec("C:\\xampp\\mysql\\bin\\mysqldump -u siiComeii -psiiComeii.2020 siiComeii");
            InputStream is = p.getInputStream();//Pedimos la entrada
            FileOutputStream fos = new FileOutputStream(fechaFormateada + "_backup_siicomeii.sql"); //creamos el archivo para le respaldo
            byte[] buffer = new byte[1000]; //Creamos una variable de tipo byte para el buffer

            int leido = is.read(buffer); //Devuelve el número de bytes leídos o -1 si se alcanzó el final del stream.
            while (leido > 0) {
                fos.write(buffer, 0, leido);//Buffer de caracteres, Desplazamiento de partida para empezar a escribir caracteres, Número de caracteres para escribir
                leido = is.read(buffer);
            }
            Element.makeNotification("SQL de Base de datos guardado", Notification.Type.TRAY_NOTIFICATION, Position.TOP_CENTER).show(ui.getPage());
            fos.close();//Cierra respaldo
        } catch (IOException e) {
            System.out.println("ERROR AL DESCARGAR SQL");
        }
    }
    
    @Override
    protected void eventWebinarsAgremiado(Usuario obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventTutorialSesiones(Usuario obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
