package com.tiamex.siicomeii.vista.administracion.usuario;

import com.tiamex.siicomeii.controlador.ControladorUsuario;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import java.util.logging.Logger;

/** @author cerimice **/
public class UsuarioDlg extends TemplateDlg<Usuario>{
    
    public UsuarioDlg(){
        init();
    }
    
    private void init(){
        grid.addColumn(Usuario::getNombre).setCaption("Nombre");
        grid.addColumn(Usuario::getCorreo).setCaption("Correo");
        grid.addColumn(Usuario::getUsuarioGrupo).setCaption("Grupo");
        
        buttonSearchEvent();
    }
    
    @Override
    protected void buttonSearchEvent(){
        try{
            grid.setItems(ControladorUsuario.getInstance().getAll());
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent() {
    }

    @Override
    protected void gridEvent() {
    }
    
    @Override
    protected void eventEditButtonGrid(Usuario obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
