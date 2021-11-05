package com.tiamex.siicomeii.test;

import com.tiamex.siicomeii.controlador.ControladorAsistenciaWebinar;
import com.tiamex.siicomeii.persistencia.entidad.AsistenciaWebinar;

/**@author Bellcross**/
public class Insert_11_AsistenciaWebinar {

    public static void main(String[] args) throws Exception {
        int w = 1, u = 1;
        AsistenciaWebinar objAsistenciaWebinar;

        for (int i = 1; i <= 21; i++) {
            objAsistenciaWebinar = new AsistenciaWebinar();
            objAsistenciaWebinar.setAgremiado(1);
            objAsistenciaWebinar.setUsuario(u);
            objAsistenciaWebinar.setWebinar(i);
            ControladorAsistenciaWebinar.getInstance().save(objAsistenciaWebinar);

            objAsistenciaWebinar = new AsistenciaWebinar();
            objAsistenciaWebinar.setAgremiado(2);
            objAsistenciaWebinar.setUsuario(u);
            objAsistenciaWebinar.setWebinar(i);
            ControladorAsistenciaWebinar.getInstance().save(objAsistenciaWebinar);

            objAsistenciaWebinar = new AsistenciaWebinar();
            objAsistenciaWebinar.setAgremiado(3);
            objAsistenciaWebinar.setUsuario(u);
            objAsistenciaWebinar.setWebinar(i);
            ControladorAsistenciaWebinar.getInstance().save(objAsistenciaWebinar);

            objAsistenciaWebinar = new AsistenciaWebinar();
            objAsistenciaWebinar.setAgremiado(4);
            objAsistenciaWebinar.setUsuario(u);
            objAsistenciaWebinar.setWebinar(i);
            ControladorAsistenciaWebinar.getInstance().save(objAsistenciaWebinar);
        }

    }

}

/*
objAsistenciaWebinar = new AsistenciaWebinar();
        objAsistenciaWebinar.setAgremiado(1);
        objAsistenciaWebinar.setUsuario(1);
        objAsistenciaWebinar.setWebinar(1);
        ControladorAsistenciaWebinar.getInstance().save(objAsistenciaWebinar);
        
        objAsistenciaWebinar = new AsistenciaWebinar();
        objAsistenciaWebinar.setAgremiado(2);
        objAsistenciaWebinar.setUsuario(2);
        objAsistenciaWebinar.setWebinar(2);
        ControladorAsistenciaWebinar.getInstance().save(objAsistenciaWebinar);
        
        objAsistenciaWebinar = new AsistenciaWebinar();
        objAsistenciaWebinar.setAgremiado(3);
        objAsistenciaWebinar.setUsuario(3);
        objAsistenciaWebinar.setWebinar(3);
        ControladorAsistenciaWebinar.getInstance().save(objAsistenciaWebinar);
*/
