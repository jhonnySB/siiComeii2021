package com.tiamex.siicomeii.vista.administracion.Agremiado;

import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import java.util.logging.Logger;

/** @author fred **/
public class AgremiadoDlg extends TemplateDlg<Agremiado>{
    
    public AgremiadoDlg(){  //Constructor de la clase AgremiadoDlg
        init();
    }
    
    private void init(){    //Columnas que son asignadas a la tabla de agremiado en la interfaz web
        grid.addColumn(Agremiado::getId).setCaption("Id");
        grid.addColumn(Agremiado::getObjGradoEstudio).setCaption("Grado estudio");
        grid.addColumn(Agremiado::getInstitucion).setCaption("Institución");
        grid.addColumn(Agremiado::getNombre).setCaption("Nombre");
        grid.addColumn(Agremiado::getPais).setCaption("País");
        grid.addColumn(Agremiado::getSexo).setCaption("Sexo");
        
        
        buttonSearchEvent(); //Método que es llamado sin recibir ningún parametro 
    }
    
    @Override //Método que stablece los elementos de datos de este componente proporcionados como una colección.
    protected void buttonSearchEvent(){
        try{
            grid.setItems(ControladorAgremiado.getInstance().getAll());
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent() {
        ui.addWindow(new AgremiadoModalWin());
    }

    @Override
    protected void gridEvent() {
    }
    
    @Override
    protected void eventEditButtonGrid(Agremiado obj) {
        ui.addWindow(new AgremiadoModalWin(obj.getId()));
    }
    
}
