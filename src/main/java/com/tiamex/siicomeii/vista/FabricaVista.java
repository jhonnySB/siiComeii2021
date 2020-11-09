package com.tiamex.siicomeii.vista;

import com.tiamex.siicomeii.vista.administracion.Agremiado.AgremiadoDlg;
import com.tiamex.siicomeii.vista.administracion.GradoEstudio.GradoEstudioDlg;
import com.tiamex.siicomeii.vista.administracion.Pais.PaisDlg;
import com.tiamex.siicomeii.vista.administracion.ProximoEvento.ProximoEventoDlg;
import com.tiamex.siicomeii.vista.administracion.WebinarRealizado.WebinarRealizadoDlg;
import com.tiamex.siicomeii.vista.administracion.WebinarRealizado.WebinarRealizadoModalWin;
import com.tiamex.siicomeii.vista.administracion.usuario.UsuarioDlg;
import com.tiamex.siicomeii.vista.administracion.usuarioGrupo.UsuarioGrupoDlg;
import com.tiamex.siicomeii.vista.administracion.proximowebinar.ProximoWebinarDlg;
import com.tiamex.siicomeii.vista.administracion.tutorial.TutorialDlg;
import java.io.IOException;

/** @author fred **/
public class FabricaVista{
    
    public MainPanel mainPanel;
    public MainPanel getMainPanel() throws IOException{
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
    
    public PaisDlg paisDlg;
    public PaisDlg getPaisDlg(){
        if(paisDlg == null){paisDlg = new PaisDlg();}
        return paisDlg;
    }

    public WebinarRealizadoDlg WebinarRealizadoDlg;
    public WebinarRealizadoDlg getWebinarRealizadoDlg(){
        if(WebinarRealizadoDlg == null){WebinarRealizadoDlg = new WebinarRealizadoDlg();}
        return WebinarRealizadoDlg;
    }
    
}
