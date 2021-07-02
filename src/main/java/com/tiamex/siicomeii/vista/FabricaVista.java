package com.tiamex.siicomeii.vista;

import com.tiamex.siicomeii.vista.administracion.Agremiado.AgremiadoDlg;
import com.tiamex.siicomeii.vista.administracion.GradoEstudio.GradoEstudioDlg;
import com.tiamex.siicomeii.vista.administracion.Pais.PaisDlg;
import com.tiamex.siicomeii.vista.administracion.ProximoEvento.ProximoEventoDlg;
import com.tiamex.siicomeii.vista.administracion.WebinarRealizado.WebinarRealizadoDlg;
import com.tiamex.siicomeii.vista.administracion.tutorialSesion.TutorialSesionDlg;
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
    public UsuarioDlg getUsuarioDlg() throws Exception{
        if(usuarioDlg == null){usuarioDlg = new UsuarioDlg();}
        return usuarioDlg;
    }
    
    public UsuarioGrupoDlg usuarioGrupoDlg;
    public UsuarioGrupoDlg getUsuarioGrupoDlg() throws Exception{
        if(usuarioGrupoDlg == null){usuarioGrupoDlg = new UsuarioGrupoDlg();}
        return usuarioGrupoDlg;
    }
    
    public GradoEstudioDlg gradoEstudioDlg;
    public GradoEstudioDlg getGradoEstudioDlg() throws Exception{
        if(gradoEstudioDlg == null){gradoEstudioDlg = new GradoEstudioDlg();}
        return gradoEstudioDlg;
    }
    
    public ProximoWebinarDlg proximoWebinarDlg;
    public ProximoWebinarDlg getProximoWebinarDlg() throws Exception{
        if(proximoWebinarDlg == null){proximoWebinarDlg = new ProximoWebinarDlg();}
        return proximoWebinarDlg;
    }
    
    public TutorialDlg tutorialDlg;
    public TutorialDlg getTutorialDlg() throws Exception{
        if(tutorialDlg == null){tutorialDlg = new TutorialDlg();}
        return tutorialDlg;
    }

    public TutorialSesionDlg tutorialSesionDlg;
    public TutorialSesionDlg getTutorialsesionDlg(long tutorial) throws Exception{
    
        if(tutorialSesionDlg!=null){ // 
            if(tutorialSesionDlg.tutorialLinked!=tutorial){ // 
                tutorialSesionDlg = null; 
            }else{
                return tutorialSesionDlg;
            }
        }
            
        tutorialSesionDlg = new TutorialSesionDlg(tutorial);
        return tutorialSesionDlg;
    }

    public ProximoEventoDlg proximoEventoDlg;
    public ProximoEventoDlg getProximoEventoDlg() throws Exception{
        if(proximoEventoDlg == null){proximoEventoDlg = new ProximoEventoDlg();}
        return proximoEventoDlg;
    }
    
    public AgremiadoDlg agremiadoDlg;
    public AgremiadoDlg getAgremiadoDlg() throws Exception{
        if(agremiadoDlg == null){agremiadoDlg = new AgremiadoDlg();}
        return agremiadoDlg;
    }
    
    public PaisDlg paisDlg;
    public PaisDlg getPaisDlg() throws Exception{
        if(paisDlg == null){paisDlg = new PaisDlg();}
        return paisDlg;
    }

    public WebinarRealizadoDlg WebinarRealizadoDlg;
    public WebinarRealizadoDlg getWebinarRealizadoDlg() throws Exception{
        if(WebinarRealizadoDlg == null){WebinarRealizadoDlg = new WebinarRealizadoDlg();}
        return WebinarRealizadoDlg;
    }
    
}
