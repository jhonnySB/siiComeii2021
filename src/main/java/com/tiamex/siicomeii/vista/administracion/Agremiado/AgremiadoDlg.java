package com.tiamex.siicomeii.vista.administracion.Agremiado;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorProximoEvento;
import com.tiamex.siicomeii.persistencia.entidad.Agremiado;
import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;
import com.tiamex.siicomeii.reportes.base.pdf.ListadoAgremiadosPDF;
import com.tiamex.siicomeii.reportes.base.pdf.ListadoWebinarsPDF;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.ShowPDFDlg;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author fred *
 */
public class AgremiadoDlg extends TemplateDlg<Agremiado> {

    private Button listadoAgremiados;
    ListDataProvider<Agremiado> dataProvider = DataProvider.ofCollection(ControladorAgremiado.getInstance().getAll());
    List<Agremiado> filterList;
    DateField fechaInicioF;
    DateField fechaFinF;
    Button btnClear;

    public AgremiadoDlg() throws Exception {  //Constructor de la clase AgremiadoDlg
        init();
    }

    private void init() {    //Columnas que son asignadas a la tabla de agremiado en la interfaz web
        banBoton = 2;
        grid.addColumn(Agremiado::getNombre).setCaption("Nombre").setHidable(false);
        grid.addColumn(Agremiado::getCorreo).setCaption("Correo").setHidable(true).setHidingToggleCaption("Mostrar Correo").setHidden(true);
        grid.addColumn(Agremiado::getInstitucion).setCaption("Institución").setHidable(false);
        grid.addColumn(Agremiado::getObjGradoEstudio).setCaption("Grado estudio").setHidable(true).setHidingToggleCaption("Mostrar Grado Estudio");
        grid.addColumn(Agremiado::getObjPais).setCaption("País").setHidable(true).setHidingToggleCaption("Mostrar País");
        grid.addColumn(Agremiado::getFechaReg).setCaption("Fecha registro").setHidable(true).setHidingToggleCaption("Mostrar Fecha registro")
                .setId("colFechaReg");
        grid.addColumn(agremiadoBean -> (agremiadoBean.getSexo() == 'H' ? "Hombre" : "Mujer")).setCaption("Género")
                .setHidable(true).setHidingToggleCaption("Mostrar Género").setHidden(true);

        HeaderRow headerTitulo = grid.appendHeaderRow();
        headerTitulo.getCell("colFechaReg").setComponent(buildFilterDate());

        ///////////////////////
        listadoAgremiados = new Button();
        listadoAgremiados.setResponsive(true);
        listadoAgremiados.setCaption("Descargar PDF");
        listadoAgremiados.setDescription("Descargar lista de agremiados");
        listadoAgremiados.setIcon(VaadinIcons.FILE_TEXT_O);
        listadoAgremiados.addClickListener(event -> {
            eventoBotonListadoAgremiados();
        });

        ResponsiveLayout contenido = new ResponsiveLayout();
        Element.cfgLayoutComponent(contenido);
        contenido.setResponsive(true);
        contenido.setWidth("100%");
        contenido.addRow().withComponents(listadoAgremiados).withAlignment(Alignment.TOP_RIGHT).withSpacing(true).setSizeFull();

        contentLayout.addComponent(contenido);
        setCaption("<b>Agremiados</b>");
        eventMostrar();
    }

