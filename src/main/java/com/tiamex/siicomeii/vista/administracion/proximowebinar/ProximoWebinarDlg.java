package com.tiamex.siicomeii.vista.administracion.proximowebinar;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.vaadin.data.HasValue;
import com.vaadin.data.Result;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.SerializableComparator;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.vaadin.hene.popupbutton.PopupButton;

/**
 * @author fred *
 */
public class ProximoWebinarDlg extends TemplateDlg<ProximoWebinar> {

    ListDataProvider<ProximoWebinar> dataProvider = DataProvider.ofCollection(ControladorProximoWebinar.getInstance().getAll());
    Label lblFecha; // ;
    Label lblTime;
    Label lblEstado;
    ProximoWebinar proxWeb;
    DateField fechaInicioF, fechaFinF;
    int resFilterSize[] = new int[]{0};
    Button btnClear, btnToday;
    ZoneId zoneId = ZoneId.of("America/Mexico_City");
    List<ProximoWebinar> filterList = null;
    public TextField cpySearchFld;
    public ProximoWebinarDlg() throws Exception {
        init();
    }

    private void init() {
        cpySearchFld = searchField;
        banBoton = 4;
        grid.setHeaderRowHeight(40);
        searchField.setPlaceholder("Buscar por título,ponente,instituto");
        SerializableComparator<ProximoWebinar> comparator = (web1, web2) -> {
            int y1 = web1.getFecha().getYear(), y2 = web2.getFecha().getYear(),
                    m1 = web1.getFecha().getMonthValue(), m2 = web2.getFecha().getMonthValue(),
                    d1 = web1.getFecha().getDayOfMonth(), d2 = web2.getFecha().getDayOfMonth();
            if (y1 < y2) {
                return -1;
            }
            if (y1 > y2) {
                return 1;
            }
            if (m1 < m2) {
                return -1;
            }
            if (m1 > m2) {
                return 1;
            }
            if (d1 < d2) {
                return -1;
            }
            if (d1 > d2) {
                return 1;
            }
            return 0;
        };
        int minWidth = 200;
        int maxWidth = 300;
        grid.addColumn(ProximoWebinar::getTitulo).setCaption("Titulo").setHidable(false).setMinimumWidth(minWidth).setMaximumWidth(maxWidth)
                .setDescriptionGenerator(web->web.getTitulo());
        grid.addColumn(ProximoWebinar::getPonente).setCaption("Ponente").setHidable(true).setHidingToggleCaption("Mostrar Ponente").
                setMinimumWidth(minWidth).setMaximumWidth(maxWidth).setDescriptionGenerator(web->web.getPonente());
        grid.addColumn(ProximoWebinar::getInstitucion).setCaption("Institución").setHidable(true).setHidingToggleCaption("Mostrar Institución")
                .setMinimumWidth(150).setMaximumWidth(maxWidth).setDescriptionGenerator(web->web.getInstitucion());
        grid.addComponentColumn(this::buildFechaForm).setCaption("Fecha").setMinimumWidth(525).setHidable(true).setHidingToggleCaption("Mostrar Fecha")
                .setComparator(comparator).setId("dateCol");
        grid.addComponentColumn((ProximoWebinar web) -> {
            ResponsiveLayout lay = new ResponsiveLayout(); lay.setResponsive(true);
            lay.setSizeFull(); lay.setSpacing();
            if (web.getImagen() != null) {
                lay.addRow().withAlignment(Alignment.MIDDLE_CENTER).
                        withComponents(createPopupImageBtn(web.getImagen(), web.getTitulo()));
            }else
                lay.addRow().withAlignment(Alignment.MIDDLE_CENTER).withComponents(new Label("Sin imagen"));
            return lay;
        }).setCaption("Imagen").setHidable(true).setHidden(false).setHidingToggleCaption("Mostrar imagen").setResizable(false)
                .setMinimumWidth(150).setMaximumWidth(150);
        setCaption("<b>Próximos webinars</b>");

        HeaderRow filterHeader = grid.appendHeaderRow();
        filterHeader.getCell("dateCol").setComponent(buildFilterDate());
        eventMostrar();
        enableFilters();
    }

    private void enableFilters() {
        if (dataProvider.getItems().isEmpty()) {
            fechaInicioF.setEnabled(false);
        }
    }

