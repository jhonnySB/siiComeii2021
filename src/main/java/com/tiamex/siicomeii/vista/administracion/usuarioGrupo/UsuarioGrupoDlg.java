package com.tiamex.siicomeii.vista.administracion.usuarioGrupo;

import com.tiamex.siicomeii.controlador.ControladorUsuarioGrupo;
import com.tiamex.siicomeii.persistencia.entidad.UsuarioGrupo;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import java.util.logging.Logger;

/** @author cerimice **/
public class UsuarioGrupoDlg extends TemplateDlg<UsuarioGrupo>{

    public UsuarioGrupoDlg(){
        init();
    }

    private void init(){
        grid.addColumn(UsuarioGrupo::getId).setCaption("Id");
        grid.addColumn(UsuarioGrupo::getNombre).setCaption("Nombre");
        setCaption("<b>Grupo de usuarios</b>");
        buttonSearchEvent();
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            grid.setItems(ControladorUsuarioGrupo.getInstance().getByName(searchField.getValue()));
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
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
