package com.tiamex.siicomeii.vista.administracion.GradoEstudio;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorGradoEstudio;
import com.tiamex.siicomeii.persistencia.entidad.GradoEstudio;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.tiamex.siicomeii.vista.utils.TemplateDlgCatalogos;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.JavaScript;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author cerimice **/
public class GradoEstudioDlg extends TemplateDlgCatalogos<GradoEstudio>{

    public GradoEstudioDlg() throws Exception{
        init();
    }

    private void init(){
        searchField.setPlaceholder("Buscar grado de estudio");
        grid.addColumn(GradoEstudio::getNombre).setCaption("Nombre");
        setCaption("<b>Grado de estudios</b>");
        //buttonSearchEvent();
        eventMostrar();
    }
    
    @Override
    protected void eventMostrar() { 
        grid.setItems(ControladorGradoEstudio.getInstance().getAll());
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            if(!searchField.isEmpty()){
                //resBusqueda.setHeight("35px");
                String strBusqueda = searchField.getValue();
                Collection<GradoEstudio> grados = ControladorGradoEstudio.getInstance().getByName(strBusqueda);
                int gradoSize = grados.size();
                if(gradoSize>1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:14px;font-family:Lora;"
                            + "letter-spacing: 1px;\">Se encontraron "+Integer.toString(gradoSize)+" coincidencias para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else if(gradoSize==1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:14px;fotn-family:Lora;"
                            + "letter-spacing: 1px;\">Se encontró "+Integer.toString(gradoSize)+" coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else{
                     resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:14px;font-family:Lora"
                             + "letter-spacing: 1px;\">No se encontro ninguna coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>"); 
                }
                grid.setItems(grados);
            }else{
                resBusqueda.setValue(null);
                //resBusqueda.setHeight("10px");
                grid.setItems(ControladorGradoEstudio.getInstance().getAll());
            }
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent(){
        ui.addWindow(new GradoEstudioDlgModalWin());
    }
    
     @Override
    protected void eventDeleteButtonGrid(GradoEstudio obj) {
        try {
            ui.addWindow(new GradoEstudioModalDelete(obj.getId()));
        } catch (Exception ex) {
            Logger.getLogger(GradoEstudioDlg.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
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

    
    @Override
    protected void eventWebinarsAgremiado(GradoEstudio obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventTutorialSesiones(GradoEstudio obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
