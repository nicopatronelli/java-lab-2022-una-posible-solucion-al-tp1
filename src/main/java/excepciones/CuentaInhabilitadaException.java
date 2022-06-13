package excepciones;

public class CuentaInhabilitadaException extends RuntimeException {
    public CuentaInhabilitadaException(String message) {
        super(message);
    }
}
