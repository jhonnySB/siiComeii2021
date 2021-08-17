package com.tiamex.siicomeii.vista.administracion.proximowebinar;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author fred **/
public class ProximoWebinarDlg extends TemplateDlg<ProximoWebinar>{

    Label lblFecha ;
    Label lblTime;
    Label lblEstado ;
    ProximoWebinar proxWeb;
    
    public ProximoWebinarDlg() throws Exception{
        init();
    }

    private void init(){
        banBoton = 4;
        
        grid.addColumn(ProximoWebinar::getTitulo).setCaption("Titulo").setHidable(false).setMaximumWidth(200);
        grid.addComponentColumn(this::buildFechaForm).setCaption("Fecha").setMinimumWidth(600).setHidable(true).setHidingToggleCaption("Mostrar Fecha").setResizable(true);
        grid.addColumn(ProximoWebinar::getPonente).setCaption("Ponente").setHidable(true).setHidingToggleCaption("Mostrar Ponente");
        grid.addColumn(ProximoWebinar::getInstitucion).setCaption("Institución").setHidable(true).setHidingToggleCaption("Mostrar Institución");
        grid.addColumn(ProximoWebinar::getImagen).setCaption("Imagen").setHidable(true).setHidden(true).setHidingToggleCaption("Mostrar Imagen");
        
        setCaption("<b>Próximos webinars</b>");
        eventMostrar();
    }
    
    private ResponsiveLayout buildFechaForm(ProximoWebinar webinar) {
        ResponsiveLayout layout = new ResponsiveLayout();
        ResponsiveRow row = layout.addRow().withAlignment(Alignment.MIDDLE_LEFT);
        Element.cfgLayoutComponent(row, true, false);
        
        proxWeb = webinar;

        //
        buildDatesLabels();
        //
        
        row.addColumn().withComponent(lblFecha);
        row.addColumn().withComponent(lblTime);
        row.addColumn().withComponent(lblEstado);
        
        return layout;
    }
    
