package com.tiamex.siicomeii.persistencia.entidad;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/** @author fred **/

@Entity
@Table(name="proximoWebinar")
@NamedQueries({
})
public class ProximoWebinar implements Serializable{
    @Id
    @Basic(optional=false)
    @Column(name="id",nullable=false)
    private long id;
    public long getId(){return id;}
    public void setId(long valor){id = valor;}
    
    @Basic(optional=false)
    @Column(name="titulo",nullable=false)
    private String  titulo;
    public String getTitulo(){return titulo;}
    public void setTitulo(String valor){titulo = valor;}
    
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
    private LocalDateTime fecha;
    public String getFechaFrm(){
        return fecha.format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' y hh:mm", new Locale("es", "MX")));
    }
    public LocalDateTime getFecha(){return fecha;}
    public void setFecha(LocalDateTime valor){fecha = valor;}
    
    @Basic(optional=false)
    @Column(name="imagen",nullable=false,columnDefinition = "LONGTEXT")
    private String imagen;
    public String getImagen(){return imagen;}
    public void setImagen(String valor){imagen = valor;}
    
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
    
    /** Constructores **/
    public ProximoWebinar(){
    }
    
    /** Metodos
     * @return  **/
    @Override
    public String toString(){return titulo;}
}
