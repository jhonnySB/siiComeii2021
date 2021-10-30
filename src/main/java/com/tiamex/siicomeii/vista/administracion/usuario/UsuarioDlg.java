package com.tiamex.siicomeii.vista.administracion.usuario;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorUsuario;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.tiamex.siicomeii.vista.utils.UploadReceiverImg;
import com.tiamex.siicomeii.vista.utils.UploadReceiverSql;
import com.vaadin.addons.AutocompleteOffExtension;
import com.vaadin.data.Binder;
import com.vaadin.data.ReadOnlyHasValue;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Page;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.server.Setter;
import com.vaadin.server.UserError;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.batik.gvt.flow.MarginInfo;
import org.vaadin.hene.popupbutton.PopupButton;
import org.vaadin.hene.popupbutton.PopupButton.PopupVisibilityListener;

/**
 * @author cerimice *
 */
public class UsuarioDlg extends TemplateDlg<Usuario> {

    private TextField userLbl;
    private Label fileInfoLbl;
    private Upload fileUploader;
    private Button sqlButton, restoreDb, cancelBtn, modalRestoreDb;
    private PasswordField passwordFld;
    Label statusLabel;
    PopupButton popupBtn;
    UploadReceiverSql receiver;
    ListDataProvider<Usuario> dataProvider = 
            DataProvider.ofCollection(ControladorUsuario.getInstance().getAll());

    public UsuarioDlg() throws Exception {
        init();
    }

    private void init() throws Exception {
        btnAdd.setCaption("Nuevo registro");
        searchField.setPlaceholder("Buscar por nombre,correo,grupo..");
        grid.addComponentColumn(this::buildActiveTag).setCaption("Estado").setMinimumWidth(250);
        grid.addColumn(Usuario::getNombre).setCaption("Nombre").setMinimumWidth(200);
        grid.addColumn(Usuario::getCorreo).setCaption("Correo").setMinimumWidth(200);
        grid.addColumn(Usuario::getObjUsuarioGrupo).setCaption("Grupo de usuario").setMinimumWidth(150);

        createDownloadSql();

        setCaption("<b>Usuarios</b>");
        eventMostrar();
    }

    private void createDownloadSql() {
        sqlButton = new Button();
        sqlButton.setResponsive(true);
        sqlButton.setCaption("Respaldar BD"); sqlButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        sqlButton.setDescription("Descargar respaldo de la base de datos");
        sqlButton.setIcon(VaadinIcons.FILE_TEXT_O);
        sqlButton.addClickListener(event -> {
            downlaodSQL();
        });

        modalRestoreDb = createBtn("Restaurar BD", VaadinIcons.ROTATE_LEFT, "");
        modalRestoreDb.setSizeUndefined(); modalRestoreDb.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        modalRestoreDb.setDescription("Restaurar base de datos");
        modalRestoreDb.addClickListener((ClickListener) listener -> {
            openModanRestorewdw();
        });

        statusLabel = createLabel();
        popupBtn = new PopupButton();
        popupBtn.setDirection(Alignment.TOP_LEFT);
        popupBtn.setDescription("Restaurar base de datos");
        popupBtn.setIcon(VaadinIcons.REFRESH);
        popupBtn.addClickListener((ClickListener) e -> {
            statusLabel.setValue("");
            //UploadReceiverSql newReceiver = new UploadReceiverSql(fileUploader, statusLabel);
            //fileUploader.setReceiver(newReceiver);
            //fileUploader.addSucceededListener(newReceiver);
            //fileUploader.addProgressListener(newReceiver);
        });
        userLbl = createLabel("<b>Nombre de usuario:</b>", VaadinIcons.USER);
        userLbl.setSizeFull();
        passwordFld = createPassField("<b>Contraseña:</b>", VaadinIcons.PASSWORD);
        passwordFld.setSizeFull();
        fileUploader = createUploader("<b>Archivo SQL: </b><span style=\"color:red\">*</span>",
                VaadinIcons.FILE_O);
        //receiver = new UploadReceiverSql(fileUploader, statusLabel);
        fileInfoLbl = createLabel();
        //laySql.addRow().withComponents(fileUploader,fileInfoLbl);
        restoreDb = createBtn("Restaurar", null, ValoTheme.BUTTON_PRIMARY);
        restoreDb.addClickListener((e) -> {
            //validateUploader((UploadReceiverSql) fileUploader.getReceiver());
        });
        cancelBtn = createBtn("Cancelar", null, ValoTheme.BUTTON_DANGER);
        cancelBtn.addClickListener((e) -> {
            popupBtn.setPopupVisible(false);
        });

        //btnAdd.setIcon(VaadinIcons.PLUS);
        ResponsiveLayout layout = new ResponsiveLayout();
        ResponsiveRow r = layout.addRow().withAlignment(Alignment.TOP_RIGHT);
        r.setSpacing(true);
        r.addColumn().withComponent(modalRestoreDb);
        r.addColumn().withComponent(sqlButton);
        r.addColumn().withComponent(btnAdd);
        //colSearchField.withComponent(layoutReportes);
        //colSearchField.withDisplayRules(12, 6,6,6);
        
        //rowBar.removeComponent(colBtnSearch);
        rowBar.removeAllComponents();
        rowBar.setSpacing(ResponsiveRow.SpacingSize.SMALL, true); 
        searchField.setWidth(100,Unit.PERCENTAGE);
        rowBar.withComponents(searchField,btnAdd,sqlButton,modalRestoreDb);
        //rowBar.removeComponent(colSearchField);
        //searchField.setWidth(50, Unit.PERCENTAGE);
        //colSearchField.withDisplayRules(12, 12, 6, 6).setAlignment(ResponsiveColumn.ColumnComponentAlignment.LEFT);
        //colBtnAdd.withDisplayRules(12, 12, 6, 6);
        //colBtnAdd.setContent(layout);
    }

