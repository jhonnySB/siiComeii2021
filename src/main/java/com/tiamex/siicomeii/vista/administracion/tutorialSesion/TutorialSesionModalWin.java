/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiamex.siicomeii.vista.administracion.tutorialSesion;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorTutorialSesion;
import com.tiamex.siicomeii.persistencia.entidad.TutorialSesion;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.administracion.tutorial.TutorialDlg;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.data.Binder;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author fred
 */
public class TutorialSesionModalWin extends TemplateModalWin {

    private TextField institucion;
    private TextField nombre;
    private TextField tutor;
    private TextField tutorial;
    private TextField urlYoutube;
    private TextField usuario;

    private long idTutorialLinked;
    private String sys;

    public long getTutorial() {
        return idTutorialLinked;
    }

    /*public void TutorialSesionModalWin(long idTutorialLinked) {
        this.idTutorialLinked = idTutorialLinked;
    }*/

    public TutorialSesionModalWin(long id,boolean nuevo) {
        this.idTutorialLinked = id;
        init();
        if(!nuevo)
            loadData(id);
    }

    private void init() {
        sys = TutorialDlg.opt;
        try {

            ResponsiveLayout contenido = new ResponsiveLayout();
            Element.cfgLayoutComponent(contenido);

            institucion = new TextField();
            Element.cfgComponent(institucion, "Institución");
            institucion.setPlaceholder("Ingrese nombre de institución");
            institucion.setRequiredIndicatorVisible(true);

            nombre = new TextField();
            Element.cfgComponent(nombre, "Nombre");
            nombre.setPlaceholder("Ingrese nombre");
            nombre.setRequiredIndicatorVisible(true);

            tutor = new TextField();
            Element.cfgComponent(tutor, "Tutor");
            tutor.setPlaceholder("Ingrese nombre del tutor");
            tutor.setRequiredIndicatorVisible(true);

            urlYoutube = new TextField();
            Element.cfgComponent(urlYoutube, "URL de Youtube");
            urlYoutube.setPlaceholder("Ingrese URL del video");
            urlYoutube.setRequiredIndicatorVisible(true);

            tutorial = new TextField();
            tutorial.setValue(sys);
            Element.cfgComponent(tutorial, "Tutorial");
            tutorial.setReadOnly(true);

            usuario = new TextField();
            Element.cfgComponent(usuario, "Usuario");
            usuario.setValue(ui.getUsuario().getNombre());
            usuario.setReadOnly(true);

            ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.TOP_CENTER);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(institucion);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(nombre);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(tutor);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(tutorial);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(urlYoutube);
            row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(usuario);

            contentLayout.addComponent(contenido);
            setCaption("Sesiones del tutorial (" +sys+ ")");
            setWidth("50%");

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void loadData(long id) {
        try {
            TutorialSesion obj = ControladorTutorialSesion.getInstance().getById(id);
            this.id = obj.getId();
            institucion.setValue(obj.getInstitucion());
            nombre.setValue(obj.getNombre());
            tutor.setValue(obj.getTutor());
            idTutorialLinked = obj.getTutorial();
            urlYoutube.setValue(obj.getUrlYoutube());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }


    @Override
    protected void buttonAcceptEvent() {
        try {
            if (validarCampos()) {
                TutorialSesion obj = new TutorialSesion();
                obj.setId(id);
                obj.setInstitucion(institucion.getValue());
                obj.setNombre(nombre.getValue());
                obj.setTutor(tutor.getValue());
                obj.setTutorial(idTutorialLinked);
                obj.setUrlYoutube(urlYoutube.getValue());
                obj.setUsuario(ui.getUsuario().getId());
                
                if(regexName()){
                    Element.makeNotification("El nombre del tutor solo puede contener letras", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                }else{
                    TutorialSesion tutorialSesion = (TutorialSesion)ControladorTutorialSesion.getInstance().getByNameLinked(idTutorialLinked,nombre.getValue());
                    
                    if(id==0){
                        if(tutorialSesion!=null){
                            Element.makeNotification("Ya existe una sesión con el mismo nombre: '"+tutorialSesion.getNombre()+"'", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                        }else{
                            obj = ControladorTutorialSesion.getInstance().save(obj);
                            if (obj != null) {
                                Element.makeNotification("Datos guardados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                ui.getFabricaVista().getTutorialsesionDlg(idTutorialLinked).eventMostrar();
                                close();
                            }
                        }
                    }else{
                        if(tutorialSesion!=null){ // mismo reg
                                    
                                    if(compareTutorial(tutorialSesion)){ // sin cambios
                                        close();
                                    }else if(tutorialSesion.getId()!=id){
                                        Element.makeNotification("Ya existe una sesión con el mismo nombre: '"+tutorialSesion.getNombre()+"'", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
                                    }else{
                                        obj = ControladorTutorialSesion.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos actualizados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getTutorialsesionDlg(idTutorialLinked).eventMostrar();
                                            close();
                                        }else{
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                    }
                                    
                                }else{
                                    
                                        obj = ControladorTutorialSesion.getInstance().save(obj);
                                        if (obj != null) {
                                            Element.makeNotification("Datos actualizados con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                            ui.getFabricaVista().getTutorialsesionDlg(idTutorialLinked).eventMostrar();
                                            close();
                                        }else{
                                            Element.makeNotification("Ocurrió un error en el servidor", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                                        }
                                    
                                }
                    }
                    
                    
                } 
            } else {
                Element.makeNotification("Faltan campos por completar", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(Page.getCurrent());
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    protected boolean compareTutorial(TutorialSesion tutorialS){
        return (tutorialS.getInstitucion().compareTo(institucion.getValue())==0 && tutorialS.getId()==id
                && tutorialS.getTutor().compareTo(tutor.getValue())==0 && tutorialS.getUrlYoutube().compareTo(urlYoutube.getValue())==0);
    }
    
    protected boolean regexName(){
        String regex = "[^A-z|ñ| ]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcherName = pattern.matcher(tutor.getValue()); 
        
        return matcherName.find();
    }

    @Override
    protected void buttonCancelEvent() {
        close();
    }

    private boolean validarCampos() {
        Binder<TutorialSesion> binder = new Binder<>();

        binder.forField(institucion).asRequired("Campo requerido").bind(TutorialSesion::getInstitucion, TutorialSesion::setInstitucion);
        binder.forField(nombre).asRequired("Campo requerido").bind(TutorialSesion::getNombre, TutorialSesion::setNombre);
        binder.forField(tutor).asRequired("Campo requerido").bind(TutorialSesion::getTutor, TutorialSesion::setTutor);
        binder.forField(urlYoutube).asRequired("Campo requerido").bind(TutorialSesion::getUrlYoutube, TutorialSesion::setUrlYoutube);

        return binder.validate().isOk();
    }
}
