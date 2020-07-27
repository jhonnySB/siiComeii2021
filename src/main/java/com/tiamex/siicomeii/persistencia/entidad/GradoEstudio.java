package com.tiamex.siicomeii.persistencia.entidad;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** @author cerimice **/

@Entity
@Table(name="gradoEstudio")
@NamedQueries({
})
public class GradoEstudio implements Serializable{
    @Id
    @Basic(optional=false)
    @Column(name="id",nullable=false)
    private long id;
    public long getId(){return id;}
    public void setId(long valor){id = valor;}
    
    @Basic(optional=false)
    @Column(name="nombre",nullable=false)
    private String nombre;
    public String getNombre(){return nombre;}
    public void setNombre(String valor){nombre = valor;}
    
    /** Relaciones **/
    @OneToMany(mappedBy="objGradoEstudio",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Agremiado> listaAgremiados;
    public List<Agremiado> getListaAgremiados(){return listaAgremiados;}
    
    /** Constructores **/
    public GradoEstudio(){
    }
    
    /** Metodos
     * @return  **/
    @Override
    public String toString(){return nombre;}
}
