package com.tiamex.siicomeii.vista.administracion.Pais;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorPais;
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
public class PaisModalDelete extends TemplateModalDelete {

    private Label lblConfirmar;
    private long idPais;

    public PaisModalDelete() throws Exception {
        init();
        //delete.setVisible(false);
    }

    public PaisModalDelete(long id) throws Exception {
        idPais=id;
        init();       
        
        //loadData(id);
        //delete.setVisible(false);
    }

    private void init() throws Exception {

        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);
        lblConfirmar = new Label("¿Esta seguro que desea eliminar el registro <br>"
                +"' "+ControladorPais.getInstance().getById(idPais).getNombre()+" '",ContentMode.HTML);


        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        row1.addColumn().withDisplayRules(6, 6, 12, 12).withComponent(lblConfirmar);
        contentLayout.addComponent(contenido); 

        setCaption("Confirmación");
        setWidth("25%");
    }
    
    
    @Override
    protected void buttonAcceptEvent(){
        try {
            if(ControladorPais.getInstance().delete(idPais)>0){
                Element.makeNotification("Eliminado con éxito", Notification.Type.HUMANIZED_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
                ui.getFabricaVista().paisDlg.eventMostrar();
                close();
            }else{
                Element.makeNotification("No se pudo eliminar el registro", Notification.Type.WARNING_MESSAGE, Position.TOP_CENTER).show(ui.getPage());        
                accept.setDisableOnClick(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(PaisModalDelete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void buttonCancelEvent() {
        close();
    }
    
}