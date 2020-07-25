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
@Table(name="usuario")
@NamedQueries({
})
public class Usuario implements Serializable{
    @Id
    @Basic(optional=false)
    @Column(name="id",nullable=false)
    private long id;
    public long getId(){return id;}
    public void setId(long valor){id = valor;}
    
    @Basic(optional=false)
    @Column(name="nombre",nullable=false)
    private String  nombre;
    public String getNombre(){return nombre;}
    public void setNombre(String valor){nombre = valor;}
    
    @Basic(optional=false)
    @Column(name="correo",nullable=false)
    private String  correo;
    public String getCorreo(){return correo;}
    public void setCorreo(String valor){correo = valor;}
    
    @Basic(optional=false)
    @Column(name="password",nullable=false)
    private String  password;
    public String getPassword(){return password;}
    public void setPassword(String valor){password = valor;}
    
    @Basic(optional=false)
    @Column(name="activo",nullable=false)
    private boolean activo;
    public boolean getActivo(){return activo;}
    public void setActivo(boolean valor){activo = valor;}
    
    @Basic(optional=false)
    @Column(name="cambiarPassword",nullable=false)
    private boolean cambiarPassword;
    public boolean getCambiarPassword(){return cambiarPassword;}
    public void setCambiarPassword(boolean valor){cambiarPassword = valor;}
    
    @Basic(optional=false)
    @Column(name="usuarioGrupo",nullable=false)
    private long usuarioGrupo;
    public long getUsuarioGrupo(){return usuarioGrupo;}
    public void setUsuarioGrupo(long valor){usuarioGrupo = valor;}
    
    /** Relaciones **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioGrupo",referencedColumnName="id",insertable=false,updatable=false)
    private UsuarioGrupo objUsuarioGrupo;
    public UsuarioGrupo getObjUsuarioGrupo(){return objUsuarioGrupo;}
    
    /** Constructores **/
    public Usuario(){
    }
    
    /** Metodos **/
    @Override
    public String toString(){return nombre;}
}
