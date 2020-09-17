package com.tiamex.siicomeii.vista.administracion.Agremiado;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorGradoEstudio;
import com.tiamex.siicomeii.controlador.ControladorPais;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.GradoEstudio;
import com.tiamex.siicomeii.persistencia.entidad.Pais;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import java.util.logging.Logger;

/** @author fred **/
public class AgremiadoModalWin extends TemplateModalWin {

    private ComboBox <GradoEstudio> gradoEstudio;
    private TextField institucion;
    private TextField nombre;
    private ComboBox <Pais> pais;
    private TextField sexo;

    public AgremiadoModalWin() {
        init();
        delete.setVisible(false);
    }

    public AgremiadoModalWin(long id) {
        init();
        loadData(id);
        delete.setVisible(false);
    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);

        gradoEstudio = new ComboBox<>();
        Element.cfgComponent(gradoEstudio, "Grado Estudios"); 
        
        institucion = new TextField();
        Element.cfgComponent(institucion, "Institución");
        
        nombre = new TextField();
        Element.cfgComponent(nombre, "Nombre");
        
        pais = new ComboBox<>();
        Element.cfgComponent(pais, "País");
        
        sexo = new TextField();
        Element.cfgComponent(sexo, "Sexo");   
        
        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(gradoEstudio);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(institucion);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(pais);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(sexo);
        
        try{
            gradoEstudio.setItems(ControladorGradoEstudio.getInstance().getAll());
            pais.setItems(ControladorPais.getInstance().getAll());
        }catch(Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }

        contentLayout.addComponent(contenido);

        setCaption("Agremiado");
        setWidth("50%");
    }

    @Override
    protected void loadData(long id) {
        try {
            Agremiado obj = ControladorAgremiado.getInstance().getById(id);
            this.id = obj.getId();
            gradoEstudio.setValue(obj.getObjGradoEstudio());
            institucion.setValue(obj.getInstitucion());
            nombre.setValue(obj.getNombre());
            pais.setValue(obj.getObjPais());
            sexo.setValue(String.valueOf(obj.getSexo()));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonDeleteEvent() {
        try {
            ControladorAgremiado.getInstance().delete(id);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAcceptEvent() {
        try {
            Agremiado obj = new Agremiado();
            obj.setId(id);
            obj.setGradoEstudios(gradoEstudio.getValue()==null?0:gradoEstudio.getValue().getId());
            obj.setInstitucion(institucion.getValue());
            obj.setNombre(nombre.getValue());
            obj.setPais(pais.getValue()==null?0:pais.getValue().getId());
            obj.setSexo(sexo.getValue().charAt(0));

            obj = ControladorAgremiado.getInstance().save(obj);
            if (obj != null) {
                Element.makeNotification("Datos guardados", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().getAgremiadoDlg().updateDlg();
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
