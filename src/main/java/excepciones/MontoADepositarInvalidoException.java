package excepciones;

import cuentas.Cuenta;

import java.text.MessageFormat;

public class MontoADepositarInvalidoException extends Exception {
    private static final String messageTemplate = "El monto a depositar ({0}) en la cuenta {1} es inválido ya que no es un valor positivo.";

    public MontoADepositarInvalidoException(Cuenta cuenta, double montoInvalido) {
        super(MessageFormat.format(messageTemplate, montoInvalido, cuenta.getNroCuenta()));
    }
}
