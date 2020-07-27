package com.tiamex.siicomeii.persistencia.entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/** @author cerimice **/

@Entity
@Table(name="webinarRealizado")
@NamedQueries({
})
public class WebinarRealizado implements Serializable{
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
    @Column(name="ponente",nullable=false)
    private String  ponente;
    public String getPonente(){return ponente;}
    public void setPonente(String valor){ponente = valor;}
    
    @Basic(optional=false)
    @Column(name="institucion",nullable=false)
    private String institucion;
    public String getInstitucion(){return institucion;}
    public void setInstitucion(String valor){institucion = valor;}
    
    @Basic(optional=false)
    @Column(name="fecha",nullable=false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    public Date getFecha(){return fecha;}
    public void setFecha(Date valor){fecha = valor;}
    
    @Lob
    @Column(name="presentacion",nullable=false,columnDefinition = "TEXT",length=512)
    private String presentacion;
    public String getPresentacion(){return presentacion;}
    public void setPresentacion(String valor){presentacion = valor;}
    
    @Lob
    @Column(name="urlYoutube",nullable=false,columnDefinition = "TEXT",length=512)
    private String urlYoutube;
    public String getUrlYoutube(){return urlYoutube;}
    public void setUrlYoutube(String valor){urlYoutube = valor;}
    
    /** Relaciones **/
    @OneToMany(mappedBy="objWebinarRealizado",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<AsistenciaWebinar> listaAsistenciaWebinar;
    public List<AsistenciaWebinar> getListaAsistenciaWebinar(){return listaAsistenciaWebinar;}
    
    /** Constructores **/
    public WebinarRealizado(){
    }
    
    /** Metodos
     * @return  **/
    @Override
    public String toString(){return nombre;}
}
