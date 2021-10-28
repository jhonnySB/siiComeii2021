package com.tiamex.siicomeii.vista.administracion.usuarioGrupo;
import com.tiamex.siicomeii.controlador.ControladorUsuarioGrupo;
import com.tiamex.siicomeii.persistencia.entidad.UsuarioGrupo;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.tiamex.siicomeii.vista.utils.TemplateDlgCatalogos;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author cerimice **/
public class UsuarioGrupoDlg extends TemplateDlgCatalogos<UsuarioGrupo>{

    public UsuarioGrupoDlg() throws Exception{
        init();
    }

    private void init(){
        searchField.setPlaceholder("Buscar grupo de usuario");
        //grid.addColumn(UsuarioGrupo::getId).setCaption("Id");
        grid.addColumn(UsuarioGrupo::getNombre).setCaption("Nombre");
        setCaption("<b>Grupo de usuarios</b>");
        //buttonSearchEvent();
        eventMostrar();
    }
    
    @Override
    protected void eventMostrar() { 
        grid.setItems(ControladorUsuarioGrupo.getInstance().getAll());
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            if(!searchField.isEmpty()){
                //resBusqueda.setHeight("35px");
                String strBusqueda = searchField.getValue();
                Collection<UsuarioGrupo> grupos = ControladorUsuarioGrupo.getInstance().getByName(strBusqueda);
                int grupoSize = grupos.size();
                if(grupoSize>1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:14px;font-family:Lora;"
                            + "letter-spacing: 1px;\">Se encontraron "+Integer.toString(grupoSize)+" coincidencias para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else if(grupoSize==1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:14px;fotn-family:Lora;"
                            + "letter-spacing: 1px;\">Se encontró "+Integer.toString(grupoSize)+" coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else{
                     resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:14px;font-family:Lora;"
                             + "letter-spacing: 1px;\">No se encontro ninguna coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>");  
                }
                grid.setItems(grupos);
            }else{
                resBusqueda.setValue(null);
                //resBusqueda.setHeight("10px");
                grid.setItems(ControladorUsuarioGrupo.getInstance().getAll());
            }
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
      @Override
    protected void eventDeleteButtonGrid(UsuarioGrupo obj) {
        try {
            ui.addWindow(new UsuarioGrupoModalDelete(obj.getId()));
        } catch (Exception ex) {
            Logger.getLogger(UsuarioGrupoDlg.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    protected void buttonAddEvent(){
        ui.addWindow(new UsuarioGrupoModalWin());
    }

    @Override
    protected void gridEvent() {
    }

    @Override
    protected void eventEditButtonGrid(UsuarioGrupo obj){
        ui.addWindow(new UsuarioGrupoModalWin(obj.getId()));
    }

    @Override
    protected void eventAsistenciaButton(UsuarioGrupo obj,String idBtn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventListaAsistentes(UsuarioGrupo obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventWebinarsAgremiado(UsuarioGrupo obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventTutorialSesiones(UsuarioGrupo obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
