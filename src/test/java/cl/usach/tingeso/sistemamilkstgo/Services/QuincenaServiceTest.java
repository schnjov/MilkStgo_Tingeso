package cl.usach.tingeso.sistemamilkstgo.Services;

import static org.junit.jupiter.api.Assertions.*;

import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.QuincenaEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.ValoresAcopioEntity;
import cl.usach.tingeso.sistemamilkstgo.Repositories.QuincenaRepository;
import cl.usach.tingeso.sistemamilkstgo.Services.AcopioService;
import cl.usach.tingeso.sistemamilkstgo.Services.ProveedorService;
import cl.usach.tingeso.sistemamilkstgo.Services.QuincenaService;
import cl.usach.tingeso.sistemamilkstgo.Services.ValoresAcopioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuincenaServiceTest {
    @Mock
    private ValoresAcopioService valoresAcopioService;
    @Mock
    private QuincenaRepository quincenaRepository;
    @Mock
    private AcopioService acopioService;

    @InjectMocks
    private QuincenaService quincenaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test para verificarQuincena cuando hay quincenas registradas
    @Test
    public void testVerificarQuincena_Registrada() {
        ProveedorEntity proveedor = new ProveedorEntity();
        List<QuincenaEntity> quincenas = new ArrayList<>();
        quincenas.add(new QuincenaEntity());
        when(quincenaRepository.findQuincenaEntityByProveedor(proveedor)).thenReturn(quincenas);

        boolean result = quincenaService.verificarQuincena(proveedor);

        assertTrue(result);
        verify(quincenaRepository, times(1)).findQuincenaEntityByProveedor(proveedor);
    }

    // Test para verificarQuincena cuando no hay quincenas registradas
    @Test
    public void testVerificarQuincena_NoRegistrada() {
        ProveedorEntity proveedor = new ProveedorEntity();
        when(quincenaRepository.findQuincenaEntityByProveedor(proveedor)).thenReturn(new ArrayList<>());

        boolean result = quincenaService.verificarQuincena(proveedor);

        assertFalse(result);
        verify(quincenaRepository, times(1)).findQuincenaEntityByProveedor(proveedor);
    }

    // Test para getQuincenaAnterior cuando hay quincenas registradas
    @Test
    public void testGetQuincenaAnterior_Registrada() {
        ProveedorEntity proveedor = new ProveedorEntity();
        List<QuincenaEntity> quincenas = new ArrayList<>();
        quincenas.add(new QuincenaEntity());
        when(quincenaRepository.findTopByProveedorOrderByFechaDesc(proveedor)).thenReturn(new QuincenaEntity());
        when(quincenaRepository.findQuincenaEntityByProveedor(proveedor)).thenReturn(quincenas);
        QuincenaEntity result = quincenaService.getQuincenaAnterior(proveedor);

        assertNotNull(result);
        verify(quincenaRepository, times(1)).findTopByProveedorOrderByFechaDesc(proveedor);
    }

    // Test para getQuincenaAnterior cuando no hay quincenas registradas
    @Test
    public void testGetQuincenaAnterior_NoRegistrada() {
        ProveedorEntity proveedor = new ProveedorEntity();
        when(quincenaRepository.findTopByProveedorOrderByFechaDesc(proveedor)).thenReturn(null);

        QuincenaEntity result = quincenaService.getQuincenaAnterior(proveedor);

        assertNull(result);
    }

    // Test para crearQuincenaActual cuando quincenaAnterior es null
    @Test
    public void testCrearQuincenaActual_QuincenaAnteriorNull() {
        Date date = new Date();
        ProveedorEntity proveedor = new ProveedorEntity();
        List<Integer> acopios = new ArrayList<>();
        acopios.add(1);
        acopios.add(2);
        acopios.add(3);
        when(quincenaService.getQuincenaAnterior(proveedor)).thenReturn(null);
        when(acopioService.getAcopioTotal(null, proveedor)).thenReturn(100);
        when(acopioService.getAcopiosFromDateByProveedor(null, proveedor)).thenReturn(acopios);
        when(valoresAcopioService.findByProveedor(proveedor)).thenReturn(new ValoresAcopioEntity());
        when(quincenaRepository.save(any(QuincenaEntity.class))).thenReturn(new QuincenaEntity());

        QuincenaEntity result = quincenaService.crearQuincenaActual(date, proveedor);

        assertNotNull(result);
        verify(acopioService, times(1)).getAcopioTotal(null, proveedor);
        verify(acopioService, times(1)).getAcopiosFromDateByProveedor(null, proveedor);
        verify(valoresAcopioService, times(1)).findByProveedor(proveedor);
        verify(quincenaRepository, times(1)).save(any(QuincenaEntity.class));
    }

    // Test para crearQuincenaActual cuando quincenaAnterior no es null
    @Test
    public void testCrearQuincenaActual_QuincenaAnteriorNotNull() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date date2 = calendar.getTime();
        ProveedorEntity proveedor = new ProveedorEntity();
        List<QuincenaEntity> quincenas = new ArrayList<>();
        quincenas.add(new QuincenaEntity(1,date2,proveedor, 200, 6, 3, 2, 24, 22));
        List<Integer> acopios = new ArrayList<>();
        acopios.add(1);
        acopios.add(2);
        acopios.add(3);
        when(quincenaRepository.findQuincenaEntityByProveedor(proveedor)).thenReturn(quincenas);
        when(acopioService.getAcopioTotal(any(Date.class), eq(proveedor))).thenReturn(100);
        when(acopioService.getAcopiosFromDateByProveedor( any(), any())).thenReturn(acopios);
        when(valoresAcopioService.findByProveedor(proveedor)).thenReturn(new ValoresAcopioEntity());
        when(quincenaRepository.save(any(QuincenaEntity.class))).thenReturn(new QuincenaEntity());

        QuincenaEntity result = quincenaService.crearQuincenaActual(date, proveedor);

        assertNotNull(result);
    }

    // Test para obtenerVariacionKilos cuando hay quincenas registradas
    @Test
    public void testObtenerVariacionKilos_Registradas() {
        ProveedorEntity proveedor = new ProveedorEntity();
        List<QuincenaEntity> quincenas = new ArrayList<>();
        QuincenaEntity quincenaActual = new QuincenaEntity();
        quincenaActual.setKilos(200);
        QuincenaEntity quincenaAnterior = new QuincenaEntity();
        quincenaAnterior.setKilos(100);
        quincenas.add(quincenaActual);
        quincenas.add(quincenaAnterior);
        when(quincenaRepository.findQuincenaEntityByProveedorOrderByFechaDesc(proveedor)).thenReturn(quincenas);

        double result = quincenaService.obtenerVariacionKilos(proveedor);

        assertEquals(1.0, result);
        verify(quincenaRepository, times(1)).findQuincenaEntityByProveedorOrderByFechaDesc(proveedor);
    }

    // Test para obtenerVariacionKilos cuando no hay quincenas registradas
    @Test
    public void testObtenerVariacionKilos_NoRegistradas() {
        ProveedorEntity proveedor = new ProveedorEntity();
        when(quincenaRepository.findQuincenaEntityByProveedorOrderByFechaDesc(proveedor)).thenReturn(new ArrayList<>());

        double result = quincenaService.obtenerVariacionKilos(proveedor);

        assertEquals(0.0, result);
        verify(quincenaRepository, times(1)).findQuincenaEntityByProveedorOrderByFechaDesc(proveedor);
    }

    // Test para obtenerVariacionGrasa cuando hay quincenas registradas
    @Test
    public void testObtenerVariacionGrasa_Registradas() {
        ProveedorEntity proveedor = new ProveedorEntity();
        List<QuincenaEntity> quincenas = new ArrayList<>();
        QuincenaEntity quincenaActual = new QuincenaEntity();
        quincenaActual.setPorcentajeGrasa(20);
        QuincenaEntity quincenaAnterior = new QuincenaEntity();
        quincenaAnterior.setPorcentajeGrasa(10);
        quincenas.add(quincenaActual);
        quincenas.add(quincenaAnterior);
        when(quincenaRepository.findQuincenaEntityByProveedorOrderByFechaDesc(proveedor)).thenReturn(quincenas);

        double result = quincenaService.obtenerVariacionGrasa(proveedor);

        assertEquals(1.0, result);
        verify(quincenaRepository, times(1)).findQuincenaEntityByProveedorOrderByFechaDesc(proveedor);
    }

    // Test para obtenerVariacionGrasa cuando no hay quincenas registradas
    @Test
    public void testObtenerVariacionGrasa_NoRegistradas() {
        ProveedorEntity proveedor = new ProveedorEntity();
        when(quincenaRepository.findQuincenaEntityByProveedorOrderByFechaDesc(proveedor)).thenReturn(new ArrayList<>());

        double result = quincenaService.obtenerVariacionGrasa(proveedor);

        assertEquals(0.0, result);
        verify(quincenaRepository, times(1)).findQuincenaEntityByProveedorOrderByFechaDesc(proveedor);
    }

}
