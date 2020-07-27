package com.tiamex.siicomeii.persistencia.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/** @author cerimice **/

@Entity
@Table(name="tutorialSesion")
@NamedQueries({
})
public class TutorialSesion implements Serializable{
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
    @Column(name="tutor",nullable=false)
    private String  tutor;
    public String getTutor(){return tutor;}
    public void setTutor(String valor){tutor = valor;}
    
    @Basic(optional=false)
    @Column(name="institucion",nullable=false)
    private String institucion;
    public String getInstitucion(){return institucion;}
    public void setInstitucion(String valor){institucion = valor;} 
    
    @Lob
    @Column(name="urlYoutube",nullable=false,columnDefinition = "TEXT",length=512)
    private String urlYoutube;
    public String getUrlYoutube(){return urlYoutube;}
    public void setUrlYoutube(String valor){urlYoutube = valor;}
    
    @Basic(optional=false)
    @Column(name="usuario",nullable=false)
    private long usuario;
    public long getUsuario(){return usuario;}
    public void setUsuario(long valor){usuario = valor;}
    
    /** Relaciones **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutorial",referencedColumnName="id",insertable=false,updatable=false)
    private Tutorial objTutorial;
    public Tutorial getObjTutorial(){return objTutorial;}
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario",referencedColumnName="id",insertable=false,updatable=false)
    private Usuario objUsuario;
    public Usuario getObjUsuario(){return objUsuario;}
    
    /** Constructores **/
    public TutorialSesion(){
    }
    
    /** Metodos **/
    @Override
    public String toString(){return nombre;}
}
