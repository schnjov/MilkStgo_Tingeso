package cl.usach.tingeso.sistemamilkstgo.Services;

import cl.usach.tingeso.sistemamilkstgo.Entities.AcopioEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import cl.usach.tingeso.sistemamilkstgo.Repositories.AcopioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AcopioServiceTest {

    @Mock
    private AcopioRepository acopioRepository;

    @InjectMocks
    private AcopioService acopioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        // Arrange
        List<AcopioEntity> acopioList = new ArrayList<>();
        acopioList.add(new AcopioEntity());
        when(acopioRepository.findAll()).thenReturn(acopioList);

        // Act
        List<AcopioEntity> result = acopioService.findAll();

        // Assert
        assertEquals(1, result.size());
        verify(acopioRepository, times(1)).findAll();
    }

    @Test
    void findByProveedor_WithDateNull() {
        // Arrange
        Date date = null;
        ProveedorEntity proveedor = new ProveedorEntity();
        List<AcopioEntity> acopioList = new ArrayList<>();
        acopioList.add(new AcopioEntity());
        when(acopioRepository.findByProveedor(proveedor)).thenReturn(acopioList);

        // Act
        List<AcopioEntity> result = acopioService.findByProveedor(date, proveedor);

        // Assert
        assertEquals(1, result.size());
        verify(acopioRepository, times(1)).findByProveedor(proveedor);
        verify(acopioRepository, never()).findByProveedorAndFechaGreaterThanEqual(proveedor, date);
    }

    @Test
    void findByProveedor_WithDateNotNull() {
        // Arrange
        Date date = new Date();
        ProveedorEntity proveedor = new ProveedorEntity();
        List<AcopioEntity> acopioList = new ArrayList<>();
        acopioList.add(new AcopioEntity());
        when(acopioRepository.findByProveedorAndFechaGreaterThanEqual(proveedor, date)).thenReturn(acopioList);

        // Act
        List<AcopioEntity> result = acopioService.findByProveedor(date, proveedor);

        // Assert
        assertEquals(1, result.size());
        verify(acopioRepository, never()).findByProveedor(proveedor);
        verify(acopioRepository, times(1)).findByProveedorAndFechaGreaterThanEqual(proveedor, date);
    }

    @Test
    void getAcopioTotal() {
        // Arrange
        Date date = new Date();
        ProveedorEntity proveedor = new ProveedorEntity();
        List<AcopioEntity> acopioList = new ArrayList<>();
        acopioList.add(new AcopioEntity("1", date, 1, proveedor, 10)); // 10 kilos
        acopioList.add(new AcopioEntity("2", date, 1, proveedor, 15)); // 15 kilos
        when(acopioRepository.findByProveedorAndFechaGreaterThanEqual(proveedor,date)).thenReturn(acopioList);
        // Act
        Integer result = acopioService.getAcopioTotal(date, proveedor);

        // Assert
        assertEquals(25, result);
    }

    @Test
    void getAcopiosFromDateByProveedor() {
        // Arrange
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        ProveedorEntity proveedor = new ProveedorEntity();
        List<AcopioEntity> acopioList = new ArrayList<>();
        acopioList.add(new AcopioEntity("1", date, 1, proveedor, 10)); // M turno
        acopioList.add(new AcopioEntity("2", date, 2, proveedor, 15)); // T turno
        calendar.add(Calendar.DATE, 1); // Increment day by 1
        Date date2 = calendar.getTime(); // Update the date object
        acopioList.add(new AcopioEntity("3", date2, 1, proveedor, 20)); // M turno
        when(acopioRepository.findByProveedorAndFechaGreaterThanEqual(proveedor, date)).thenReturn(acopioList);
        when(acopioRepository.findByFecha(date)).thenReturn(acopioList.subList(0,2));
        when(acopioRepository.findByFecha(date2)).thenReturn(acopioList.subList(2,3));

        // Act
        List<Integer> result = acopioService.getAcopiosFromDateByProveedor(date, proveedor);

        // Assert
        assertEquals(1, result.get(0)); // Total days
        assertEquals(1, result.get(1)); // 1st shift count
        assertEquals(0, result.get(2)); // 2nd shift count
        verify(acopioRepository, times(1)).findByProveedorAndFechaGreaterThanEqual(proveedor, date);
        verify(acopioRepository, times(1)).findByFecha(date);
    }

}
