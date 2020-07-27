package com.tiamex.siicomeii.persistencia.entidad;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
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
    
    @OneToMany(mappedBy="objUsuario",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<AsistenciaWebinar> listaAsistenciaWebinar;
    public List<AsistenciaWebinar> geListaAsistenciaWebinar(){return listaAsistenciaWebinar;}
    
    @OneToMany(mappedBy="objUsuario",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<ProximoWebinar> listaProximoWebinar;
    public List<ProximoWebinar> geListaProximoWebinar(){return listaProximoWebinar;}
    
    @OneToMany(mappedBy="objUsuario",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<ProximoEvento> listaProximoEvento;
    public List<ProximoEvento> getListaProximoEvento(){return listaProximoEvento;}
    
    @OneToMany(mappedBy="objUsuario",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Tutorial> listaTutorial;
    public List<Tutorial> getListaTutorial(){return listaTutorial;}
    
    @OneToMany(mappedBy="objUsuario",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<TutorialSesion> listaTutorialSesion;
    public List<TutorialSesion> getListaTutorialSesion(){return listaTutorialSesion;}
    
    /** Constructores **/
    public Usuario(){
    }
    
    /** Metodos
     * @return  **/
    @Override
    public String toString(){return nombre;}
}
