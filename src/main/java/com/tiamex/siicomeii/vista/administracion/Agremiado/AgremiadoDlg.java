package com.tiamex.siicomeii.vista.administracion.Agremiado;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.reportes.base.pdf.ListadoAgremiadosPDF;
import com.tiamex.siicomeii.reportes.base.pdf.ListadoWebinarsPDF;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.ShowPDFDlg;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author fred **/
public class AgremiadoDlg extends TemplateDlg<Agremiado> {

    private Button listadoAgremiados;


    public AgremiadoDlg() throws Exception{  //Constructor de la clase AgremiadoDlg
        init();
    }

    private void init() {    //Columnas que son asignadas a la tabla de agremiado en la interfaz web
        banBoton = 2;
        grid.addColumn(Agremiado::getNombre).setCaption("Nombre");
        grid.addColumn(Agremiado::getCorreo).setCaption("Correo");
        grid.addColumn(Agremiado::getInstitucion).setCaption("Institución");
        grid.addColumn(Agremiado::getObjGradoEstudio).setCaption("Grado estudio");
        grid.addColumn(Agremiado::getObjPais).setCaption("País");
        grid.addColumn(Agremiado::getSexo).setCaption("Sexo"); 


        listadoAgremiados = new Button();
        //Element.cfgComponent(listadoAgremiados);
        listadoAgremiados.setResponsive(true);
        listadoAgremiados.setCaption("Descargar PDF");
        listadoAgremiados.setDescription("Descargar lista de agremiados");
        listadoAgremiados.setIcon(VaadinIcons.FILE_TEXT_O);
        //listadoAgremiados.setCaption("Generar reportes");
        listadoAgremiados.addClickListener(event -> {
            eventoBotonListadoAgremiados();
        });

        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);
        contenido.setResponsive(true);
        contenido.setWidth("100%");
        contenido.addRow().withComponents(listadoAgremiados).withAlignment(Alignment.TOP_RIGHT).withSpacing(true).setSizeFull();

        //ResponsiveRow row1 = contenido.addRow().withComponents(listadoAgremiados).withAlignment(Alignment.TOP_RIGHT);
        //Element.cfgLayoutComponent(row1,true,false);
        //row1.addColumn().withDisplayRules(12,6,3,2).withComponent(listadoAgremiados);
        contentLayout.addComponent(contenido);
        setCaption("<b>Agremiados</b>");
        eventMostrar();
    }
    
    
    @Override
    protected void eventMostrar(){ 
        grid.setItems(ControladorAgremiado.getInstance().getAll());  
        
    }

    @Override
    protected void buttonSearchEvent(){
        try{
            if(!searchField.isEmpty()){
                resBusqueda.setHeight("35px");
                resBusqueda.setContentMode(ContentMode.HTML);
                String strBusqueda = searchField.getValue();
                Collection<Agremiado> agremiados = ControladorAgremiado.getInstance().getByName(strBusqueda);
                int listSize = agremiados.size();
                if(listSize>1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;font-family:Open Sans;\">Se encontraron "+Integer.toString(listSize)+" coincidencias para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else if(listSize==1){
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;fotn-family:Open Sans;\">Se encontró "+Integer.toString(listSize)+" coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>");
                }else{
                     resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:16px;font-family:Open Sans\">No se encontro ninguna coincidencia para la búsqueda '"+strBusqueda+"'"+" </span></b>"); 
                }
                grid.setItems(agremiados);
            }else{
                resBusqueda.setValue(null);
                resBusqueda.setHeight("10px");
                grid.setItems(ControladorAgremiado.getInstance().getAll());
            }
            
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
                @Override
    protected void eventDeleteButtonGrid(Agremiado obj) {
        try {
            ui.addWindow(new AgremiadoModalDelete(obj.getId()));
        } catch (Exception ex) {
            Logger.getLogger(AgremiadoDlg.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    private void eventoBotonListadoAgremiados() {
        try {
            ui.addWindow(new ShowPDFDlg(new StreamResource(new ListadoAgremiadosPDF(), (Utils.getFormatIdLong() + ".pdf").replace(" ", ""))));
        } catch (Exception ex) {
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

    @Override
    protected void eventAsistenciaButton(Agremiado obj,String idBtn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventListaAsistentes(Agremiado obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventWebinarsAgremiado(Agremiado obj) {
        try {
            ui.addWindow(new ShowPDFDlg(new StreamResource(new ListadoWebinarsPDF(obj), (Utils.getFormatIdLong() + ".pdf").replace(" ", ""))));
        } catch (Exception ex) {
            Logger.getLogger(AgremiadoDlg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void eventTutorialSesiones(Agremiado obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}