    private void openModanRestorewdw() {
        Window wdw = new Window();
        wdw.setResizable(false);
        wdw.setResponsive(true);
        wdw.setCaption("Restaurar base de datos");
        wdw.setModal(true);
        wdw.setWidth(375, Unit.PIXELS);
        
        Label infoLbl = createLabel(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_TINY); infoLbl.setContentMode(ContentMode.HTML);
        infoLbl.setValue("<b>Nombre: -</b><br><b>Tamaño: -</b>");
        infoLbl.setWidth(180, Unit.PIXELS);
        Label errorLbl = createLabel(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_TINY,"label-error-info");
        Upload uploader = new Upload(); errorLbl.setContentMode(ContentMode.HTML);
        uploader.setResponsive(true);
        uploader.setCaption("Archivo SQL: <span style=color:red>*</span>"); 
        uploader.setCaptionAsHtml(true);
        uploader.setDescription("Cargar archivo sql");
        uploader.setButtonCaption("Cargar archivo");
        uploader.setButtonStyleName("v-button friendly");
        UploadReceiverSql newReceiver = new UploadReceiverSql(uploader, infoLbl,errorLbl);
        uploader.setReceiver(newReceiver);
        uploader.addSucceededListener(newReceiver);
        uploader.addProgressListener(newReceiver);
        
        Button accept = createBtn("Restaurar", null,ValoTheme.BUTTON_PRIMARY);
        accept.addClickListener((ClickListener) listener->{
            validateUploader((UploadReceiverSql) uploader.getReceiver(),uploader,errorLbl);
        });
        Button cancel = createBtn("Cancelar", null, ValoTheme.BUTTON_DANGER);
        cancel.addClickListener((ClickListener) listener->{
            wdw.close();
        });
        
        ResponsiveLayout lay = new ResponsiveLayout(); lay.setResponsive(true);
        ResponsiveRow row = lay.addRow();
        row.setSpacing(ResponsiveRow.SpacingSize.SMALL, true);
        row.addColumn().withComponent(uploader).withDisplayRules(12, 12, 6, 6);
        row.addColumn().withComponent(infoLbl).withDisplayRules(12, 12, 6, 6);
        row.addColumn().withComponent(errorLbl).withDisplayRules(12, 12, 12, 12);
        row.addColumn().withComponent(accept).withDisplayRules(12, 12, 6, 6);
        row.addColumn().withComponent(cancel).withDisplayRules(12, 12, 6, 6);
        row.setMargin(ResponsiveRow.MarginSize.SMALL, ResponsiveLayout.DisplaySize.LG);
        wdw.setContent(lay);
        wdw.center();
        ui.addWindow(wdw);
    }

