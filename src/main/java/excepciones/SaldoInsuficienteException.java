package excepciones;

import cuentas.Cuenta;

import java.text.MessageFormat;

public class SaldoInsuficienteException extends Exception {
    private static final String messageTemplate = "La cuenta {0} perteneciente a {1} no tiene saldo suficiente para realizar esta operación.";

    public SaldoInsuficienteException(Cuenta cuenta) {
        super(MessageFormat.format(messageTemplate, cuenta.getNroCuenta(), cuenta.getTitular()));
    }
}
