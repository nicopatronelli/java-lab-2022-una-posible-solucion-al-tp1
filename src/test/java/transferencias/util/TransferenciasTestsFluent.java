package transferencias.util;

import cuentas.CajaDeAhorro;
import cuentas.Cuenta;
import cuentas.CuentaCorriente;

public class TransferenciasTestsFluent {
    public static Cuenta cuentaOrigen(Cuenta cuenta) {
        return cuenta;
    }

    public static CajaDeAhorro cuentaDestino(CajaDeAhorro cuenta) {
        return cuenta;
    }

    public static CuentaCorriente cuentaDestino(CuentaCorriente cuenta) {
        return cuenta;
    }
}
