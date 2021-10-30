package com.tiamex.siicomeii.vista.administracion.ProximoEvento;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorProximoEvento;
import com.tiamex.siicomeii.controlador.ControladorTutorial;
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
public class ProximoEventoModalDelete extends TemplateModalDelete {

    private Label lblConfirmar;
    private long idProximoEvento;

    public ProximoEventoModalDelete() throws Exception {
        init();
  
    }

    public ProximoEventoModalDelete(long id) throws Exception {
        idProximoEvento=id;
        init();       

    }

    private void init() throws Exception {

        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);
        lblConfirmar = new Label("¿Esta seguro que desea eliminar el registro <br>"
                +"' "+ControladorProximoEvento.getInstance().getById(idProximoEvento).getTitulo()+" '",ContentMode.HTML);


        ResponsiveRow row1 = contenido.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        row1.addColumn().withDisplayRules(6, 6, 12, 12).withComponent(lblConfirmar);
        contentLayout.addComponent(contenido); 

        setCaption("Confirmación");
        setWidth("25%");
    }
    
    
    @Override
    protected void buttonAcceptEvent(){
        try {
            if(ControladorProximoEvento.getInstance().delete(idProximoEvento)>0){
                Element.buildSucessNotification().show(Page.getCurrent());
                ui.getFabricaVista().proximoEventoDlg.eventMostrar();
                close();
            }else{
                Element.buildNotification("Aviso", "No se pudo eliminar el registro", "bar warning closable").show(Page.getCurrent());
                accept.setDisableOnClick(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProximoEventoModalDelete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void buttonCancelEvent() {
        close();
    }
    
}