package com.tiamex.siicomeii.vista.administracion.GradoEstudio;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorGradoEstudio;
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
public class GradoEstudioModalDelete extends TemplateModalDelete {

    private Label lblConfirmar;
    private long idGrado;

    public GradoEstudioModalDelete() throws Exception {
        init();
        //delete.setVisible(false);
    }

    public GradoEstudioModalDelete(long id) throws Exception {
        idGrado=id;
        init();       
        
        //loadData(id);
        //delete.setVisible(false);
    }

    private void init() throws Exception {

        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);
        lblConfirmar = new Label("¿Esta seguro que desea eliminar el registro <br>"
                +"' "+ControladorGradoEstudio.getInstance().getById(idGrado).getNombre()+" '",ContentMode.HTML);


        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        row1.addColumn().withDisplayRules(6, 6, 12, 12).withComponent(lblConfirmar);
        contentLayout.addComponent(contenido); 

        setCaption("Confirmación");
        setWidth("25%");
    }
    
    
    @Override
    protected void buttonAcceptEvent(){
        try {
            if(ControladorGradoEstudio.getInstance().delete(idGrado)>0){
                Element.buildSucessNotification().show(Page.getCurrent());
                ui.getFabricaVista().gradoEstudioDlg.eventMostrar();
                close();
            }else{
                Element.buildNotification("Aviso", "No se pudo eliminar el registro", "bar warning closable").show(Page.getCurrent());
                accept.setDisableOnClick(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(GradoEstudioModalDelete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void buttonCancelEvent() {
        close();
    }
    
}