    public void buildDatesLabels(){
        
        boolean antiguo = false;
        
        lblFecha = new Label(); lblTime = new Label(); lblEstado=new Label();
        
        lblFecha.setValue(proxWeb.getFechaFrm());
        lblFecha.setResponsive(true);
        lblFecha.setWidthFull();
        lblTime.setResponsive(true);
        lblTime.setWidthFull();

        lblFecha.setContentMode(ContentMode.HTML);
        lblTime.setContentMode(ContentMode.HTML);
        lblEstado.setContentMode(ContentMode.HTML);
        
        long diasTotales = ChronoUnit.DAYS.between(LocalDate.now(), proxWeb.getFecha().toLocalDate()); //System.out.println("diasTotales: "+diasTotales);
        int signedDias = Long.signum(diasTotales); //System.out.println("signedDias: "+signedDias);
        int unsignedDias = (int) Math.abs(diasTotales); //System.out.println("unsignedDias: "+unsignedDias);

        if (signedDias < 0) { // fecha antigua (<0)
            antiguo = true;
            if (unsignedDias < 7) { // dias menor a una semana
                switch (unsignedDias) {
                    case 1:
                        lblTime.setValue("<span style=\"background-color:#17a2b8;padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Ayer.</span>");
                        break;
                    case 2:
                        lblTime.setValue("<span style=\"background-color:#17a2b8;padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Antier.</span>");
                        break;
                    default:
                        lblTime.setValue("<span style=\"background-color:#17a2b8;padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Hace " + unsignedDias + " días.</span>");
                        break;
                }
            } else if (unsignedDias < 30) { // menor a 1 mes (semanas)
                int semanas = unsignedDias / 7;
                if (unsignedDias == 7) {
                    lblTime.setValue("<span style=\"background-color:#17a2b8;padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Hace una semana.</span>");
                } else {
                    if (unsignedDias % 7 == 0) { // semanas completas
                        lblTime.setValue("<span style=\"background-color:#17a2b8;padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Hace " + semanas + " semanas.</span>");
                    } else { //semanas con días
                        if (unsignedDias > 7 && unsignedDias < 14) {
                            lblTime.setValue("<span style=\"background-color:#17a2b8;padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Hace más de una semana.</span>");
                        } else {
                            lblTime.setValue("<span style=\"background-color:#17a2b8;padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Hace más de " + semanas + " semanas.</span>");
                        }
                    }
                }
            } else if (unsignedDias < 365) { // mayor o igual a 1 mes
                int meses = unsignedDias / 31;
                if (unsignedDias == 31) {
                    lblTime.setValue("<span style=\"background-color:#17a2b8;padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Hace un mes.</span>");
                } else {
                    if (unsignedDias % 31 == 0) {
                        //System.out.println("multiplo 7");
                        lblTime.setValue("<span style=\"background-color:#17a2b8;padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Hace " + meses + " meses.</span>");
                    } else {
                        //System.out.println("semanas con dias");
                        if (unsignedDias > 31 && unsignedDias < 62) {
                            //System.out.println("1 semana con dias");
                            lblTime.setValue("<span style=\"background-color:#17a2b8;padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Hace más de un mes.</span>");
                        } else {
                            //System.out.println("Mas semanas");
                            lblTime.setValue("<span style=\"background-color:#17a2b8;padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Hace más de " + meses + " meses.</span>");
                        }
                    }
                }
            } else {
                int años = unsignedDias / 365;
                if (unsignedDias == 365) {
                    lblTime.setValue("<span style=\"background-color:#17a2b8;padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Hace un año.</span>");
                } else {
                    if (unsignedDias % 365 == 0) {
                        lblTime.setValue("<span style=\"background-color:#17a2b8;padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Hace " + años + " años.</span>");
                    } else {
                        if (unsignedDias > 365 && unsignedDias < 730) {
                            lblTime.setValue("<span style=\"background-color:#17a2b8;padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Hace más de un año.</span>");
                        } else {
                            lblTime.setValue("<span style=\"background-color:#17a2b8;padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Hace más de " + años + " años.</span>");
                        }
                    }
                }
            }

        } else if (signedDias > 0) { // fecha proxima (>0)
            if (unsignedDias < 7) { // dias menor a una semana
                switch (unsignedDias) {
                    case 1:
                        lblTime.setValue("<span style=\"background-color:rgba(0,123,255,0.8);padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Mañana.</span>");
                        break;
                    case 2:
                        lblTime.setValue("<span style=\"background-color:rgba(0,123,255,0.8);padding:3px 6px;color:white;border-radius:0px;font-size:12px\">Pasado mañana.</span>");
                        break;
                    default:
                        lblTime.setValue("<span style=\"background-color:rgba(0,123,255,0.8);padding:3px 6px;color:white;border-radius:0px;font-size:12px\">En " + unsignedDias + " días.</span>");
                        break;
                }
            } else if (unsignedDias < 30) { // menor a 1 mes (semanas)
                int semanas = unsignedDias / 7;
                if (unsignedDias == 7) {
                    lblTime.setValue("<span style=\"background-color:rgba(0,123,255,0.8);padding:3px 6px;color:white;border-radius:0px;font-size:12px\">En una semana.</span>");
                } else {
                    if (unsignedDias % 7 == 0) { // semanas completas
                        lblTime.setValue("<span style=\"background-color:rgba(0,123,255,0.8);padding:3px 6px;color:white;border-radius:0px;font-size:12px\">En " + semanas + " semanas.</span>");
                    } else { //semanas con días
                        if (unsignedDias > 7 && unsignedDias < 14) {
                            lblTime.setValue("<span style=\"background-color:rgba(0,123,255,0.8);padding:3px 6px;color:white;border-radius:0px;font-size:12px\">A más de una semana.</span>");
                        } else {
                            lblTime.setValue("<span style=\"background-color:rgba(0,123,255,0.8);padding:3px 6px;color:white;border-radius:0px;font-size:12px\">A más de " + semanas + " semanas.</span>");
                        }
                    }
                }
            } else if (unsignedDias < 365) { // mayor o igual a 1 mes
                int meses = unsignedDias / 31;
                if (unsignedDias == 31) {
                    lblTime.setValue("<span style=\"background-color:rgba(0,123,255,0.8);padding:3px 6px;color:white;border-radius:0px;font-size:12px\">En un mes.</span>");
                } else {
                    if (unsignedDias % 31 == 0) {
                        //System.out.println("multiplo 7");
                        lblTime.setValue("<span style=\"background-color:rgba(0,123,255,0.8);padding:3px 6px;color:white;border-radius:0px;font-size:12px\">En " + meses + " meses.</span>");
                    } else {
                        //System.out.println("semanas con dias");
                        if (unsignedDias > 31 && unsignedDias < 62) {
                            //System.out.println("1 semana con dias");
                            lblTime.setValue("<span style=\"background-color:rgba(0,123,255,0.8);padding:3px 6px;color:white;border-radius:0px;font-size:12px\">A más de un mes.</span>");
                        } else {
                            //System.out.println("Mas semanas");
                            lblTime.setValue("<span style=\"background-color:rgba(0,123,255,0.8);padding:3px 6px;color:white;border-radius:0px;font-size:12px\">A más de " + meses + " meses.</span>");
                        }
                    }
                }
            } else {
                int años = unsignedDias / 365;
                if (unsignedDias == 365) {
                    lblTime.setValue("<span style=\"background-color:rgba(0,123,255,0.8);padding:3px 6px;color:white;border-radius:0px;font-size:12px\">En un año.</span>");
                } else {
                    if (unsignedDias % 365 == 0) {
                        lblTime.setValue("<span style=\"background-color:rgba(0,123,255,0.8);padding:3px 6px;color:white;border-radius:0px;font-size:12px\">En " + años + " años.</span>");
                    } else {
                        if (unsignedDias > 365 && unsignedDias < 730) {
                            lblTime.setValue("<span style=\"background-color:rgba(0,123,255,0.8);padding:3px 6px;color:white;border-radius:0px;font-size:12px\">A más de un año.</span>");
                        } else {
                            lblTime.setValue("<span style=\"background-color:rgba(0,123,255,0.8);padding:3px 6px;color:white;border-radius:0px;font-size:12px\">A más de " + años + " años.</span>");
                        }
                    }
                }
            }

        } else { // fecha actual ==0
            antiguo = true;
            lblTime.setValue("<span style=\"background-color:#28a745;padding:3px 6px;color:white;border-radius:0px;font-size:13px\">Hoy.</span>");
        }

        if (antiguo == true) {
            WebinarRealizado webR = (WebinarRealizado) ControladorWebinarRealizado.getInstance().getByNames(proxWeb.getTitulo());
            if (webR != null) {
                upWebinar.setDescription("Ver detalles del webinar realizado");
                lblEstado.setValue("<span style=\"display:inline-block;border-radius:100px;background-color:rgba(0, 204, 40, 0.4);"
                        + "color:white;font-style:normal;height:16px;width:16px;padding:0px 2px 7px 5px\">✔</span>");
                lblEstado.setDescription("Archivado en webinars realizados");
            } else {
                lblEstado.setValue("<span style=\"display:inline-block;border-radius:100px;background-color:rgba(255, 40, 40, 0.4);"
                        + "color:white;font-style:normal;height:23px;width:23px;padding:0px 0px 0px 0px;text-align:center;font-family:Lora;\">i</span>");
                lblEstado.setDescription("No se ha archivado en webinars realizados");
            }
        } else {
            upWebinar.setEnabled(antiguo);
            upWebinar.setDescription("El webinar está próximo o en progreso...");
        }
    }
    
    
    @Override
    protected void eventMostrar() { 
        grid.setItems(ControladorProximoWebinar.getInstance().getAll());
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            if(!searchField.isEmpty()){
                resBusqueda.setHeight("35px");
                String strBusqueda = searchField.getValue();
                Collection<ProximoWebinar> webinars = ControladorProximoWebinar.getInstance().getByTitulo(strBusqueda);
                int proxWebinar = webinars.size();
                if(proxWebinar>1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;font-family:Open Sans;\">Se encontraron "+Integer.toString(proxWebinar)+" coincidencias para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else if(proxWebinar==1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;fotn-family:Open Sans;\">Se encontró "+Integer.toString(proxWebinar)+" coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else{
                     resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:16px;font-family:Open Sans\">No se encontro ninguna coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>"); 
                }
                grid.setItems(webinars);
            }else{
                resBusqueda.setValue(null);
                resBusqueda.setHeight("10px");
                grid.setItems(ControladorProximoWebinar.getInstance().getAll());
            }
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    @Override
    protected void eventDeleteButtonGrid(ProximoWebinar obj) {
        try {
            ui.addWindow(new ProximoWebinarModalDelete(obj.getId()));
        } catch (Exception ex) {
            Logger.getLogger(ProximoWebinarDlg.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    protected void buttonAddEvent(){
        ui.addWindow(new ProximoWebinarModalWin());
    }

    @Override
    protected void gridEvent() {
    }

    @Override
    protected void eventEditButtonGrid(ProximoWebinar obj) {
        ui.addWindow(new ProximoWebinarModalWin(obj.getId()));
    }

    @Override
    protected void eventAsistenciaButton(ProximoWebinar obj,String idBtn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventListaAsistentes(ProximoWebinar obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventWebinarsAgremiado(ProximoWebinar obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventTutorialSesiones(ProximoWebinar t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
