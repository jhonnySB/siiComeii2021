package com.tiamex.siicomeii.vista.utils;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.SiiComeiiUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author cerimice *
 */
/**
 * @company tiamex *
 */
public abstract class TemplateModalWin extends Window {

    protected SiiComeiiUI ui;

    protected long id;

    protected VerticalLayout contentLayout;
    protected Button delete;
    protected Button accept;
    protected Button cancel;
    protected Button lblIcon;
    protected CheckBox editBox;
    ResponsiveLayout actions;

    public TemplateModalWin() {
        initDlg();
    }

    private void initDlg() {
        ui = Element.getUI();
        actions = new ResponsiveLayout();

        editBox = new CheckBox();

        accept = new Button("Aceptar");
        accept.setClickShortcut(13);
        //Element.cfgComponent(accept);
        accept.setWidthFull();
        accept.setResponsive(true);
        accept.setCaptionAsHtml(true);
        accept.addClickListener((Button.ClickEvent event) -> {
            buttonAcceptEvent();
        });
        accept.setStyleName(ValoTheme.BUTTON_PRIMARY);

        cancel = new Button("Cancelar");
        //Element.cfgComponent(cancel);
        cancel.setWidthFull();
        cancel.setResponsive(true);
        cancel.setCaptionAsHtml(true);
        cancel.addClickListener((Button.ClickEvent event) -> {
            buttonCancelEvent();
        });
        cancel.setStyleName(ValoTheme.BUTTON_DANGER);
        ResponsiveRow row1 = actions.addRow();
        row1.setSpacing(ResponsiveRow.SpacingSize.SMALL, true);
        Label brLbl = new Label();
        brLbl.setResponsive(true);
        brLbl.addStyleNames(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_TINY);
        row1.addColumn().withDisplayRules(12, 12, 12, 12).withComponent(brLbl);
        row1.addColumn().withDisplayRules(12, 12, 6, 6).withComponent(accept);
        row1.addColumn().withDisplayRules(12, 12, 6, 6).withComponent(cancel);
        contentLayout = new VerticalLayout();
        Element.cfgLayoutComponent(contentLayout, false, false);

        VerticalLayout main = new VerticalLayout();
        //Element.cfgLayoutComponent(main,true,true);
        main.setResponsive(true);
        main.setSpacing(true);
        main.addComponent(contentLayout);
        main.addComponent(actions);
        main.setSizeUndefined();
        //main.setComponentAlignment(contentLayout,Alignment.MIDDLE_CENTER);

        this.setContent(main);
        this.setModal(true);
        this.setClosable(true);
        this.setResizable(false);
        this.setCaptionAsHtml(true);
        this.setSizeUndefined();
        //this.setWidth(Element.windowWidthPercent());
        //this.addStyleName(ValoTheme.WINDOW_TOP_TOOLBAR);
    }

    protected abstract void loadData(long id);

    protected abstract void buttonAcceptEvent();

    protected abstract void buttonCancelEvent();
}
