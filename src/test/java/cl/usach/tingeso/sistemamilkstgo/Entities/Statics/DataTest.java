package cl.usach.tingeso.sistemamilkstgo.Entities.Statics;

import cl.usach.tingeso.sistemamilkstgo.Utils.Statics.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataTest {
    private Data data;

    @BeforeEach
    void setup() {
        data = new Data();
    }

    @Test
    void testGetPagoByCategoria() {
        Assertions.assertEquals(700.0, data.getPagoByCategoria("A"));
        Assertions.assertEquals(550.0, data.getPagoByCategoria("B"));
        Assertions.assertEquals(400.0, data.getPagoByCategoria("C"));
        Assertions.assertEquals(250.0, data.getPagoByCategoria("D"));
    }

    @Test
    void testGetPagoByGrasa() {
        Assertions.assertEquals(30.0, data.getPagoByGrasa(10.0));
        Assertions.assertEquals(80.0, data.getPagoByGrasa(30.0));
        Assertions.assertEquals(120.0, data.getPagoByGrasa(50.0));
    }

    @Test
    void testGetPagoBySolidos() {
        Assertions.assertEquals(-130.0, data.getPagoBySolidos(5.0));
        Assertions.assertEquals(-90.0, data.getPagoBySolidos(10.0));
        Assertions.assertEquals(95.0, data.getPagoBySolidos(25.0));
        Assertions.assertEquals(150.0, data.getPagoBySolidos(40.0));
    }

    @Test
    void testGetBonificacionByFrecuencia() {
        Assertions.assertEquals(0.2, data.getBonificacionByFrecuencia("Mañana y Tarde"));
        Assertions.assertEquals(0.12, data.getBonificacionByFrecuencia("Mañana"));
        Assertions.assertEquals(0.08, data.getBonificacionByFrecuencia("Tarde"));
    }

    @Test
    void testGetDescuentoByVariacionKls() {
        Assertions.assertEquals(0.0, data.getDescuentoByVariacionKls(5.0));
        Assertions.assertEquals(7.0, data.getDescuentoByVariacionKls(15.0));
        Assertions.assertEquals(15.0, data.getDescuentoByVariacionKls(35.0));
        Assertions.assertEquals(30.0, data.getDescuentoByVariacionKls(50.0));
    }

    @Test
    void testGetDescuentoByVariacionGrasa() {
        Assertions.assertEquals(0.0, data.getDescuentoByVariacionGrasa(10.0));
        Assertions.assertEquals(12.0, data.getDescuentoByVariacionGrasa(20.0));
        Assertions.assertEquals(20.0, data.getDescuentoByVariacionGrasa(35.0));
        Assertions.assertEquals(30.0, data.getDescuentoByVariacionGrasa(50.0));
    }

    @Test
    void testGetDescuentoByVariacionSt() {
        Assertions.assertEquals(0.0, data.getDescuentoByVariacionSt(5.0));
        Assertions.assertEquals(18.0, data.getDescuentoByVariacionSt(10.0));
        Assertions.assertEquals(27.0, data.getDescuentoByVariacionSt(20.0));
        Assertions.assertEquals(45.0, data.getDescuentoByVariacionSt(50.0));
    }
}