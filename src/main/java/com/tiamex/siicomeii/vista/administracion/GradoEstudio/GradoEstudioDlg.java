package com.tiamex.siicomeii.vista.administracion.GradoEstudio;
import com.tiamex.siicomeii.controlador.ControladorGradoEstudio;
import com.tiamex.siicomeii.persistencia.entidad.GradoEstudio;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.ui.Button;
import java.util.logging.Logger;

/** @author cerimice **/
public class GradoEstudioDlg extends TemplateDlg<GradoEstudio>{
    
    public GradoEstudioDlg(){
        init();
    }
    
    private void init(){
        grid.addColumn(GradoEstudio::getId).setCaption("Id");
        grid.addColumn(GradoEstudio::getNombre).setCaption("Nombre");
        setCaption("<b>Grado de estudios</b>");
        buttonSearchEvent();
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            grid.setItems(ControladorGradoEstudio.getInstance().getByName(searchField.getValue()));
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    @Override
    protected void buttonAddEvent(){
        ui.addWindow(new GradoEstudioDlgModalWin());
    }
    
    @Override
    protected void gridEvent(){
        
    }
    
    @Override
    protected void eventEditButtonGrid(GradoEstudio obj){
        ui.addWindow(new GradoEstudioDlgModalWin(obj.getId()));
    }

    @Override
    protected void eventAsistenciaButton(GradoEstudio obj,String idBtn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventListaAsistentes(GradoEstudio obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
