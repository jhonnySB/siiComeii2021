package com.tiamex.siicomeii.vista;

import com.tiamex.siicomeii.vista.administracion.GradoEstudio.GradoEstudioDlg;
import com.tiamex.siicomeii.vista.administracion.usuario.UsuarioDlg;
import com.tiamex.siicomeii.vista.administracion.usuarioGrupo.UsuarioGrupoDlg;

/** @author cerimice **/
public class FabricaVista{
    
    public MainPanel mainPanel;
    public MainPanel getMainPanel(){
        if(mainPanel == null){mainPanel = new MainPanel();}
        return mainPanel;
    }
    
    public UsuarioDlg usuarioDlg;
    public UsuarioDlg getUsuarioDlg(){
        if(usuarioDlg == null){usuarioDlg = new UsuarioDlg();}
        return usuarioDlg;
    }
    
    public UsuarioGrupoDlg usuarioGrupoDlg;
    public UsuarioGrupoDlg getUsuarioGrupoDlg(){
        if(usuarioGrupoDlg == null){usuarioGrupoDlg = new UsuarioGrupoDlg();}
        return usuarioGrupoDlg;
    }
    
    public GradoEstudioDlg gradoEstudioDlg;
    public GradoEstudioDlg getGradoEstudioDlg(){
        if(gradoEstudioDlg == null){gradoEstudioDlg = new GradoEstudioDlg();}
        return gradoEstudioDlg;
    }
}
