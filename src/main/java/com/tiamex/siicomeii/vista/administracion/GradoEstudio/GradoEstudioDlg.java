package com.tiamex.siicomeii.vista.administracion.GradoEstudio;
import com.tiamex.siicomeii.controlador.ControladorGradoEstudio;
import com.tiamex.siicomeii.persistencia.entidad.GradoEstudio;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import java.util.logging.Logger;

/** @author cerimice **/
public class GradoEstudioDlg extends TemplateDlg<GradoEstudio>{
    
    public GradoEstudioDlg(){
        init();
    }
    
    private void init(){
        grid.addColumn(GradoEstudio::getId).setCaption("Id");
        grid.addColumn(GradoEstudio::getNombre).setCaption("Nombre");
        
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
    
}
