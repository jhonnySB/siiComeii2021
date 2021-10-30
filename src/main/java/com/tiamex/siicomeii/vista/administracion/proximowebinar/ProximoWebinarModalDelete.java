package com.tiamex.siicomeii.vista.administracion.proximowebinar;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
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
public class ProximoWebinarModalDelete extends TemplateModalDelete {

    private Label lblConfirmar;
    private long idWebinar;

    public ProximoWebinarModalDelete() throws Exception {
        init();
        //delete.setVisible(false);
    }

    public ProximoWebinarModalDelete(long id) throws Exception {
        idWebinar=id;
        init();       
        
        //loadData(id);
        //delete.setVisible(false);
    }

    private void init() throws Exception {

        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);
        lblConfirmar = new Label("¿Esta seguro que desea eliminar el registro <br>"
                +"' "+ControladorProximoWebinar.getInstance().getById(idWebinar).getTitulo()+" '",ContentMode.HTML);


        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        row1.addColumn().withDisplayRules(6, 6, 12, 12).withComponent(lblConfirmar);
        contentLayout.addComponent(contenido); 

        setCaption("Confirmación");
        setWidth("25%");
    }
    
    
    @Override
    protected void buttonAcceptEvent(){
        try {
            if(ControladorProximoWebinar.getInstance().delete(idWebinar)>0){
                Element.buildSucessNotification().show(Page.getCurrent());
                ui.getFabricaVista().proximoWebinarDlg.eventMostrar();
                close();
            }else{
                Element.buildNotification("Aviso", "No se pudo eliminar el registro", "bar warning closable").show(Page.getCurrent());
                accept.setDisableOnClick(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProximoWebinarModalDelete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void buttonCancelEvent() {
        close();
    }
    
}