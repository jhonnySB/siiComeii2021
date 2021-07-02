package com.tiamex.siicomeii.vista.administracion.usuario;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorUsuario;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author cerimice *
 */
public class UsuarioDlg extends TemplateDlg<Usuario> {

    private Button descargarSQL;

    public UsuarioDlg() throws Exception{
        init();
    }

    private void init() throws Exception{
        grid.addComponentColumn(this::buildActiveTag).setCaption("Estado");
        grid.addColumn(Usuario::getNombre).setCaption("Nombre");
        grid.addColumn(Usuario::getCorreo).setCaption("Correo");
        grid.addColumn(Usuario::getObjUsuarioGrupo).setCaption("Grupo de usuario");

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
        eventMostrar();
    }
    
    private ResponsiveLayout buildActiveTag(Usuario obj) {
        ResponsiveLayout layout = new ResponsiveLayout();
        ResponsiveRow row = layout.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        Element.cfgLayoutComponent(row, true, false);
        Label lblActivo = new Label();
        
        if(obj.getActivo()){
            lblActivo.setValue("<span style=\"background-color:#28a745;padding:3px 6px;color:white;border-radius:20px;font-size:16px\">Activo</span>");
        }else{
            lblActivo.setValue("<span style=\"background-color:#dc3545;padding:3px 6px;color:white;border-radius:20px;font-size:16px\">Inactivo</span>");
        }
        if(ui.getUsuario().getCorreo().compareTo(obj.getCorreo())==0){
            delete.setEnabled(false); delete.setDescription("Cambia de sesión para poder eliminar este usuario");
            Label lblSesion = new Label();
            lblSesion.setValue("<span style=\"background-color:#007bff;padding:3px 6px;color:white;border-radius:20px;font-size:16px\">En sesión</span>");
            lblSesion.setContentMode(ContentMode.HTML);
            row.addColumn().withComponent(lblSesion);
        }
        
        lblActivo.setContentMode(com.vaadin.shared.ui.ContentMode.HTML);
        row.addColumn().withComponent(lblActivo);
        
        return layout;
    }
    
    @Override
    protected void eventMostrar() { 
        grid.setItems(ControladorUsuario.getInstance().getAll());
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            if(!searchField.isEmpty()){
                resBusqueda.setHeight("35px");
                String strBusqueda = searchField.getValue();
                Collection<Usuario> usuarios = ControladorUsuario.getInstance().getByName(strBusqueda);
                int userSize = usuarios.size();
                if(userSize>1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;font-family:Open Sans;\">Se encontraron "+Integer.toString(userSize)+" coincidencias para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else if(userSize==1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;fotn-family:Open Sans;\">Se encontró "+Integer.toString(userSize)+" coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else{
                     resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:16px;font-family:Open Sans\">No se encontro ninguna coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>"); 
                }
                grid.setItems(usuarios);
            }else{
                resBusqueda.setValue(null);
                resBusqueda.setHeight("10px");
                grid.setItems(ControladorUsuario.getInstance().getAll());
            }
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
       @Override
    protected void eventDeleteButtonGrid(Usuario obj) {
        try {
            ui.addWindow(new UsuarioModalDelete(obj.getId()));
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDlg.class.getName()).log(Level.SEVERE, null, ex);
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
        Locale varRegional = new Locale("es", "MX");
        LocalDate fechaActual = LocalDate.now(ZoneId.of("America/Mexico_City"));
        String fechaFormateada = fechaActual.format(DateTimeFormatter.ofPattern("dd_MM_yyyy", varRegional));

        try {
            Process p = Runtime.getRuntime().exec("mysqldump -u siiComeii -psiiComeii.2020 siiComeii");
            //Siguiente linea para compilar la base de datos en windows con xampp
            //Process p = Runtime.getRuntime().exec("C:\\xampp\\mysql\\bin\\mysqldump -u siiComeii -psiiComeii.2020 siiComeii");
            InputStream is = p.getInputStream();//Pedimos la entrada
            FileOutputStream fos = new FileOutputStream("./respaldosBD/"+ fechaFormateada + "_backup_siicomeii.sql"); //creamos el archivo para le respaldo
            byte[] buffer = new byte[1000]; //Creamos una variable de tipo byte para el buffer

            int leido = is.read(buffer); //Devuelve el número de bytes leídos o -1 si se alcanzó el final del stream.
            while (leido > 0) {
                fos.write(buffer, 0, leido);//Buffer de caracteres, Desplazamiento de partida para empezar a escribir caracteres, Número de caracteres para escribir
                leido = is.read(buffer);
            }
            Element.makeNotification("SQL de Base de datos guardado", Notification.Type.TRAY_NOTIFICATION, Position.TOP_CENTER).show(ui.getPage());
            fos.close();//Cierra respaldo
        } catch (IOException e) {
            Element.makeNotification("Error al descargar archivo SQL", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
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
