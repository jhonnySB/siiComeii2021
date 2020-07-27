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
@Table(name="tutorial")
@NamedQueries({
})
public class Tutorial implements Serializable{
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
    
    @Basic(optional=false)
    @Column(name="usuario",nullable=false)
    private long usuario;
    public long getUsuario(){return usuario;}
    public void setUsuario(long valor){usuario = valor;}
    
    /** Relaciones **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario",referencedColumnName="id",insertable=false,updatable=false)
    private Usuario objUsuario;
    public Usuario getObjUsuario(){return objUsuario;}
    
    @OneToMany(mappedBy="objTutorial",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<TutorialSesion> listaTutorialSesion;
    public List<TutorialSesion> getListaTutorialSesion(){return listaTutorialSesion;}
    
    /** Constructores **/
    public Tutorial(){
    }
    
    /** Metodos
     * @return  **/
    @Override
    public String toString(){return nombre;}
}