    private boolean validateForm() {
        String requiredMsg = "Campo requerido";
        ValueProvider<TextField, String> getter;
        Setter<TextField, String> setter;
        getter = (txtFld) -> {
            return txtFld.getValue();
        };
        setter = (txtFld, value) -> {
            txtFld.setValue(value);
        };
        Binder<TextField> binderUser = new Binder<>();
        binderUser.forField(userLbl).asRequired(requiredMsg).bind(getter, setter);
        getter = (txtFld) -> {
            return txtFld.getValue();
        };
        setter = (txtFld, value) -> {
            txtFld.setValue(value);
        };
        Binder<TextField> passwordBinder = new Binder<>();
        passwordBinder.forField(passwordFld).asRequired(requiredMsg).bind(getter, setter);
        return binderUser.validate().isOk() & passwordBinder.validate().isOk();
    }

    private boolean validateUploader(UploadReceiverSql receiver,Upload uploader,Label label) {
        Notification not;
        if (receiver.newFileReceived()) {
            String fileNameExt[] = receiver.getFilename().split("\\.");
            if (fileNameExt[fileNameExt.length-1].trim().compareTo("sql") == 0) {
                windowModalRestoreDb(receiver);
            } else {
                not = new Notification("Error", "Archivo no soportado", Notification.Type.ERROR_MESSAGE);
                not.setStyleName("v-Notification error bar v-Notification-error bar v-position-top v-position-left topbar");
                label.setValue("Seleccione un archivo con extensión sql");
                not.show(Page.getCurrent());
            }
        } else {
            String errorMsg = "Debe seleccionar un archivo";
            UserError errorCmpt = new UserError(errorMsg);
            uploader.setComponentError(errorCmpt);
            label.setValue(errorMsg);
        }
        return false;
    }
    
    private void windowModalRestoreDb(UploadReceiverSql receiver){
        Window wd = new Window();
        wd.center();
        wd.setClosable(true);
        wd.setResizable(false);
        VerticalLayout vl = new VerticalLayout();
        Label lbl = new Label(); lbl.setWidth(150, Unit.PIXELS);
        lbl.setValue("Esta acción es irreversible. "
                + "Una vez que termine el proceso de restauración serás dirigido al inicio de sesión");
        lbl.addStyleNames(ValoTheme.LABEL_TINY, ValoTheme.LABEL_BOLD);
        Button btnA = new Button();
        btnA.setCaption("Aceptar");
        btnA.addClickListener((ClickListener) listener -> {
            wd.close();
            if (restoreDb(receiver.getPathSql())) {
                ui.getSession().close();
                ui.getPage().reload();
            } else {
                Notification not = new Notification("Error", "No se pudo completar la acción", Notification.Type.ERROR_MESSAGE);
                not.setStyleName("v-Notification error bar v-Notification-error bar v-position-top v-position-left topbar");
                not.show(Page.getCurrent());
            }
        });
        vl.addComponent(lbl);
        vl.addComponent(btnA);
        wd.setContent(vl);
        ui.addWindow(wd);
    }

