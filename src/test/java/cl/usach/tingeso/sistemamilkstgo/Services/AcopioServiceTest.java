package cl.usach.tingeso.sistemamilkstgo.Services;

import cl.usach.tingeso.sistemamilkstgo.Entities.AcopioEntity;
import cl.usach.tingeso.sistemamilkstgo.Entities.ProveedorEntity;
import cl.usach.tingeso.sistemamilkstgo.Repositories.AcopioRepository;
import cl.usach.tingeso.sistemamilkstgo.Services.AcopioService;
import cl.usach.tingeso.sistemamilkstgo.Services.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AcopioServiceTest {
    @Mock
    private AcopioRepository acopioRepository;
    @Mock
    private ProveedorService proveedorService;

    @InjectMocks
    private AcopioService acopioService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAcopiosFromDateByProveedor() {
        Date date = new Date(2023,Calendar.APRIL,30);
        List<AcopioEntity> acopios = new ArrayList<>();
        ProveedorEntity proveedor = new ProveedorEntity("00001", "culun", "A", true);
        acopios.add(new AcopioEntity("1", new Date(2023, Calendar.JUNE, 1), 1, proveedor, 1000));
        acopios.add(new AcopioEntity("2", new Date(2023, Calendar.JUNE, 1), 2, proveedor, 800));
        acopios.add(new AcopioEntity("3", new Date(2023, Calendar.JUNE, 2), 1, proveedor, 1200));
        acopios.add(new AcopioEntity("4", new Date(2023, Calendar.JUNE, 2), 2, proveedor, 1500));
        acopios.add(new AcopioEntity("5", new Date(2023, Calendar.JUNE, 3), 1, proveedor, 700));
        acopios.add(new AcopioEntity("6", new Date(2023, Calendar.JUNE, 3), 2, proveedor, 900));
        Logger.getLogger("test").info(acopios.get(0).getFecha().toString());
        when(acopioRepository.findByProveedorAndFechaGreaterThanEqual(proveedor,date)).thenReturn(acopios);
        when(acopioRepository.findByFecha(acopios.get(0).getFecha())).thenReturn(acopios.subList(0, 2));
        when(acopioRepository.findByFecha(acopios.get(2).getFecha())).thenReturn(acopios.subList(2, 4));
        when(acopioRepository.findByFecha(acopios.get(4).getFecha())).thenReturn(acopios.subList(4, 6));

        List<Integer> result = acopioService.getAcopiosFromDateByProveedor(date, proveedor);

        assertEquals(3, result.get(0));
        assertEquals(0, result.get(1));
        assertEquals(0, result.get(2));
    }
}
