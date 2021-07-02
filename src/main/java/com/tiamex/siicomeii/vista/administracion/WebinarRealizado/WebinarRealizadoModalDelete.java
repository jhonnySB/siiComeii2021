package com.tiamex.siicomeii.vista.administracion.WebinarRealizado;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateModalDelete;
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
public class WebinarRealizadoModalDelete extends TemplateModalDelete {

    private Label lblConfirmar;
    private long idWebinar;

    public WebinarRealizadoModalDelete() throws Exception {
        init();
        //delete.setVisible(false);
    }

    public WebinarRealizadoModalDelete(long id) throws Exception {
        idWebinar=id;
        init();       
        
        //loadData(id);
        //delete.setVisible(false);
    }

    private void init() throws Exception {

        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);
        lblConfirmar = new Label("¿Esta seguro que desea eliminar el registro <br>"
                +"' "+ControladorWebinarRealizado.getInstance().getById(idWebinar).getNombre()+" '",ContentMode.HTML);


        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        row1.addColumn().withDisplayRules(6, 6, 12, 12).withComponent(lblConfirmar);
        contentLayout.addComponent(contenido); 

        setCaption("Confirmación");
        setWidth("25%");
    }
    
    
    @Override
    protected void buttonAcceptEvent(){
        try {
            if(ControladorWebinarRealizado.getInstance().delete(idWebinar)>0){
                Element.makeNotification("Eliminado con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().WebinarRealizadoDlg.eventMostrar();
                close();
            }else{
                Element.makeNotification("No se pudo eliminar el registro", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());        
                accept.setDisableOnClick(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(WebinarRealizadoModalDelete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void buttonCancelEvent() {
        close();
    }
    
}