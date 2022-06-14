package excepciones;

import cuentas.Cuenta;

public class TransferenciaException extends Exception {
    private static final String messageTemplate = "La transferencia de la cuenta {0} a la cuenta {1} por el monto de {2} no pudo realizarse debido a que {3}.";

    public TransferenciaException(Cuenta cuentaOrigen, Cuenta cuentaDestino, double montoATransferir, Throwable cause) {

    }
}
