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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/** @author cerimice **/

@Entity
@Table(name="proximoEvento")
@NamedQueries({
})
public class ProximoEvento implements Serializable{
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
    @Column(name="descripcion",nullable=false,length=300)
    private String  descripcion;
    public String getDescripcion(){return descripcion;}
    public void setDescripcion(String valor){descripcion = valor;}
    
    @Basic(optional=false)
    @Column(name="fecha",nullable=false)
    private LocalDateTime fecha;
    public String getFechaFrm(){
        return fecha.format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' y '('hh:mm a')'", new Locale("es", "MX")));
    }
    public LocalDateTime getFecha(){return fecha;}
    public void setFecha(LocalDateTime valor){fecha = valor;}
    
    @Lob
    @Basic(optional=false)
    @Column(name="imagen",nullable=false)
    private byte[] imagen;
    public byte[] getImagen(){return imagen;}
    public void setImagen(byte[] valor){imagen = valor;}
    
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
    public ProximoEvento(){
    }
    
    /** Metodos
     * @return  **/
    @Override
    public String toString(){return titulo;}
}