    public ResponsiveLayout buildFilterDate() {
        ResponsiveLayout lay = new ResponsiveLayout();
        lay.setResponsive(true);
        lay.setSizeFull();
        fechaInicioF = buildDateField("Fecha inicio", "Selecciona la fecha de inicio", "");
        fechaFinF = buildDateField("Fecha fin", "Selecciona la fecha final", "");
        fechaFinF.setEnabled(false);
        Label label = new Label("-");
        label.setResponsive(true);
        btnClear = new Button("Limpiar");
        btnClear.setResponsive(true);
        btnClear.setDescription("Reiniciar las celdas de selección de fecha");
        btnClear.setEnabled(false);
        btnClear.addClickListener((Button.ClickListener) event -> {
            fechaInicioF.setValue(null);
            fechaFinF.setValue(null);
            fechaFinF.setEnabled(false);
            btnClear.setEnabled(false);
            dataProvider.clearFilters();
        });
        btnClear.addStyleNames(ValoTheme.BUTTON_TINY, ValoTheme.BUTTON_LINK);
        btnToday = new Button("Hoy");
        btnToday.setResponsive(true);
        btnToday.addStyleNames(ValoTheme.BUTTON_TINY, ValoTheme.BUTTON_LINK);
        btnToday.addClickListener((Button.ClickListener) event -> {
            fechaInicioF.setValue(LocalDate.now(zoneId));
        });

        ResponsiveRow row = lay.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        row.withComponents(fechaInicioF, fechaFinF, btnToday, btnClear);
        row.setHorizontalSpacing(ResponsiveRow.SpacingSize.SMALL, true);
        row.setSizeFull();

        fechaInicioF.addValueChangeListener((HasValue.ValueChangeListener) event -> {
            if (event.getValue() != null) {
                if(fechaFinF.isEmpty()){
                    filterDate(fechaInicioF);
                    fechaFinF.setEnabled(true);
                    btnClear.setEnabled(true);
                }else{
                    dataProvider.setFilter(filter());
                }
                fechaFinF.setRangeStart(fechaInicioF.getValue().plusDays(1));
            }
        });
        fechaFinF.addValueChangeListener((HasValue.ValueChangeListener) event -> {
            if (event.getValue() != null) {
                dataProvider.setFilter(filter());
                fechaInicioF.setRangeEnd(((LocalDate) event.getValue()).minusDays(1));
            }
        });
        return lay;
    }

    private SerializablePredicate<ProximoWebinar> filter() {
        SerializablePredicate<ProximoWebinar> columnPredicate;
        columnPredicate = proxEvento -> {
            LocalDateTime fechaInicio = fechaInicioF.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime fechaFin = fechaFinF.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime newFecha = proxEvento.getFecha().withHour(0).withMinute(0).withSecond(0);
            LocalDateTime newFechaIn = fechaInicio.withHour(0).withMinute(0).withSecond(0);
            LocalDateTime newFechaFin = fechaFin.withHour(0).withMinute(0).withSecond(0);
            return newFechaIn.compareTo(newFecha) * newFecha.compareTo(newFechaFin) >= 0;
        };

        return columnPredicate;
    }

    public DateField buildDateField(String placeHolder, String description, String caption) {
        DateField dateField = new DateField() {
            @Override
            protected Result<LocalDate> handleUnparsableDateString(String dateString) {
                try {
                    LocalDate parsedAtServer = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
                    return Result.ok(parsedAtServer);
                } catch (DateTimeParseException e) {
                    return Result.error("Fecha no válida");
                }
            }
        };
        dateField.setResponsive(true);
        dateField.setTextFieldEnabled(false);
        dateField.setDefaultValue(LocalDate.now(ZoneId.systemDefault()));
        dateField.addStyleName(ValoTheme.DATEFIELD_TINY);
        dateField.setPlaceholder(placeHolder);
        dateField.setDescription(description);
        dateField.setShowISOWeekNumbers(true);
        dateField.setZoneId(ZoneId.of("America/Mexico_City"));
        dateField.setResolution(DateResolution.DAY);
        dateField.setLocale(new Locale("es", "MX"));
        return dateField;
    }

    private void filterDate(DateField fechaInicioF) {
        LocalDateTime fechaInicio = fechaInicioF.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        dataProvider.setFilter((ProximoWebinar::getFecha), fecha -> {
            if (fecha == null) {
                return false;
            }
            LocalDateTime newFecha = fecha.withHour(0).withMinute(0).withSecond(0);
            LocalDateTime newFechaIn = fechaInicio.withHour(0).withMinute(0).withSecond(0);
            return newFechaIn.compareTo(newFecha) == 0;
        });

    }

    private PopupButton createPopupImageBtn(byte[] bytes, String title) {
        PopupButton popBtn = new PopupButton();
        VerticalLayout vL = new VerticalLayout();
        vL.setResponsive(true);
        vL.addStyleName(ValoTheme.ACCORDION_BORDERLESS);
        vL.setSpacing(false);
        vL.setMargin(false);
        popBtn.setCaption("Ver imagen");
        popBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        popBtn.setDirection(Alignment.BOTTOM_CENTER);
        Image img = new Image();
        img.setSource(new StreamResource(() -> new ByteArrayInputStream(bytes), "image_proxWebinar_" + title));
        img.setWidth(250, Unit.PIXELS);
        img.setHeight(250, Unit.PIXELS);
        img.setResponsive(true);
        img.setAlternateText("Cargando imagen...");
        Button closeBtn = new Button();
        closeBtn.setIcon(VaadinIcons.CLOSE_SMALL);
        closeBtn.addStyleName(ValoTheme.BUTTON_TINY);
        closeBtn.addStyleName(ValoTheme.BUTTON_DANGER);
        closeBtn.addClickListener((ClickListener) listener -> {
            popBtn.setPopupVisible(false);
        });
        vL.addComponent(closeBtn);
        vL.addComponent(img);
        popBtn.setContent(vL);
        return popBtn;
    }

