package cl.usach.tingeso.sistemamilkstgo.Entities;

import jakarta.persistence.*;

@Entity
public class PlanillaPagosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "quincena_id")
    private QuincenaEntity quincena;

    private Integer diasDeAcopioTotal;

    private Double promedioDiarioKilos;

    private Double porcentajeVariacionLeche;

    private Double porcentajeVariacionGrasa;

    private Double porcentajeVariacionSolidos;

    private Integer pagoPorLeche;

    private Integer pagoPorGrasa;

    private Integer pagoPorSolidos;

    private Integer pagoTotal;

    private Double bonificacionPorFrecuencia;

    private Integer descuentoPorVariacionLeche;

    private Integer descuentoPorVariacionGrasa;

    private Integer descuentoPorVariacionSolidos;

    private Double montoRetencion;

    private Double montoFinal;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuincenaEntity getQuincena() {
        return quincena;
    }

    public void setQuincena(QuincenaEntity quincena) {
        this.quincena = quincena;
    }

    public Integer getDiasDeAcopioTotal() {
        return diasDeAcopioTotal;
    }

    public void setDiasDeAcopioTotal(Integer diasDeAcopioTotal) {
        this.diasDeAcopioTotal = diasDeAcopioTotal;
    }

    public Double getPromedioDiarioKilos() {
        return promedioDiarioKilos;
    }

    public void setPromedioDiarioKilos(Double promedioDiarioKilos) {
        this.promedioDiarioKilos = promedioDiarioKilos;
    }

    public Double getPorcentajeVariacionLeche() {
        return porcentajeVariacionLeche;
    }

    public void setPorcentajeVariacionLeche(Double porcentajeVariacionLeche) {
        this.porcentajeVariacionLeche = porcentajeVariacionLeche;
    }

    public Double getPorcentajeVariacionGrasa() {
        return porcentajeVariacionGrasa;
    }

    public void setPorcentajeVariacionGrasa(Double porcentajeVariacionGrasa) {
        this.porcentajeVariacionGrasa = porcentajeVariacionGrasa;
    }

    public Double getPorcentajeVariacionSolidos() {
        return porcentajeVariacionSolidos;
    }

    public void setPorcentajeVariacionSolidos(Double porcentajeVariacionSolidos) {
        this.porcentajeVariacionSolidos = porcentajeVariacionSolidos;
    }

    public Integer getPagoPorLeche() {
        return pagoPorLeche;
    }

    public void setPagoPorLeche(Integer pagoPorLeche) {
        this.pagoPorLeche = pagoPorLeche;
    }

    public Integer getPagoPorGrasa() {
        return pagoPorGrasa;
    }

    public void setPagoPorGrasa(Integer pagoPorGrasa) {
        this.pagoPorGrasa = pagoPorGrasa;
    }

    public Integer getPagoPorSolidos() {
        return pagoPorSolidos;
    }

    public void setPagoPorSolidos(Integer pagoPorSolidos) {
        this.pagoPorSolidos = pagoPorSolidos;
    }

    public Integer getPagoTotal() {
        return pagoTotal;
    }

    public void setPagoTotal(Integer pagoTotal) {
        this.pagoTotal = pagoTotal;
    }

    public Double getBonificacionPorFrecuencia() {
        return bonificacionPorFrecuencia;
    }

    public void setBonificacionPorFrecuencia(Double bonificacionPorFrecuencia) {
        this.bonificacionPorFrecuencia = bonificacionPorFrecuencia;
    }

    public Integer getDescuentoPorVariacionLeche() {
        return descuentoPorVariacionLeche;
    }

    public void setDescuentoPorVariacionLeche(Integer descuentoPorVariacionLeche) {
        this.descuentoPorVariacionLeche = descuentoPorVariacionLeche;
    }

    public Integer getDescuentoPorVariacionGrasa() {
        return descuentoPorVariacionGrasa;
    }

    public void setDescuentoPorVariacionGrasa(Integer descuentoPorVariacionGrasa) {
        this.descuentoPorVariacionGrasa = descuentoPorVariacionGrasa;
    }

    public Integer getDescuentoPorVariacionSolidos() {
        return descuentoPorVariacionSolidos;
    }

    public void setDescuentoPorVariacionSolidos(Integer descuentoPorVariacionSolidos) {
        this.descuentoPorVariacionSolidos = descuentoPorVariacionSolidos;
    }

    public Double getMontoRetencion() {
        return montoRetencion;
    }

    public void setMontoRetencion(Double montoRetencion) {
        this.montoRetencion = montoRetencion;
    }

    public Double getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(Double montoFinal) {
        this.montoFinal = montoFinal;
    }
}
