package com.tiamex.siicomeii.persistencia.entidad;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/** @author cerimice **/

@Entity
@Table(name="agremiado")
@NamedQueries({
    @NamedQuery(name="Agremiado.getByEmail",query="SELECT t FROM Agremiado t WHERE t.correo IN (:correo)")
})
public class Agremiado implements Serializable{
    @Id
    @Basic(optional=false)
    @Column(name="id",nullable=false)
    private long id;
    public long getId(){return id;}
    public void setId(long valor){id = valor;}
    
    @Basic(optional=false)
    @Column(name="gradoEstudio",nullable=false)
    private long gradoEstudio;
    public long getGradoEstudios(){return gradoEstudio;}
    public void setGradoEstudios(long valor){gradoEstudio = valor;}
    
    @Basic(optional=false)
    @Column(name="institucion",nullable=false)
    private String institucion;
    public String getInstitucion(){return institucion;}
    public void setInstitucion(String valor){institucion = valor;}
    
    @Basic(optional=false)
    @Column(name="nombre",nullable=false)
    private String  nombre;
    public String getNombre(){return nombre;}
    public void setNombre(String valor){nombre = valor;}
    
    @Basic(optional=false)
    @Column(name="correo",nullable=false,unique=true)
    private String  correo;
    public String getCorreo(){return correo;}
    public void setCorreo(String valor){correo = valor;}
    
    @Basic(optional=false)
    @Column(name="pais",nullable=false)
    private long pais;
    public long getPais(){return pais;}
    public void setPais(long valor){pais = valor;}
    
    @Basic(optional=false)
    @Column(name="sexo",nullable=false)
    private char sexo;
    public char getSexo(){return sexo;}
    public void setSexo(char valor){sexo = valor;}
   
    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.DATE)
    private Calendar createdAt;
    public java.util.Date getCreatedAt(){
        return createdAt.getTime();
    }
    
    @PrePersist
    private void creationTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
        this.createdAt = cal;
    }
    public LocalDate getFechaReg(){
        LocalDate localDate = LocalDateTime.ofInstant(createdAt.toInstant(), createdAt.getTimeZone().toZoneId()).toLocalDate();
        return localDate;
    }
    
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updated_at;
    public java.util.Date getTimestamp() {
        return updated_at.getTime();
    }
    
    @Basic(optional=false)
    @Column(name="urlIcon")
    private boolean urlIcon=false;
    public boolean getUrlIcon(){
        return urlIcon;
    }

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
    //return gradoEstudio + institucion + nombre + pais + sexo;
    public String toString(){return institucion;}
    
}
