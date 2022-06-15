package cuentas;

import excepciones.CuentaInhabilitadaException;
import excepciones.MontoADepositarInvalidoException;
import excepciones.SaldoInsuficienteException;
import lombok.AccessLevel;
import lombok.Getter;

public abstract class Cuenta {
    @Getter protected double saldo;
    private final String nroCuenta;
    @Getter(AccessLevel.PROTECTED) private final String titular;
    private final boolean cuentaHabilitada;

    public Cuenta(double saldo, String nroCuenta, String titular, boolean cuentaHabilitada) {
        this.saldo = saldo;
        this.nroCuenta = nroCuenta;
        this.titular = titular;
        this.cuentaHabilitada = cuentaHabilitada;
    }

    public abstract void transferirSaldoA(CajaDeAhorro cuentaDestino, double montoATransferir);

    public abstract void transferirSaldoA(CuentaCorriente cuentaDestino, double montoATransferir);

    protected boolean mismoTitular(Cuenta otraCuenta) {
        return this.getTitular().equals(otraCuenta.getTitular());
    }

    protected void retirar(double montoARetirar) throws SaldoInsuficienteException, CuentaInhabilitadaException {
        this.saldo -= montoARetirar;
    }

    protected void depositar(double montoADepositar) throws CuentaInhabilitadaException, MontoADepositarInvalidoException {
        if (this.cuentaHabilitada) {
            if (montoADepositar > 0) this.saldo += montoADepositar;
            else throw new MontoADepositarInvalidoException("El monto a depositar: " + montoADepositar + " es un monto inválido ya que no es un valor positivo.");
        } else {
            throw new CuentaInhabilitadaException("No se puede realizar la operación porque la cuenta se encuentra inhabilitada. " +
                    " Pongáse en contacto con un represente bancario para más información.");
        }
    }

    protected abstract double comisionPorTransferencia();

    protected void retirarSinCobrarComision(double montoARetirar) {
        if (this.cuentaHabilitada) {
            if (this.getSaldo() >= montoARetirar) {
                this.retirar(montoARetirar); // Delego el retiro de dinero a la cuenta específica
            }
            // No puedo retirar el dinero
            else throw new SaldoInsuficienteException("No tiene saldo suficiente para realizar esta operación");
        } else throw new CuentaInhabilitadaException("No se puede realizar la operación porque la cuenta se encuentra inhabilitada. " +
                " Pongáse en contacto con un representante bancario para más información.");
    }

    protected void retirarCobrandoComision(double montoARetirar) throws SaldoInsuficienteException, CuentaInhabilitadaException {
        double comision = montoARetirar * this.comisionPorTransferencia();
        this.retirarSinCobrarComision(montoARetirar + comision);
    }

    protected void transferirSinCobrarComision(double montoATransferir, Cuenta cuentaDestino) throws SaldoInsuficienteException {
        this.retirarSinCobrarComision(montoATransferir);
        cuentaDestino.depositar(montoATransferir);
    }

    protected void transferirCobrandoComision(double montoATransferir, Cuenta cuentaDestino) throws CuentaInhabilitadaException {
        this.retirarCobrandoComision(montoATransferir);
        cuentaDestino.depositar(montoATransferir);
    }
}
