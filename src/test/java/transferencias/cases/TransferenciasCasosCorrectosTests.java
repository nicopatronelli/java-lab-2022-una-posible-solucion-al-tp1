package transferencias.cases;

import cuentas.CajaDeAhorro;
import cuentas.CuentaCorriente;
import excepciones.SaldoInsuficienteException;
import excepciones.TransferenciaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static transferencias.util.TransferenciasTestsFluent.cuentaDestino;
import static transferencias.util.TransferenciasTestsFluent.cuentaOrigen;

public class TransferenciasCasosCorrectosTests {
    private CajaDeAhorro cajaDeAhorroDeAsh;
    private CajaDeAhorro cajaDeAhorroDeMisty;
    private CuentaCorriente cuentaCorrienteDeGary;
    private CuentaCorriente cuentaCorrienteDeAsh;

    @BeforeEach
    void setUpDataSet() {
        cajaDeAhorroDeAsh = new CajaDeAhorro(1500, "CA-171874-02/20", "Ash Ketchum", true);
        cajaDeAhorroDeMisty = new CajaDeAhorro(1200, "CA-1948712-05/21", "Misty", true);
        cuentaCorrienteDeGary = new CuentaCorriente(1000, 500, "CC-287194-03/18", "Gary Oak", true);
        cuentaCorrienteDeAsh = new CuentaCorriente(800, 500, "CC-171874-04/20", "Ash Ketchum", true);
    }

    @Test
    @DisplayName("No se cobra comisión en una transferencia entre cajas de ahorro de diferentes titulares")
    void transferencia_entre_cajas_de_ahorro_de_diferentes_titulares() throws SaldoInsuficienteException, TransferenciaException {
        cuentaOrigen(cajaDeAhorroDeMisty).transferirSaldoA(cuentaDestino(cajaDeAhorroDeAsh), 200);

        assertEquals(1000, cajaDeAhorroDeMisty.getSaldo());
        assertEquals(1700, cajaDeAhorroDeAsh.getSaldo());
    }

    @Test
    @DisplayName("No se cobra comisión en una transferencia entre cuentas corrientes de diferentes titulares")
    void transferencia_entre_cuentas_corrientes_de_diferentes_titulares() throws SaldoInsuficienteException, TransferenciaException {
        cuentaOrigen(cuentaCorrienteDeGary).transferirSaldoA(cuentaDestino(cuentaCorrienteDeAsh), 450);

        assertEquals(1050, cuentaCorrienteDeGary.getSaldo()); // 1000 + 500 (descubierto) - 450 = 1050
        assertEquals(1750, cuentaCorrienteDeAsh.getSaldo()); // 800 + 500 (descubierto) + 450 = 1750
    }

    @Test
    @DisplayName("No se cobra comisión en una transferencia de una caja de ahorro a una cuenta corriente pertenecientes al mismo titular")
    void transferencia_mismo_titular_de_caja_de_ahorro_a_cuenta_corriente() throws SaldoInsuficienteException, TransferenciaException {
        cuentaOrigen(cajaDeAhorroDeAsh).transferirSaldoA(cuentaDestino(cuentaCorrienteDeAsh), 500);

        assertEquals(1000, cajaDeAhorroDeAsh.getSaldo()); // 1500 - 500
        assertEquals(1800, cuentaCorrienteDeAsh.getSaldo()); // 800 + 500 (descubierto) + 500 = 1800
    }

    @Test
    @DisplayName("No se cobra comisión en una transferencia de una cuenta corriente a una caja de ahorro pertenecientes al mismo titular")
    void transferencia_mismo_titular_de_cuenta_corriente_a_caja_de_ahorro() throws SaldoInsuficienteException, TransferenciaException {
        cuentaOrigen(cuentaCorrienteDeAsh).transferirSaldoA(cuentaDestino(cajaDeAhorroDeAsh), 500);

        assertEquals(800, cuentaCorrienteDeAsh.getSaldo()); // 800 + 500 (descubierto) - 500 = 800
        assertEquals(2000, cajaDeAhorroDeAsh.getSaldo()); // 1500 + 500
    }

    @Test
    @DisplayName("En una transferencia entre distintos titulares y tipo de cuenta, se cobra 1,5% de comision si la cuenta origen es caja de ahorro")
    void transferencia_entre_distintos_titulares_con_cuenta_origen_caja_de_ahorro_y_cuenta_destino_cuenta_corriente() throws SaldoInsuficienteException, TransferenciaException {
        cuentaOrigen(cajaDeAhorroDeAsh).transferirSaldoA(cuentaDestino(cuentaCorrienteDeGary), 500);

        assertEquals(992.5, cajaDeAhorroDeAsh.getSaldo()); // 1500 - 500 - 500*0,015 = 1500 - 500 - 7,5 = 1500 - 507,5 = 992,5
        assertEquals(2000, cuentaCorrienteDeGary.getSaldo()); // 1000 + 500 (descubierto) + 500 = 2000
    }

    @Test
    @DisplayName("En una transferencia entre distintos titulares y tipo de cuenta, se cobra 3% de comision si la cuenta origen es cuenta corriente")
    void transferencia_entre_distintos_titulares_con_cuenta_origen_caja_de_corriente_y_cuenta_destino_caja_de_ahorro() throws SaldoInsuficienteException, TransferenciaException {
        cuentaOrigen(cuentaCorrienteDeAsh).transferirSaldoA(cuentaDestino(cajaDeAhorroDeMisty), 500);

        assertEquals(785, cuentaCorrienteDeAsh.getSaldo()); // 800 + 500 (descubierto) - 500 - 500*0,03 = 800 - 15 = 785
        assertEquals(1700, cajaDeAhorroDeMisty.getSaldo()); // 1200 + 500
    }
}
