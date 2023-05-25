package cl.usach.tingeso.sistemamilkstgo.Services.Utils.Statics;import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class DataTest {

    private Data data;

    @BeforeEach
    public void setup() {
        data = new Data();
    }

    @Test
    public void testGetPagoByCategoria() {
        Double pagoA = data.getPagoByCategoria("A");
        Double pagoB = data.getPagoByCategoria("B");
        Double pagoC = data.getPagoByCategoria("C");
        Double pagoD = data.getPagoByCategoria("D");

        Assertions.assertEquals(700.0, pagoA);
        Assertions.assertEquals(550.0, pagoB);
        Assertions.assertEquals(400.0, pagoC);
        Assertions.assertEquals(250.0, pagoD);
    }

    @Test
    public void testGetPagoByGrasa() {
        Double pago1 = data.getPagoByGrasa(10.0);
        Double pago2 = data.getPagoByGrasa(30.0);
        Double pago3 = data.getPagoByGrasa(50.0);

        Assertions.assertEquals(30.0, pago1);
        Assertions.assertEquals(80.0, pago2);
        Assertions.assertEquals(120.0, pago3);
    }

    @Test
    public void testGetPagoBySolidos() {
        Double pago1 = data.getPagoBySolidos(5.0);
        Double pago2 = data.getPagoBySolidos(15.0);
        Double pago3 = data.getPagoBySolidos(25.0);
        Double pago4 = data.getPagoBySolidos(40.0);

        Assertions.assertEquals(-130.0, pago1);
        Assertions.assertEquals(-90.0, pago2);
        Assertions.assertEquals(95.0, pago3);
        Assertions.assertEquals(150.0, pago4);
    }

    @Test
    public void testGetBonificacionByFrecuencia() {
        Double bonificacion1 = data.getBonificacionByFrecuencia("MT");
        Double bonificacion2 = data.getBonificacionByFrecuencia("M");
        Double bonificacion3 = data.getBonificacionByFrecuencia("T");
        Double bonificacion4 = data.getBonificacionByFrecuencia("");

        Assertions.assertEquals(0.2, bonificacion1);
        Assertions.assertEquals(0.12, bonificacion2);
        Assertions.assertEquals(0.08, bonificacion3);
        Assertions.assertEquals(0.0, bonificacion4);
    }

    @Test
    public void testGetDescuentoByVariacionKls() {
        Double descuento1 = data.getDescuentoByVariacionKls(5.0);
        Double descuento2 = data.getDescuentoByVariacionKls(15.0);
        Double descuento3 = data.getDescuentoByVariacionKls(30.0);
        Double descuento4 = data.getDescuentoByVariacionKls(50.0);

        Assertions.assertEquals(0.0, descuento1);
        Assertions.assertEquals(7.0, descuento2);
        Assertions.assertEquals(15.0, descuento3);
        Assertions.assertEquals(30.0, descuento4);
    }

    @Test
    public void testGetDescuentoByVariacionGrasa() {
        Double descuento1 = data.getDescuentoByVariacionGrasa(10.0);
        Double descuento2 = data.getDescuentoByVariacionGrasa(20.0);
        Double descuento3 = data.getDescuentoByVariacionGrasa(35.0);
        Double descuento4 = data.getDescuentoByVariacionGrasa(50.0);

        Assertions.assertEquals(0.0, descuento1);
        Assertions.assertEquals(12.0, descuento2);
        Assertions.assertEquals(20.0, descuento3);
        Assertions.assertEquals(30.0, descuento4);
    }

    @Test
    public void testGetDescuentoByVariacionSt() {
        Double descuento1 = data.getDescuentoByVariacionSt(5.0);
        Double descuento2 = data.getDescuentoByVariacionSt(10.0);
        Double descuento3 = data.getDescuentoByVariacionSt(25.0);
        Double descuento4 = data.getDescuentoByVariacionSt(40.0);

        Assertions.assertEquals(0.0, descuento1);
        Assertions.assertEquals(18.0, descuento2);
        Assertions.assertEquals(27.0, descuento3);
        Assertions.assertEquals(45.0, descuento4);
    }

    @Test
    public void testGetPorcentajeRetencion() {
        Double porcentajeRetencion = data.getPorcentajeRetencion();

        Assertions.assertEquals(0.13, porcentajeRetencion);
    }
}
