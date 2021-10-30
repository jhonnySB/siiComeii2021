package com.tiamex.siicomeii.vista.administracion.tutorial;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorTutorial;
import com.tiamex.siicomeii.persistencia.entidad.Tutorial;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.data.Binder;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** @author fred **/
public final class TutorialModalWin extends TemplateModalWin {

    private TextField institucion;
    private TextField nombre;
    private TextField tutor;
    private TextField usuario;

    public TutorialModalWin() {

        init();

    }

    public TutorialModalWin(long id) {

        init();
        loadData(id);

    }

    private void init() {
        ResponsiveLayout contenido = new ResponsiveLayout();
        //Element.cfgLayoutComponent(contenido);
        contenido.setResponsive(true); contenido.setCaptionAsHtml(true);
        
        institucion = new TextField(); institucion.setWidthFull();
        //Element.cfgComponent(institucion, "Institución");
        institucion.setResponsive(true); institucion.setCaptionAsHtml(true); institucion.setCaption("<b>Institución</b>");
        institucion.setPlaceholder("Ingrese nombre de institución");
        institucion.setRequiredIndicatorVisible(true);

        nombre = new TextField(); nombre.setWidthFull();
        //Element.cfgComponent(nombre, "Nombre");
        nombre.setResponsive(true); nombre.setCaptionAsHtml(true); nombre.setCaption("<b>Nombre</b>");
        nombre.setPlaceholder("Ingrese nombre");
        nombre.setRequiredIndicatorVisible(true);

        tutor = new TextField(); tutor.setWidthFull();
        //Element.cfgComponent(tutor, "Tutor");
        tutor.setResponsive(true); tutor.setCaptionAsHtml(true); tutor.setCaption("<b>Tutor</b>");
        tutor.setPlaceholder("Ingrese nombre del tutor");
        tutor.setRequiredIndicatorVisible(true);

        usuario = new TextField(); usuario.setWidthFull();
        //Element.cfgComponent(usuario, "Usuario");
        usuario.setResponsive(true); usuario.setCaptionAsHtml(true); usuario.setCaption("<b>Usuario</b>");
        usuario.setValue(ui.getUsuario().getNombre());
        //usuario.setEnabled(false);
        usuario.setReadOnly(true);

        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
        row1.setSpacing(ResponsiveRow.SpacingSize.SMALL, true);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(institucion);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(tutor);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(usuario);

        contentLayout.addComponent(contenido);
        setCaption("Tutorial");
        setWidth("35%");
    }

    @Override
    protected void loadData(long id) {
        try {
            Tutorial obj = ControladorTutorial.getInstance().getById(id);
            this.id = obj.getId();
            institucion.setValue(obj.getInstitucion());
            nombre.setValue(obj.getNombre());
                tutor.setValue(obj.getTutor());
            //usuario.setValue(String.valueOf(ui.getUsuario().getId()));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }


    @Override
    protected void buttonAcceptEvent() {
        try {
            if (validarCampos()) {
                Tutorial obj = new Tutorial();
                obj.setId(id);
                obj.setInstitucion(institucion.getValue());
                obj.setNombre(nombre.getValue());
                obj.setTutor(tutor.getValue());
                obj.setUsuario(ui.getUsuario().getId());
                
                if(regexTutor()){
                   Element.buildNotification("Error", "No se permiten números y caracteres especiales en el nombre", "bar error closable").
                            show(Page.getCurrent());
                }else{
                    Tutorial tutorial = (Tutorial)ControladorTutorial.getInstance().getByNames(nombre.getValue());
                    if(id==0){ // nuevo registro (botón agregar)
                                
                                if (tutorial != null) { // nuevo registro con entrada de correo duplicada
                                    Element.buildNotification("Aviso", "Ya existe un registro con el mismo nombre", "bar warning closable").
                                            show(Page.getCurrent());
                                }else{ // nuevo registro con nuevo correo
                                    
                                        obj = ControladorTutorial.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.buildSucessNotification().show(Page.getCurrent());
                                            ui.getFabricaVista().getTutorialDlg().eventMostrar();
                                            close();
                                        }
                                }
                            }else{ // editando un registro
                                
                                if(tutorial!=null){ // mismo reg
                                    
                                    if(compareTutorial(tutorial)){ // sin cambios
                                        close();
                                    }else if(tutorial.getId()!=id){
                                        Element.buildNotification("Aviso", "Ya existe un registro con el mismo nombre", "bar warning closable").
                                            show(Page.getCurrent());
                                    }else{
                                        obj = ControladorTutorial.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.buildSucessNotification().show(Page.getCurrent());
                                            ui.getFabricaVista().getTutorialDlg().eventMostrar();
                                            close();
                                        }
                                    }
                                    
                                }else{
                                    
                                        obj = ControladorTutorial.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.buildSucessNotification().show(Page.getCurrent());
                                            ui.getFabricaVista().getTutorialDlg().eventMostrar();
                                            close();
                                        }
                                }
                            }
                }
                ui.getFabricaVista().tutorialSesionDlg.updateDlg();
            } else {
                Element.buildNotification("Aviso", "Faltan campos por completar", "bar warning closable").
                                            show(Page.getCurrent());
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    protected boolean compareTutorial(Tutorial tutorial){
        return (tutorial.getInstitucion().compareTo(institucion.getValue())==0 && tutorial.getId()==id
                && tutorial.getTutor().compareTo(tutor.getValue())==0 );
    }
    
    protected boolean regexTutor(){
        String regex = "[^A-z|ñ|\\p{L}| ]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcherName = pattern.matcher(tutor.getValue()); 
        
        return matcherName.find();
    }

    @Override
    protected void buttonCancelEvent() {
        close();
    }

    private boolean validarCampos() {
        Binder<Tutorial> binder = new Binder<>();

        binder.forField(institucion).asRequired("Campo requerido").bind(Tutorial::getInstitucion,Tutorial::setInstitucion);
        binder.forField(nombre).asRequired("Campo requerido").bind(Tutorial::getNombre,Tutorial::setNombre);
        binder.forField(tutor).asRequired("Campo requerido").bind(Tutorial::getTutor,Tutorial::setTutor);
        //Marca error binder.forField(usuario).asRequired("Campo requerido").bind(Tutorial::getUsuario,Tutorial::setUsuario);

        return binder.validate().isOk();
    }
}
