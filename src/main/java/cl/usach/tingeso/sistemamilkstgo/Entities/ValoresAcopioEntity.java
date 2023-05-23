package cl.usach.tingeso.sistemamilkstgo.Entities;

import jakarta.persistence.*;

@Entity
public class ValoresAcopioEntity {
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "codigo_proveedor")
    private ProveedorEntity proveedor;

    private int porcentajeGrasa;

    private int porcentajeSolidos;

    public ValoresAcopioEntity() {
    }

    public ValoresAcopioEntity(String id, ProveedorEntity proveedor, int porcentajeGrasa, int porcentajeSolidos) {
        this.id = id;
        this.proveedor = proveedor;
        this.porcentajeGrasa = porcentajeGrasa;
        this.porcentajeSolidos = porcentajeSolidos;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }

    public int getPorcentajeGrasa() {
        return porcentajeGrasa;
    }

    public void setPorcentajeGrasa(int porcentajeGrasa) {
        this.porcentajeGrasa = porcentajeGrasa;
    }

    public int getPorcentajeSolidos() {
        return porcentajeSolidos;
    }

    public void setPorcentajeSolidos(int porcentajeSolidos) {
        this.porcentajeSolidos = porcentajeSolidos;
    }
}
