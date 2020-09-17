package com.tiamex.siicomeii.vista.administracion.proximowebinar;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.controlador.ControladorUsuario;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.logging.Logger;

/**
 * @author fred *
 */
public final class ProximoWebinarModalWin extends TemplateModalWin {

    private DateField fecha;
    private Upload imagen;
    private TextField institucion;
    private TextField ponente;
    private TextField titulo;
    private ComboBox<Usuario> usuario;

    public ProximoWebinarModalWin() {
        init();
        delete.setVisible(false);
    }

    public ProximoWebinarModalWin(long id) {
        init();
        loadData(id);
        delete.setVisible(false);
    }

    private void init() {
        Date myDate = new Date();
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);
        fecha = new DateField();
        Element.cfgComponent(fecha, "Fecha");
        fecha.setPlaceholder(new SimpleDateFormat("yyyy-MM-dd").format(myDate));

        imagen = new Upload();
        Element.cfgComponent(imagen, "Seleccione imagen");

        institucion = new TextField();
        Element.cfgComponent(institucion, "Institución");
        institucion.setPlaceholder("Ingrese nstitución");
        
        ponente = new TextField();
        Element.cfgComponent(ponente, "Ponente");
        ponente.setPlaceholder("Ingrese ponente");
        
        titulo = new TextField();
        Element.cfgComponent(titulo, "Título");
        titulo.setPlaceholder("Ingrese título");
        
        usuario = new ComboBox<>();
        Element.cfgComponent(usuario, "Usuario");

        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(fecha);
        row1.addColumn().withDisplayRules(12, 12, 12, 6).withComponent(imagen).setAlignment(ResponsiveColumn.ColumnComponentAlignment.CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(institucion);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(ponente);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(titulo);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(usuario);

        try {
            usuario.setItems(ControladorUsuario.getInstance().getAll());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }

        contentLayout.addComponent(contenido);
        setCaption("Proximo webinar");
        setWidth("50%");
    }

    @Override
    protected void loadData(long id) {
        try {
            ProximoWebinar obj = ControladorProximoWebinar.getInstance().getById(id);
            this.id = obj.getId();
            fecha.setValue(obj.getFecha().toLocalDate());
            //imagen.setValue(obj.getImagen());
            institucion.setValue(obj.getInstitucion());
            ponente.setValue(obj.getPonente());
            titulo.setValue(obj.getTitulo());
            usuario.setValue(obj.getObjUsuario());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonDeleteEvent() {
        try {
            ControladorProximoWebinar.getInstance().delete(id);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAcceptEvent() {
        try {
            ProximoWebinar obj = new ProximoWebinar();
            obj.setId(id);
            obj.setFecha(fecha.getValue().atTime(LocalTime.MIN));
            //obj.setImagen(imagen.getValue());
            obj.setInstitucion(institucion.getValue());
            obj.setPonente(ponente.getValue());
            obj.setTitulo(titulo.getValue());
            obj.setUsuario(usuario.getValue() == null ? 0 : usuario.getValue().getId());

            obj = ControladorProximoWebinar.getInstance().save(obj);
            if (obj != null) {
                Element.makeNotification("Datos guardados", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().getProximoWebinarDlg().updateDlg();
                close();
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonCancelEvent() {
        close();
    }

}
