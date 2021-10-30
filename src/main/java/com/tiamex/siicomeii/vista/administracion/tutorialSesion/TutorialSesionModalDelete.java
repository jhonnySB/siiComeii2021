package com.tiamex.siicomeii.vista.administracion.tutorialSesion;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorTutorialSesion;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalDelete;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author fred *
 */
public class TutorialSesionModalDelete extends TemplateModalDelete {

    private Label lblConfirmar;
    private long idTutorialSesion;
    private long idTutorialLinked;

    public TutorialSesionModalDelete() throws Exception {
        
        init();
  
    }

    public TutorialSesionModalDelete(long id,long tutorialLinked) throws Exception {
        idTutorialSesion=id;
        idTutorialLinked = tutorialLinked;
        init();       

    }

    private void init() throws Exception {

        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);
        lblConfirmar = new Label("¿Esta seguro que desea eliminar el registro <br>"
                +"' "+ControladorTutorialSesion.getInstance().getById(idTutorialSesion).getNombre()+" '",ContentMode.HTML);


        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        row1.addColumn().withDisplayRules(6, 6, 12, 12).withComponent(lblConfirmar);
        contentLayout.addComponent(contenido); 

        setCaption("Confirmación");
        setWidth("25%");
    }
    
    
    @Override
    protected void buttonAcceptEvent(){
        try {
            if(ControladorTutorialSesion.getInstance().delete(idTutorialSesion)>0){
                Element.buildSucessNotification().show(Page.getCurrent());
                ui.getFabricaVista().getTutorialsesionDlg(idTutorialLinked).eventMostrar();
                close();
            }else{
                Element.buildNotification("Aviso", "No se pudo eliminar el registro", "bar warning closable").show(Page.getCurrent());
                accept.setDisableOnClick(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(TutorialSesionModalDelete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void buttonCancelEvent() {
        close();
    }
    
}