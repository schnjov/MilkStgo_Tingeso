package cl.usach.tingeso.sistemamilkstgo.Services;

import cl.usach.tingeso.sistemamilkstgo.Entities.PlanillaPagosEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.QuincenaEntity;
import cl.usach.tingeso.sistemamilkstgo.Repositories.PlanillaPagosRepository;
import cl.usach.tingeso.sistemamilkstgo.Services.Utils.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class PlanillaPagosServiceTest {
    @Mock
    private PlanillaPagosRepository planillaPagosRepository;

    @Mock
    private Calculator calculator;

    @Mock
    private QuincenaService quincenaService;

    @Mock
    private ProveedorService proveedorService;

    @InjectMocks
    private PlanillaPagosService planillaPagosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearPlanilla() {
        // Simular datos de prueba
        QuincenaEntity quincena = new QuincenaEntity();
        quincena.setDiasDeAcopioM(10);
        quincena.setDiasDeAcopioT(8);
        quincena.setDiasDeAcopioMT(5);
        quincena.setKilos(200);
        quincena.setPorcentajeGrasa(14);
        quincena.setPorcentajeSolidos(12);
        ProveedorEntity proveedor = new ProveedorEntity("12443", "Culun", "A", true);
        quincena.setProveedor(proveedor);

        // Simular el comportamiento de los métodos en las dependencias
        when(quincenaService.obtenerVariacionGrasa(proveedor)).thenReturn(1.2);
        when(quincenaService.obtenerVariacionKilos(proveedor)).thenReturn(0.8);
        when(quincenaService.obtenerVariacionSolidos(proveedor)).thenReturn(0.5);
        when(calculator.calcularPagoPorKilos(proveedor.getCategoria(), quincena.getKilos())).thenReturn(500);
        when(calculator.calcularPagoPorGrasa(quincena.getPorcentajeGrasa(), quincena.getKilos())).thenReturn(100);
        when(calculator.calcularPagoPorSt(quincena.getPorcentajeSolidos(), quincena.getKilos())).thenReturn(200);
        when(calculator.calcularBonificacionPorFrecuencia(quincena.getDiasDeAcopioMT(),
                quincena.getDiasDeAcopioM(), quincena.getDiasDeAcopioT())).thenReturn(50.0);
        when(calculator.calcularDescuentoByGrasa(quincena.getPorcentajeGrasa())).thenReturn(20);
        when(calculator.calcularDescuentoByKls(quincena.getKilos())).thenReturn(30);
        when(calculator.calcularDescuentoBySt(quincena.getPorcentajeSolidos())).thenReturn(10);
        when(calculator.calcularPagoTotal(anyInt(), anyInt(), anyInt(), anyDouble(),
                anyInt(), anyInt(), anyInt())).thenReturn(800);
        when(calculator.calcularRetencion(800, proveedor.getAfectoARetencion())).thenReturn(80.0);
        when(calculator.calcularMontoFinal(800, 80.0)).thenReturn(720.0);

        // Llamar al método a testear
        PlanillaPagosEntity planillaPagos = planillaPagosService.crearPlanilla(quincena);
        assertNotNull(planillaPagos);
        // Verificar los resultados esperados
        assertEquals(quincena, planillaPagos.getQuincena());
        assertEquals(23, planillaPagos.getDiasDeAcopioTotal());
        assertEquals(8.695652173913043, planillaPagos.getPromedioDiarioKilos());
        assertEquals(1.2, planillaPagos.getPorcentajeVariacionGrasa());
        assertEquals(0.8, planillaPagos.getPorcentajeVariacionLeche());
        assertEquals(0.5, planillaPagos.getPorcentajeVariacionSolidos());
        assertEquals(500, planillaPagos.getPagoPorLeche());
        assertEquals(100, planillaPagos.getPagoPorGrasa());
        assertEquals(200, planillaPagos.getPagoPorSolidos());
        assertEquals(800, planillaPagos.getPagoTotal());
        assertEquals(50, planillaPagos.getBonificacionPorFrecuencia());
        assertEquals(20, planillaPagos.getDescuentoPorVariacionGrasa());
        assertEquals(30, planillaPagos.getDescuentoPorVariacionLeche());
        assertEquals(10, planillaPagos.getDescuentoPorVariacionSolidos());
        assertEquals(80.0, planillaPagos.getMontoRetencion());
        assertEquals(720.0, planillaPagos.getMontoFinal());

        // Verificar que los métodos en las dependencias hayan sido llamados
        verify(quincenaService).obtenerVariacionGrasa(proveedor);
        verify(quincenaService).obtenerVariacionKilos(proveedor);
        verify(quincenaService).obtenerVariacionSolidos(proveedor);
        verify(calculator).calcularPagoPorKilos(proveedor.getCategoria(), quincena.getKilos());
        verify(calculator).calcularPagoPorGrasa(quincena.getPorcentajeGrasa(), quincena.getKilos());
        verify(calculator).calcularPagoPorSt(quincena.getPorcentajeSolidos(), quincena.getKilos());
        verify(calculator).calcularBonificacionPorFrecuencia(quincena.getDiasDeAcopioMT(),
                quincena.getDiasDeAcopioM(), quincena.getDiasDeAcopioT());
        verify(calculator).calcularDescuentoByGrasa(quincena.getPorcentajeGrasa());
        verify(calculator).calcularDescuentoByKls(quincena.getKilos());
        verify(calculator).calcularDescuentoBySt(quincena.getPorcentajeSolidos());
        verify(calculator).calcularPagoTotal(anyInt(), anyInt(), anyInt(), anyDouble(),
                anyInt(), anyInt(), anyInt());
        verify(calculator).calcularRetencion(800, proveedor.getAfectoARetencion());
        verify(calculator).calcularMontoFinal(800, 80.0);
        verifyNoMoreInteractions(quincenaService, calculator);
    }

    @Test
    void testCalcularPagos_QuincenaNull() {
        // Simular datos de prueba
        List<ProveedorEntity> proveedores = new ArrayList<>();
        proveedores.add(new ProveedorEntity());
        Date fechaHoy = new Date();
        when(proveedorService.findAll()).thenReturn(proveedores);
        when(quincenaService.crearQuincenaActual(fechaHoy, proveedores.get(0))).thenReturn(null);

        // Llamar al método a testear
        planillaPagosService.calcularPagos();

        // Verificar que los métodos en las dependencias hayan sido llamados
        verify(proveedorService).findAll();
        verifyNoMoreInteractions(proveedorService, planillaPagosRepository);
        // Asegurar que no se haya llamado al método save() del repositorio
        verifyNoInteractions(planillaPagosRepository);

    }
}