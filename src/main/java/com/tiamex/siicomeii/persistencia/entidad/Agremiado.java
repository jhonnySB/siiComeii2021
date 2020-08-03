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
@Table(name="agremiado")
@NamedQueries({
})
public class Agremiado implements Serializable{
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
    @Column(name="sexo",nullable=false)
    private char  sexo;
    public char getSexo(){return sexo;}
    public void setSexo(char valor){sexo = valor;}
    
    @Basic(optional=false)
    @Column(name="institucion",nullable=false)
    private String institucion;
    public String getInstitucion(){return institucion;}
    public void setInstitucion(String valor){institucion = valor;}
    
    @Basic(optional=false)
    @Column(name="pais",nullable=false)
    private String pais;
    public String getPais(){return pais;}
    public void setPais(String valor){pais = valor;}

    @Basic(optional=false)
    @Column(name="gradoEstudio",nullable=false)
    private String gradoEstudio;
    public String getGradoEstudios(){return gradoEstudio;}
    public void setGradoEstudios(String valor){gradoEstudio = valor;}
    
    
    /** Relaciones **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais",referencedColumnName="id",insertable=false,updatable=false)
    private Pais objPais;
    public Pais getObjPais(){return objPais;}
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gradoEstudio",referencedColumnName="id",insertable=false,updatable=false)
    private GradoEstudio objGradoEstudio;
    public GradoEstudio getObjGradoEstudio(){return objGradoEstudio;}
    
    @OneToMany(mappedBy="objAgremiado",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<AsistenciaWebinar> listaAsistenciaWebinar;
    public List<AsistenciaWebinar> geListaAsistenciaWebinar(){return listaAsistenciaWebinar;}
    
    /** Constructores **/
    public Agremiado(){
    }
    
    /** Metodos
     * @return  **/
    @Override
    public String toString(){return nombre;}
}