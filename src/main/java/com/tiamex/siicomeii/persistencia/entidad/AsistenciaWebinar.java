package com.tiamex.siicomeii.persistencia.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/** @author cerimice **/

@Entity
@Table(name="asistenciaWebinar")
@NamedQueries({
})
public class AsistenciaWebinar implements Serializable{
    @Id
    @Basic(optional=false)
    @Column(name="id",nullable=false)
    private long id;
    public long getId(){return id;}
    public void setId(long valor){id = valor;}
    
    @Basic(optional=false)
    @Column(name="webinar",nullable=false)
    private long webinar;
    public long getWebinar(){return webinar;}
    public void setWebinar(long valor){webinar = valor;}
    
    @Basic(optional=false)
    @Column(name="agremiado",nullable=false)
    private long agremiado;
    public long getAgremiado(){return agremiado;}
    public void setAgremiado(long valor){agremiado = valor;}
   
    @Basic(optional=false)
    @Column(name="usuario",nullable=false)
    private long usuario;
    public long getUsuario(){return usuario;}
    public void setUsuario(long valor){usuario = valor;}
    
    /** Relaciones **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webinar",referencedColumnName="id",insertable=false,updatable=false)
    private Usuario objWebinarRealizado;
    public Usuario getObjWebinarRealizado(){return objWebinarRealizado;}
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agremaido",referencedColumnName="id",insertable=false,updatable=false)
    private Usuario objAgremiado;
    public Usuario getObjAgremiado(){return objAgremiado;}
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario",referencedColumnName="id",insertable=false,updatable=false)
    private Usuario objUsuario;
    public Usuario getObjUsuario(){return objUsuario;}
    
    /** Constructores **/
    public AsistenciaWebinar(){
    }
    
    /** Metodos
     * @return  **/
    //@Override
    //public String toString(){return nombre;}
}
