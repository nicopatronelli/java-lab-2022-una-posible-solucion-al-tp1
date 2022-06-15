package excepciones;

import cuentas.Cuenta;

import java.text.MessageFormat;

public class CuentaInhabilitadaException extends Exception {
    private static final String messageTemplate = "No se puede realizar la operación porque la cuenta {0} perteneciente a {1} se encuentra inhabilitada. "
            + "Pongáse en contacto con un representante bancario para más información.";

    public CuentaInhabilitadaException(Cuenta cuenta) {
        super(MessageFormat.format(messageTemplate, cuenta.getNroCuenta(), cuenta.getTitular()));
    }
}
