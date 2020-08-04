package com.tiamex.siicomeii.vista;

import com.tiamex.siicomeii.vista.administracion.Agremiado.AgremiadoDlg;
import com.tiamex.siicomeii.vista.administracion.GradoEstudio.GradoEstudioDlg;
import com.tiamex.siicomeii.vista.administracion.ProximoEvento.ProximoEventoDlg;
import com.tiamex.siicomeii.vista.administracion.usuario.UsuarioDlg;
import com.tiamex.siicomeii.vista.administracion.usuarioGrupo.UsuarioGrupoDlg;
import com.tiamex.siicomeii.vista.proximowebinar.ProximoWebinarDlg;
import com.tiamex.siicomeii.vista.tutorial.TutorialDlg;

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
    
    public ProximoWebinarDlg proximoWebinarDlg;
    public ProximoWebinarDlg getProximoWebinarDlg(){
        if(proximoWebinarDlg == null){proximoWebinarDlg = new ProximoWebinarDlg();}
        return proximoWebinarDlg;
    }
    
    public TutorialDlg tutorialDlg;
    public TutorialDlg getTutorialDlg(){
        if(tutorialDlg == null){tutorialDlg = new TutorialDlg();}
        return tutorialDlg;
    }
    
    public ProximoEventoDlg proximoEventoDlg;
    public ProximoEventoDlg getProximoEventoDlg(){
        if(proximoEventoDlg == null){proximoEventoDlg = new ProximoEventoDlg();}
        return proximoEventoDlg;
    }
    
    public AgremiadoDlg agremiadoDlg;
    public AgremiadoDlg getAgremiadoDlg(){
        if(agremiadoDlg == null){agremiadoDlg = new AgremiadoDlg();}
        return agremiadoDlg;
    }
}
