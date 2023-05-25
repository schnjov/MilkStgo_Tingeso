package cl.usach.tingeso.sistemamilkstgo.Services;

import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.QuincenaEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.ValoresAcopioEntity;
import cl.usach.tingeso.sistemamilkstgo.Repositories.QuincenaRepository;
import org.aspectj.apache.bcel.generic.LOOKUPSWITCH;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class QuincenaService {
    @Autowired
    private ValoresAcopioService valoresAcopioService;
    @Autowired
    private QuincenaRepository quincenaRepository;

    @Autowired
    private AcopioService acopioService;

    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QuincenaService.class);

    //Verificar si hay quincenas registradas para el proveedor
    public boolean verificarQuincena(ProveedorEntity proveedor) {
        List<QuincenaEntity> quincena = quincenaRepository.findQuincenaEntityByProveedor(proveedor);
        if (quincena == null)
            return false;
        else
            return quincena.size() != 0;
    }

    //Obtener quincena anterior
    public QuincenaEntity getQuincenaAnterior(ProveedorEntity proveedor) {
        if (verificarQuincena(proveedor)) {
            return quincenaRepository.findTopByProveedorOrderByFechaDesc(proveedor);
        }
        return null;
    }

    public QuincenaEntity crearQuincenaActual(Date date, ProveedorEntity proveedor) {
        QuincenaEntity quincena = new QuincenaEntity();
        QuincenaEntity quincenaAnterior = getQuincenaAnterior(proveedor);
        int diaMedioMes = obtenerDiaMedioMes(date);
        int diaDelMes = obtenerDiaDelMes(date);
        if (diaDelMes <= diaMedioMes) {
            quincena.setQuincena(1);
        } else {
            quincena.setQuincena(2);
        }
        quincena.setProveedor(proveedor);
        quincena.setFecha(date);
        List<Integer> dias = new ArrayList<>();
        if (quincenaAnterior == null) {
            quincena.setKilos(acopioService.getAcopioTotal(null, proveedor));
            dias = acopioService.getAcopiosFromDateByProveedor(null, proveedor);
        } else {
            quincena.setKilos(acopioService.getAcopioTotal(quincenaAnterior.getFecha(), proveedor));
            dias = acopioService.getAcopiosFromDateByProveedor(quincenaAnterior.getFecha(), proveedor);
        }
        ValoresAcopioEntity valoresAcopio = valoresAcopioService.findByProveedor(proveedor);
        if (valoresAcopio == null) {
            return null;
        }
        quincena.setPorcentajeGrasa(valoresAcopio.getPorcentajeGrasa());
        quincena.setPorcentajeSolidos(valoresAcopio.getPorcentajeSolidos());
        quincena.setDiasDeAcopioMT(dias.get(0));
        quincena.setDiasDeAcopioM(dias.get(1));
        quincena.setDiasDeAcopioT(dias.get(2));
        save(quincena);
        return quincena;
    }

    private int obtenerDiaMedioMes(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int ultimoDiaMes = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return ultimoDiaMes / 2;
    }

    private int obtenerDiaDelMes(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public boolean save(QuincenaEntity quincena) {
        try {
            quincenaRepository.save(quincena);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error al guardar quincena: ", e);
            return false;
        }
    }

    //Obtiene la variación porcentual de kilos entre la quincena actual y la anterior
    public double obtenerVariacionKilos(ProveedorEntity proveedor) {
        List<QuincenaEntity> quincenas = quincenaRepository.findQuincenaEntityByProveedorOrderByFechaDesc(proveedor);
        if (quincenas.size() > 1) {
            QuincenaEntity quincenaActual = quincenas.get(0);
            QuincenaEntity quincenaAnterior = quincenas.get(1);
            if (quincenaAnterior.getKilos() == 0) {
                return 0;
            }
            return (double) (quincenaActual.getKilos() - quincenaAnterior.getKilos()) / quincenaAnterior.getKilos();
        }
        return 0;
    }

    //Obtiene la variación porcentual de grasa entre la quincena actual y la anterior
    public double obtenerVariacionGrasa(ProveedorEntity proveedor) {
        List<QuincenaEntity> quincenas = quincenaRepository.findQuincenaEntityByProveedorOrderByFechaDesc(proveedor);
        if (quincenas.size() > 1) {
            QuincenaEntity quincenaActual = quincenas.get(0);
            QuincenaEntity quincenaAnterior = quincenas.get(1);
            if (quincenaAnterior.getPorcentajeGrasa() == 0) {
                return 0;
            }
            return (double) (quincenaActual.getPorcentajeGrasa() - quincenaAnterior.getPorcentajeGrasa()) /
                    quincenaAnterior.getPorcentajeGrasa();
        }
        return 0;
    }

    //Obtiene la variación porcentual de sólidos entre la quincena actual y la anterior
    public double obtenerVariacionSolidos(ProveedorEntity proveedor) {
        List<QuincenaEntity> quincenas = quincenaRepository.findQuincenaEntityByProveedorOrderByFechaDesc(proveedor);
        if (quincenas.size() > 1) {
            QuincenaEntity quincenaActual = quincenas.get(0);
            QuincenaEntity quincenaAnterior = quincenas.get(1);
            if (quincenaAnterior.getPorcentajeSolidos() == 0) {
                return 0;
            }
            return (double) (quincenaActual.getPorcentajeSolidos() - quincenaAnterior.getPorcentajeSolidos()) /
                    quincenaAnterior.getPorcentajeSolidos();
        }
        return 0;
    }

    public QuincenaEntity getQuincena(ProveedorEntity proveedor) {
        return quincenaRepository.findTopByProveedorOrderByFechaDesc(proveedor);
    }
}