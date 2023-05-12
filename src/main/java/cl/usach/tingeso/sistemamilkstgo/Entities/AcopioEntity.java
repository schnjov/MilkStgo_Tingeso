package cl.usach.tingeso.sistemamilkstgo.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "acopio")
public class AcopioEntity {
    @Id
    private String id;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date fecha;
    private Integer turno;
    @ManyToOne
    @JoinColumn(name = "proveedor_codigo")
    private ProveedorEntity proveedor;
    private Integer kilos;

    public AcopioEntity(String id, Date fecha, Integer turno, ProveedorEntity proveedor, Integer kilos) {
        this.id = id;
        this.fecha = fecha;
        this.turno = turno;
        this.proveedor = proveedor;
        this.kilos = kilos;
    }

    public AcopioEntity() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
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

    @Override
    public String toString() {
        return "AcopioEntity{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", turno=" + turno +
                ", proveedor=" + proveedor.toString() +
                ", kilos=" + kilos +
                '}';
    }
}
