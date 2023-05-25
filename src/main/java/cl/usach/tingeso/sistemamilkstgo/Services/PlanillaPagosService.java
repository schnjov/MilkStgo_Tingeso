package cl.usach.tingeso.sistemamilkstgo.Services;

import cl.usach.tingeso.sistemamilkstgo.Entities.PlanillaPagosEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.QuincenaEntity;
import cl.usach.tingeso.sistemamilkstgo.Services.Utils.Calculator;
import cl.usach.tingeso.sistemamilkstgo.Repositories.PlanillaPagosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PlanillaPagosService {
    @Autowired
    private PlanillaPagosRepository planillaPagosRepository;

    @Autowired
    private Calculator calculator;

    @Autowired
    private QuincenaService quincenaService;
    @Autowired
    private ProveedorService proveedorService;

    private static final Logger LOGGER = Logger.getLogger(PlanillaPagosService.class.getName());

    public PlanillaPagosEntity crearPlanilla(QuincenaEntity quincena) {
        PlanillaPagosEntity planillaPagos = new PlanillaPagosEntity();
        planillaPagos.setQuincena(quincena);
        planillaPagos.setDiasDeAcopioTotal(quincena.getDiasDeAcopioM()+quincena.getDiasDeAcopioT()+
                quincena.getDiasDeAcopioMT());
        if(planillaPagos.getDiasDeAcopioTotal() == 0){
            planillaPagos.setPromedioDiarioKilos(0.0);
        }else
            planillaPagos.setPromedioDiarioKilos((double) quincena.getKilos() / planillaPagos.getDiasDeAcopioTotal());
        planillaPagos.setPorcentajeVariacionGrasa(quincenaService.obtenerVariacionGrasa(quincena.getProveedor()));
        planillaPagos.setPorcentajeVariacionLeche(quincenaService.obtenerVariacionKilos(quincena.getProveedor()));
        planillaPagos.setPorcentajeVariacionSolidos(quincenaService.obtenerVariacionSolidos(quincena.getProveedor()));
        planillaPagos.setPagoPorLeche(calculator.calcularPagoPorKilos(quincena.getProveedor().getCategoria(),
                quincena.getKilos()));
        planillaPagos.setPagoPorGrasa(calculator.calcularPagoPorGrasa(quincena.getPorcentajeGrasa(),
                quincena.getKilos()));
        planillaPagos.setPagoPorSolidos(calculator.calcularPagoPorSt(quincena.getPorcentajeSolidos(),
                quincena.getKilos()));
        planillaPagos.setBonificacionPorFrecuencia(calculator.calcularBonificacionPorFrecuencia(quincena.getDiasDeAcopioMT()
                , quincena.getDiasDeAcopioM(), quincena.getDiasDeAcopioT()));
        planillaPagos.setDescuentoPorVariacionGrasa(calculator.calcularDescuentoByGrasa(quincena.getPorcentajeGrasa()));
        planillaPagos.setDescuentoPorVariacionLeche(calculator.calcularDescuentoByKls(quincena.getKilos()));
        planillaPagos.setDescuentoPorVariacionSolidos(calculator.calcularDescuentoBySt(quincena.getPorcentajeSolidos()));
        planillaPagos.setPagoTotal(calculator.calcularPagoTotal(planillaPagos.getPagoPorLeche(),
                planillaPagos.getPagoPorGrasa(),planillaPagos.getPagoPorSolidos(),planillaPagos.getBonificacionPorFrecuencia(),
                planillaPagos.getDescuentoPorVariacionGrasa(),planillaPagos.getDescuentoPorVariacionLeche(),
                planillaPagos.getDescuentoPorVariacionSolidos()));
        planillaPagos.setMontoRetencion(calculator.calcularRetencion(planillaPagos.getPagoTotal(),
                quincena.getProveedor().getAfectoARetencion()));
        planillaPagos.setMontoFinal(calculator.calcularMontoFinal(planillaPagos.getPagoTotal(),
                planillaPagos.getMontoRetencion()));
        return planillaPagos;
    }

    public void calcularPagos(){
        List< ProveedorEntity > proveedores = proveedorService.findAll();
        Date fechaHoy = new Date();
        for (ProveedorEntity proveedor: proveedores) {
            QuincenaEntity quincena = quincenaService.crearQuincenaActual(fechaHoy, proveedor);
            if (quincena == null) {
                continue;
            }
            PlanillaPagosEntity planillaPagos = crearPlanilla(quincena);
            planillaPagosRepository.save(planillaPagos);
        }
    }

    public PlanillaPagosEntity findByIdProveedor(String idProveedor) {
        ProveedorEntity proveedor = proveedorService.getProveedorByCodigo(idProveedor);
        QuincenaEntity quincena = quincenaService.getQuincena(proveedor);
        return planillaPagosRepository.getTopByQuincena(quincena);
    }
}