    public HorizontalLayout buildFilterDate() {
        HorizontalLayout hLayout = new HorizontalLayout();
        fechaInicioF = new DateField();
        fechaInicioF.setZoneId(ZoneId.systemDefault());
        fechaInicioF.addStyleName(ValoTheme.DATEFIELD_TINY);
        fechaInicioF.setWidth("150px");
        fechaInicioF.setPlaceholder("Fecha inicio");
        fechaInicioF.setDescription("Selecciona la fecha de inicio");
        fechaFinF = new DateField();
        fechaFinF.setZoneId(ZoneId.systemDefault());
        fechaFinF.addStyleName(ValoTheme.DATEFIELD_TINY);
        fechaFinF.setWidth("150px");
        fechaFinF.setPlaceholder("Fecha fin");
        fechaFinF.setDescription("Selecciona la fecha final");
        if (dataProvider.getItems().isEmpty()) {
            fechaInicioF.setEnabled(false);
            fechaFinF.setEnabled(false);
        }
        Label label = new Label("-");
        hLayout.addComponent(fechaInicioF);
        hLayout.addComponent(label);
        hLayout.addComponent(fechaFinF);
        btnClear = new Button();
        btnClear.setIcon(VaadinIcons.TRASH);
        btnClear.setDescription("Limpiar");
        if (fechaInicioF.getValue() == null || fechaFinF.getValue() == null) {
            btnClear.setEnabled(false);
        }
        btnClear.addClickListener((Button.ClickListener) event -> {
            fechaInicioF.setValue(null);
            fechaFinF.setValue(null);
            btnClear.setEnabled(false);
            System.out.println(filterList);
        });
        btnClear.addStyleName(ValoTheme.BUTTON_TINY); //btnClear.addStyleName(ValoTheme.BUTTON_ICON_ONLY); 
        hLayout.addComponent(btnClear);

        //fechaInicioF.addValueChangeListener(filterDate(fechaInicioF, fechaFinF, "colFecha"));
        fechaInicioF.addValueChangeListener((HasValue.ValueChangeListener) event -> {
            if (fechaInicioF.getValue() != null && fechaFinF.getValue() == null) {
                //filterDate(fechaInicioF);
                filterList = new ArrayList<>();
                dataProvider.setFilter(filter());
                btnClear.setEnabled(true);
            } else {
                if (fechaInicioF.getValue() != null && fechaFinF.getValue() != null) {
                    filterList = new ArrayList<>();
                    dataProvider.setFilter(filter());
                    btnClear.setEnabled(true);
                } else {
                    dataProvider.clearFilters();

                }
            }

        });

        //fechaFinF.addValueChangeListener(filterDate(fechaInicioF, fechaFinF, "colFecha"));
        fechaFinF.addValueChangeListener((HasValue.ValueChangeListener) event -> {

            if (fechaInicioF.getValue() != null && fechaFinF.getValue() != null) {
                btnClear.setEnabled(true);
                filterList = new ArrayList<>();
                dataProvider.setFilter(filter());
            } else {
                dataProvider.clearFilters();
            }
        });
        System.out.println("List: " + filterList);
        return hLayout;
    }

    private SerializablePredicate<Agremiado> filter() {
        SerializablePredicate<Agremiado> columnPredicate = null;
        columnPredicate = agremiado -> {
            System.out.println(agremiado.getNombre());
            LocalDate fechaInicio = fechaInicioF.getValue();
            LocalDate fechaFin = fechaFinF.getValue();
            LocalDate newFecha = agremiado.getFechaReg();
            if (fechaInicio.compareTo(newFecha) * newFecha.compareTo(fechaFin) >= 0) {
                System.out.println(">=");
                if (!filterList.contains(agremiado)) {
                    filterList.add(agremiado);
                }
            }
            return fechaInicio.compareTo(newFecha) * newFecha.compareTo(fechaFin) >= 0;
        };

        return columnPredicate;
    }

    private void filterDate(DateField fechaInicioF) {
        LocalDate fechaInicio = fechaInicioF.getValue();
        dataProvider.setFilter((Agremiado::getFechaReg), fecha -> {
            if (fecha == null) {
                return false;
            }
            return fechaInicio.compareTo(fecha) == 0;
        });

    }

    @Override
    protected void eventMostrar() {
        dataProvider = DataProvider.ofCollection(ControladorAgremiado.getInstance().getAll());
        grid.setDataProvider(dataProvider);
    }

    @Override
    protected void buttonSearchEvent() {
        try {
            if (!searchField.isEmpty()) {
                resBusqueda.setHeight("35px");
                resBusqueda.setContentMode(ContentMode.HTML);
                String strBusqueda = searchField.getValue();
                Collection<Agremiado> agremiados = ControladorAgremiado.getInstance().getByName(strBusqueda);
                int listSize = agremiados.size();
                if (listSize > 1) {
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;font-family:Open Sans;\">Se encontraron " + Integer.toString(listSize) + " coincidencias para la búsqueda '" + strBusqueda + "'" + " </span></b>");
                } else if (listSize == 1) {
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;fotn-family:Open Sans;\">Se encontró " + Integer.toString(listSize) + " coincidencia para la búsqueda '" + strBusqueda + "'" + " </span></b>");
                } else {
                    resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:16px;font-family:Open Sans\">No se encontro ninguna coincidencia para la búsqueda '" + strBusqueda + "'" + " </span></b>");
                }
                grid.setItems(agremiados);
            } else {
                resBusqueda.setValue(null);
                resBusqueda.setHeight("10px");
                grid.setItems(ControladorAgremiado.getInstance().getAll());
            }

        } catch (Exception ex) {
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
    protected void eventAsistenciaButton(Agremiado obj, String idBtn) {
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
