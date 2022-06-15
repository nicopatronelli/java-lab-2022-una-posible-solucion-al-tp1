package transferencias.cases;

import cuentas.CajaDeAhorro;
import cuentas.CuentaCorriente;
import excepciones.CuentaInhabilitadaException;
import excepciones.MontoADepositarInvalidoException;
import excepciones.SaldoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static transferencias.util.TransferenciasTestsFluent.cuentaDestino;
import static transferencias.util.TransferenciasTestsFluent.cuentaOrigen;

public class TransferenciasCasosDeErrorTests {
    private CajaDeAhorro cajaDeAhorroDeAsh;
    private CajaDeAhorro cajaDeAhorroDeMisty;
    private CuentaCorriente cuentaCorrienteDeAsh;

    private CajaDeAhorro cajaDeAhorroInhabilitada;
    private CuentaCorriente cuentaCorrienteInhabilitada;

    @BeforeEach
    void setUpDataSet() {
        cajaDeAhorroDeAsh = new CajaDeAhorro(1500, "CA-171874-02/20", "Ash Ketchum", true);
        cajaDeAhorroDeMisty = new CajaDeAhorro(1200, "CA-1948712-05/21", "Misty", true);
        cuentaCorrienteDeAsh = new CuentaCorriente(800, 500, "CC-171874-04/20", "Ash Ketchum", true);

        cajaDeAhorroInhabilitada = new CajaDeAhorro(1000, "CA-179837-02/15", "Brock", false);
        cuentaCorrienteInhabilitada =  new CuentaCorriente(1200, 500, "CC-177502-04/16", "Profesor Oak", false);
    }

    @Test
    @DisplayName("Se lanza la excepcion CuentaInhabilitadaException en una transferencia si la cuenta origen es una caja de ahorro y está inhabilitada")
    void se_lanza_excepcion_en_una_transferencia_si_la_cuenta_origen_es_una_caja_de_ahorro_inhabilitada() {
        assertThrows(CuentaInhabilitadaException.class,
                () -> cajaDeAhorroInhabilitada.transferirSaldoA(cajaDeAhorroDeAsh, 200));
    }

    @Test
    @DisplayName("Se lanza la excepcion CuentaInhabilitadaException en una transferencia si la cuenta destino es una caja de ahorro y está inhabilitada")
    void se_lanza_excepcion_en_una_transferencia_si_la_cuenta_destino_es_una_caja_de_ahorro_inhabilitada() {
        assertThrows(CuentaInhabilitadaException.class,
                () -> cuentaOrigen(cajaDeAhorroDeAsh).transferirSaldoA(cuentaDestino(cajaDeAhorroInhabilitada), 200));

//        assertEquals(1500, cajaDeAhorroDeAsh.getSaldo());
//        assertEquals(1000, cajaDeAhorroInhabilitada.getSaldo());
    }

    @Test
    @DisplayName("Se lanza la excepcion CuentaInhabilitadaException en una transferencia si la cuenta origen es una cuenta corriente y está inhabilitada")
    void se_lanza_excepcion_en_una_transferencia_si_la_cuenta_origen_es_una_cuenta_corriente_inhabilitada() {
        assertThrows(CuentaInhabilitadaException.class,
                () -> cuentaCorrienteInhabilitada.transferirSaldoA(cajaDeAhorroDeAsh, 200));
    }

    @Test
    @DisplayName("Se lanza la excepcion SaldoInsuficienteExcepction en una transferencia cuando el saldo de la cuenta origen es una caja de ahorro y no tiene saldo suficiente")
    void se_lanza_excepcion_en_una_transferencia_con_caja_de_ahorro_como_origen_sin_saldo_suficiente() {
        assertThrows(SaldoInsuficienteException.class,
                () -> cajaDeAhorroDeMisty.transferirSaldoA(cajaDeAhorroDeAsh, 2000));
    }

    @Test
    @DisplayName("Se lanza la excepcion SaldoInsuficienteExcepction en una transferencia cuando el saldo de la cuenta origen es una cuenta corriente y no tiene saldo suficiente")
    void se_lanza_excepcion_en_una_transferencia_con_cuenta_corriente_como_origen_sin_saldo_suficiente() {
        assertThrows(SaldoInsuficienteException.class,
                () -> cuentaCorrienteDeAsh.transferirSaldoA(cajaDeAhorroDeMisty, 2000));
    }

    @Test
    @DisplayName("Se lanza la excepcion MontoADepositarInvalidoException en una transferencia cuando el monto a transferir es cero")
    void se_lanza_excepcion_en_transferencia_si_el_monto_a_transferir_es_cero() {
        assertThrows(MontoADepositarInvalidoException.class,
                () -> cuentaCorrienteDeAsh.transferirSaldoA(cajaDeAhorroDeMisty, 0));
    }

    @Test
    @DisplayName("Se lanza la excepcion MontoADepositarInvalidoException en una transferencia cuando el monto a transferir es negativo")
    void se_lanza_excepcion_en_transferencia_si_el_monto_a_transferir_es_negativo() {
        assertThrows(MontoADepositarInvalidoException.class,
                () -> cuentaCorrienteDeAsh.transferirSaldoA(cajaDeAhorroDeMisty, -150));
    }
}
