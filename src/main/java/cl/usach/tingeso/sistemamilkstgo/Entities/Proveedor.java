package cl.usach.tingeso.sistemamilkstgo.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "proveedor")
public class Proveedor {
    @Id
    @Column(name = "proveedor_codigo", nullable = false)
    private Long proveedor_codigo;

    private String nombre;
    private String categoria;
    private Boolean afectoARetencion;

    public Long getCodigo() {
        return proveedor_codigo;
    }

    public void setCodigo(Long proveedor_codigo) {
        this.proveedor_codigo = proveedor_codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getAfectoARetencion() {
        return afectoARetencion;
    }

    public void setAfectoARetencion(Boolean afectoARetencion) {
        this.afectoARetencion = afectoARetencion;
    }
}