    // C:\\xampp\\mysql\\bin\\mysqldump -u siiComeii -psiiComeii.2020 siiComeii
    private boolean restoreDb(String path) {
        //String comm = "C:\\xampp\\mysql\\bin\\mysql.exe -u root siiComeii -e source " + path+"";
        String[] executeCmd = new String[]{"mysql", "--user=" + "root", "siiComeii","-e", " source "+path};  
        System.out.println("Path: " + path);
        //System.out.println("Comm: "+comm);
        Process p;
        try {
            p = Runtime.getRuntime().exec(executeCmd);
            int processComplete = p.waitFor();
            if (processComplete == 0) {
                return true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(UsuarioDlg.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(UsuarioDlg.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private Label createLabel(String... styleNames) {
        Label lbl = new Label();
        lbl.setResponsive(true);
        lbl.addStyleNames(styleNames);
        return lbl;
    }

    private Button createBtn(String caption, VaadinIcons icon, String... styleNames) {
        Button btn = new Button();
        btn.setResponsive(true);
        if(caption!=null)
            btn.setCaption(caption);
        if (icon != null)
            btn.setIcon(icon);
        btn.setCaptionAsHtml(true);
        if(styleNames.length>0)
            btn.addStyleNames(styleNames);
        return btn;
    }

    private Upload createUploader(String caption, VaadinIcons icon) {
        Upload up = new Upload();
        up.setResponsive(true);
        up.setCaption(caption);
        up.setCaptionAsHtml(true);
        up.setButtonCaptionAsHtml(true);
        up.setIcon(icon);
        up.setButtonCaption("Cargar");
        return up;

    }

    private TextField createLabel(String caption, VaadinIcons icon) {
        TextField txtFld = new TextField();
        txtFld.setResponsive(true);
        txtFld.setCaption(caption);
        txtFld.setCaptionAsHtml(true);
        txtFld.setRequiredIndicatorVisible(true);
        txtFld.setIcon(icon);
        return txtFld;
    }

    private PasswordField createPassField(String caption, VaadinIcons icon) {
        PasswordField passFld = new PasswordField();
        passFld.setResponsive(true);
        passFld.setCaption(caption);
        passFld.setCaptionAsHtml(true);
        passFld.setRequiredIndicatorVisible(true);
        passFld.setIcon(icon);
        return passFld;
    }

    private ResponsiveLayout buildActiveTag(Usuario obj) {
        ResponsiveLayout layout = new ResponsiveLayout();
        ResponsiveRow row = layout.addRow().withAlignment(Alignment.MIDDLE_CENTER);
        Element.cfgLayoutComponent(row, true, false);
        row.setHorizontalSpacing(ResponsiveRow.SpacingSize.SMALL, true);
        Label lblActivo = new Label();

        if (obj.getActivo()) {
            lblActivo.setValue("<span style=\"background-color:#28a745;padding:3px 6px;color:white;border-radius:0px;font-size:13px\">Activo</span>");
        } else {
            lblActivo.setValue("<span style=\"background-color:#dc3545;padding:3px 6px;color:white;border-radius:0px;font-size:13px\">Inactivo</span>");
        }
        if (ui.getUsuario().getCorreo().compareTo(obj.getCorreo()) == 0) {
            delete.setEnabled(false);
            delete.setDescription("Cambia de sesión para poder eliminar este usuario");
            Label lblSesion = new Label();
            lblSesion.setValue("<span style=\"background-color:#007bff;padding:3px 6px;color:white;border-radius:0px;font-size:13px\">En sesión</span>");
            lblSesion.setContentMode(ContentMode.HTML);
            row.addColumn().withComponent(lblSesion);
        }

        lblActivo.setContentMode(com.vaadin.shared.ui.ContentMode.HTML);
        row.addColumn().withComponent(lblActivo);

        return layout;
    }

    @Override
    protected void eventMostrar() {
        dataProvider = DataProvider.ofCollection(ControladorUsuario.getInstance().getAll());
        grid.setDataProvider(dataProvider);
    }

    @Override
    protected void buttonSearchEvent() {
        try {
            if (!searchField.isEmpty()) {
                String searchTxt = searchField.getValue();
                //Collection<Usuario> usuarios = ControladorUsuario.getInstance().getByName(strBusqueda);
                dataProvider.setFilter(filterAllByString(searchTxt));
                resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:14px;font-family:Lora"
                        + "letter-spacing:1px\">No se encontro ninguna coincidencia para la búsqueda '" + searchTxt + "'" + " </span></b>");
                
                /*if (userSize > 1) {
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;font-family:Open Sans;\">Se encontraron " + Integer.toString(userSize) + " coincidencias para la búsqueda '" + strBusqueda + "'" + " </span></b>");
                } else if (userSize == 1) {
                    resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:16px;fotn-family:Open Sans;\">Se encontró " + Integer.toString(userSize) + " coincidencia para la búsqueda '" + strBusqueda + "'" + " </span></b>");
                } else {
                    resBusqueda.setValue("<b><span style=\"color:red;display:inline-block;font-size:16px;font-family:Open Sans\">No se encontro ninguna coincidencia para la búsqueda '" + strBusqueda + "'" + " </span></b>");
                }
                grid.setItems(usuarios);*/
            } else {
                resBusqueda.setValue(null);
                dataProvider.clearFilters();
            }

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    private SerializablePredicate<Usuario> filterAllByString(String searchTxt){
        SerializablePredicate<Usuario> predicate;
        predicate = (webinar)->{
            String name = webinar.getNombre(),correo = webinar.getCorreo(),userGroup = webinar.getObjUsuarioGrupo().getNombre();
            Pattern pattern = Pattern.compile(Pattern.quote(searchTxt), Pattern.CASE_INSENSITIVE);
            if(pattern.matcher(name).find() || pattern.matcher(correo).find() || pattern.matcher(userGroup).find()){
                resBusqueda.setValue("<b><span style=\"color:#28a745;display:inline-block;font-size:14px;fotn-family:Lora;"
                        + "letter-spacing: 1px;\">Se encontraron coincidencias para la búsqueda '" + searchTxt + "'" + " </span></b>");
                return true;
            }
            return false;
        };
        return predicate;
    }
    

    @Override
    protected void eventDeleteButtonGrid(Usuario obj) {
        try {
            ui.addWindow(new UsuarioModalDelete(obj.getId()));
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDlg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void buttonAddEvent() {
        ui.addWindow(new UsuarioDlgModalWin());
    }

    @Override
    protected void gridEvent() {
    }

    @Override
    protected void eventEditButtonGrid(Usuario obj) {
        ui.addWindow(new UsuarioDlgModalWin(obj.getId()));
    }

    @Override
    protected void eventAsistenciaButton(Usuario obj, String idBtn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventListaAsistentes(Usuario obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected void downlaodSQL() {
        Locale varRegional = new Locale("es", "MX");
        LocalDate fechaActual = LocalDate.now(ZoneId.of("America/Mexico_City"));
        String fechaFormateada = fechaActual.format(DateTimeFormatter.ofPattern("dd_MM_yyyy", varRegional));

        try {
            //Process p = Runtime.getRuntime().exec("mysqldump -u siiComeii -psiiComeii.2020 siiComeii");
            //Siguiente linea para compilar la base de datos en windows con xampp
            Process p = Runtime.getRuntime().exec("C:\\xampp\\mysql\\bin\\mysqldump -u siiComeii -psiiComeii.2020 siiComeii");
            InputStream is = p.getInputStream();//Pedimos la entrada
            try (FileOutputStream fos = new FileOutputStream("./respaldosBD/" + fechaFormateada + "_backup_siicomeii.sql") //creamos el archivo para le respaldo
                    ) {
                byte[] buffer = new byte[1000]; //Creamos una variable de tipo byte para el buffer
                int leido = is.read(buffer); //Devuelve el número de bytes leídos o -1 si se alcanzó el final del stream.
                while (leido > 0) {
                    fos.write(buffer, 0, leido);//Buffer de caracteres, Desplazamiento de partida para empezar a escribir caracteres, Número de caracteres para escribir
                    leido = is.read(buffer);
                }
                Element.buildSucessNotification().show(Page.getCurrent());
                //Element.makeNotification("SQL de Base de datos guardado", Notification.Type.TRAY_NOTIFICATION, Position.TOP_CENTER).show(ui.getPage());
                //Cierra respaldo
            } //Creamos una variable de tipo byte para el buffer
        } catch (IOException e) {
            Element.buildNotification("Error", "Ocurrió un error al realizar la operación", "bar error closable").show(Page.getCurrent());
            //Element.makeNotification("Error al descargar archivo SQL", Notification.Type.ERROR_MESSAGE, Position.TOP_CENTER).show(ui.getPage());
        }
    }

    @Override
    protected void eventWebinarsAgremiado(Usuario obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void eventTutorialSesiones(Usuario obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
