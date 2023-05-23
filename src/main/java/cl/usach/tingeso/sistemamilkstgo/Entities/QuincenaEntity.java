package cl.usach.tingeso.sistemamilkstgo.Entities;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class QuincenaEntity {
    @Id()
    @Column(name = "quincena_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer quincena;

    //Formato de fecha: MM/yyyy
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "proveedor_codigo")
    private ProveedorEntity proveedor;

    private Integer kilos;

    private Integer diasDeAcopioMT;
    private Integer diasDeAcopioM;
    private Integer diasDeAcopioT;

    private Integer porcentajeGrasa;
    private Integer porcentajeSolidos;

    public QuincenaEntity() {
    }

    public QuincenaEntity(Integer quincena, Date fecha, ProveedorEntity proveedor, Integer kilos, Integer diasDeAcopioMT
            , Integer diasDeAcopioM, Integer diasDeAcopioT, Integer porcentajeGrasa, Integer porcentajeSolidos) {
        this.quincena = quincena;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.kilos = kilos;
        this.diasDeAcopioMT = diasDeAcopioMT;
        this.diasDeAcopioM = diasDeAcopioM;
        this.diasDeAcopioT = diasDeAcopioT;
        this.porcentajeGrasa = porcentajeGrasa;
        this.porcentajeSolidos = porcentajeSolidos;
    }

    public Integer getDiasDeAcopioMT() {
        return diasDeAcopioMT;
    }

    public void setDiasDeAcopioMT(Integer diasDeAcopioMT) {
        this.diasDeAcopioMT = diasDeAcopioMT;
    }

    public Integer getDiasDeAcopioM() {
        return diasDeAcopioM;
    }

    public void setDiasDeAcopioM(Integer diasDeAcopioM) {
        this.diasDeAcopioM = diasDeAcopioM;
    }

    public Integer getDiasDeAcopioT() {
        return diasDeAcopioT;
    }

    public void setDiasDeAcopioT(Integer diasDeAcopioT) {
        this.diasDeAcopioT = diasDeAcopioT;
    }

    public Integer getQuincena() {
        return quincena;
    }

    public void setQuincena(Integer quincena) {
        this.quincena = quincena;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getKilos() {
        return kilos;
    }

    public void setKilos(Integer kilos) {
        this.kilos = kilos;
    }

    public Integer getPorcentajeGrasa() {
        return porcentajeGrasa;
    }

    public void setPorcentajeGrasa(Integer porcentajeGrasa) {
        this.porcentajeGrasa = porcentajeGrasa;
    }

    public Integer getPorcentajeSolidos() {
        return porcentajeSolidos;
    }

    public void setPorcentajeSolidos(Integer porcentajeSolidos) {
        this.porcentajeSolidos = porcentajeSolidos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "QuincenaEntity{" +
                "id=" + id +
                ", quincena=" + quincena +
                ", fecha=" + fecha +
                ", proveedor=" + proveedor +
                ", kilos=" + kilos +
                ", diasDeAcopioMT=" + diasDeAcopioMT +
                ", diasDeAcopioM=" + diasDeAcopioM +
                ", diasDeAcopioT=" + diasDeAcopioT +
                ", porcentajeGrasa=" + porcentajeGrasa +
                ", porcentajeSolidos=" + porcentajeSolidos +
                '}';
    }
}
