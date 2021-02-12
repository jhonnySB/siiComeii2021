/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiamex.siicomeii.controlador;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author fred
 */
public class ControladorNotificaciones extends VerticalLayout {

    public ControladorNotificaciones(int flag){
        mensages(flag);
    }

    private void mensages(int fl) {
        Notification notification = new Notification("SiiComeii | Tiamex");
        switch (fl){
            case 1:
                notification.setDescription("<span>Es necesario proporcionar CORREO</span>");
                break;
            case 2:
                notification.setDescription("<span>Es necesario proporcionar COTRASEÑA</span>");
                break;
            case 3:
                notification.setDescription("<span>El correo no existe</span>");
                break;

            case 4:
                notification.setDescription("<span>Contraseña incorrecta</span>");
                break;
        }
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.TOP_CENTER);
        notification.setDelayMsec(500);
        notification.show(Page.getCurrent());
    }
}