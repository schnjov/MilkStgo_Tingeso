package cl.usach.tingeso.sistemamilkstgo.Services.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @Test
    public void testCalcularPagoPorKilos() {
        Integer pago = calculator.calcularPagoPorKilos("A", 10);
        Assertions.assertEquals(7000, pago);
    }

    @Test
    public void testCalcularPagoPorGrasa() {
        Integer pago = calculator.calcularPagoPorGrasa(20, 10);
        Assertions.assertEquals(300, pago);
    }

    @Test
    public void testCalcularPagoPorSt() {
        Integer pago = calculator.calcularPagoPorSt(30, 10);
        Assertions.assertEquals(950, pago);
    }

    @Test
    public void testCalcularPagos() {
        Integer pagos = calculator.calcularPagos("A", 10, 20, 30);
        Assertions.assertEquals(8250, pagos);
    }

    @Test
    public void testCalcularDescuentoByGrasa() {
        Integer descuento = calculator.calcularDescuentoByGrasa(20);
        Assertions.assertEquals(1200, descuento);
    }

    @Test
    public void testCalcularDescuentoBySt() {
        Integer descuento = calculator.calcularDescuentoBySt(30);
        Assertions.assertEquals(2700, descuento);
    }

    @Test
    public void testCalcularDescuentoByKls() {
        Integer descuento = calculator.calcularDescuentoByKls(10);
        Assertions.assertEquals(700, descuento);
    }

    @Test
    public void testCalcularPagoTotal() {
        Integer pagoTotal = calculator.calcularPagoTotal(10000, 2000, 3000, 0.2, 1000, 500, 800);
        Assertions.assertEquals(15700, pagoTotal);
    }

    @Test
    public void testCalcularRetencionWithRetencionTrue() {
        Double retencion = calculator.calcularRetencion(17600, true);
        Assertions.assertEquals(2288.0, retencion);
    }

    @Test
    public void testCalcularRetencionWithRetencionFalse() {
        Double retencion = calculator.calcularRetencion(17600, false);
        Assertions.assertEquals(0.0, retencion);
    }

    @Test
    public void testCalcularMontoFinal() {
        Double montoFinal = calculator.calcularMontoFinal(17600, 2288.0);
        Assertions.assertEquals(15312.0, montoFinal);
    }

    @Test
    public void testCalcularBonificacionPorFrecuencia() {
        Double bonificacion = calculator.calcularBonificacionPorFrecuencia(15, 5, 3);
        Assertions.assertEquals(0.2, bonificacion);
    }
}