    private ResponsiveLayout buildFechaForm(ProximoWebinar webinar) {
        ResponsiveLayout layout = new ResponsiveLayout();
        ResponsiveRow row = layout.addRow().withAlignment(Alignment.MIDDLE_LEFT);
        //Element.cfgLayoutComponent(row, true, false);
        row.setWidth("100%");
        row.setResponsive(true);
        row.setCaptionAsHtml(true);
        row.setHorizontalSpacing(ResponsiveRow.SpacingSize.SMALL, true);
        proxWeb = webinar;
        buildDatesLabels();
        row.addColumn().withComponent(lblFecha);
        row.addColumn().withComponent(lblTime);
        row.addColumn().withComponent(lblEstado);

        return layout;
    }

    public void buildDatesLabels() {

        int antiguo = -1;

        lblFecha = new Label();
        lblTime = new Label();
        lblEstado = new Label();

        lblFecha.setValue(proxWeb.getFechaFrm());
        lblFecha.setResponsive(true);
        lblFecha.setWidthFull();
        lblTime.setResponsive(true);
        lblTime.setWidthFull();

        lblFecha.setContentMode(ContentMode.HTML);
        lblTime.setContentMode(ContentMode.HTML);
        lblEstado.setContentMode(ContentMode.HTML);

        long diasTotales = ChronoUnit.DAYS.between(LocalDate.now(ZoneId.of("America/Mexico_City")), proxWeb.getFecha().toLocalDate());
        int signedDias = Long.signum(diasTotales);
        int unsignedDias = (int) Math.abs(diasTotales);
        
        if (signedDias < 0) { // fecha antigua (<0)
            antiguo = 1;
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
            antiguo = 0;
            lblTime.setValue("<span style=\"background-color:#28a745;padding:3px 6px;color:white;border-radius:0px;font-size:13px\">Hoy.</span>");
        }

        if (antiguo==1) {
            WebinarRealizado webR = (WebinarRealizado) ControladorWebinarRealizado.getInstance().getByNames(proxWeb.getTitulo());
            if (webR != null) {
                delete.setEnabled(false);
                btnEdit.setEnabled(false);
                upWebinar.setEnabled(false);
                upWebinar.setDescription("Ver detalles del webinar realizado");
                lblEstado.setValue("<span style=\"display:inline-block;border-radius:100px;background-color:rgba(0, 204, 40, 0.4);"
                        + "color:white;font-style:normal;height:16px;width:16px;padding:0px 2px 7px 5px\">✔</span>");
                lblEstado.setDescription("Archivado en webinars realizados");
            } else {
                upWebinar.setEnabled(false);
                lblEstado.setValue("<span style=\"display:inline-block;border-radius:100px;background-color:rgba(255, 40, 40, 0.4);"
                        + "color:white;font-style:normal;height:23px;width:23px;padding:0px 0px 0px 0px;text-align:center;font-family:Lora;\">i</span>");
                lblEstado.setDescription("No se ha archivado en webinars realizados");
            }
        } else {
            if(antiguo==-1){
                upWebinar.setEnabled(false);
                upWebinar.setDescription("El webinar está próximo o en progreso...");
            }else
                upWebinar.setEnabled(true);
            
        }
    }

    @Override
    protected void eventMostrar() {
        dataProvider = DataProvider.ofCollection(ControladorProximoWebinar.getInstance().getAll());
        grid.setDataProvider(dataProvider);
    }

    @Override
    protected void buttonSearchEvent() {
        try {
            if (!searchField.isEmpty()) {
                String searchTxt = searchField.getValue();
                dataProvider.setFilter(filterAllByString(searchTxt));
                resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:14px;fotn-family:Lora;"
                        + "letter-spacing: 1px;\">No se encontro ninguna coincidencia para la búsqueda '" + searchTxt + "'" + " </span></b>");
            } else {
                resBusqueda.setValue(null);
                dataProvider.clearFilters();
            }

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    private SerializablePredicate<ProximoWebinar> filterAllByString(String searchTxt) {
        SerializablePredicate<ProximoWebinar> predicate;
        predicate = (webinar) -> {
            String title = webinar.getTitulo(), ponente = webinar.getPonente(), inst = webinar.getInstitucion();
            Pattern pattern = Pattern.compile(Pattern.quote(searchTxt), Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(title).find() || pattern.matcher(ponente).find() || pattern.matcher(inst).find()) {
                resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:14px;fotn-family:Lora;"
                        + "letter-spacing: 1px;\">Se encontraron coincidencias para la búsqueda '" + searchTxt + "'" + " </span></b>");
                return true;
            }
            return false;
        };
        return predicate;
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
    protected void buttonAddEvent() {
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
    protected void eventAsistenciaButton(ProximoWebinar obj, String idBtn) {
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
