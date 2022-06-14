package cuentas;

import excepciones.CuentaInhabilitadaException;
import excepciones.MontoADepositarInvalidoException;
import excepciones.SaldoInsuficienteException;
import excepciones.TransferenciaException;
import lombok.Getter;

public abstract class Cuenta {
    @Getter protected double saldo;
    @Getter private final String nroCuenta;
    @Getter private final String titular;
    private final boolean cuentaHabilitada;

    public Cuenta(double saldo, String nroCuenta, String titular, boolean cuentaHabilitada) {
        this.saldo = saldo;
        this.nroCuenta = nroCuenta;
        this.titular = titular;
        this.cuentaHabilitada = cuentaHabilitada;
    }

    protected boolean mismoTitular(Cuenta otraCuenta) {
        return this.getTitular().equals(otraCuenta.getTitular());
    }

    protected void retirar(double montoARetirar) throws SaldoInsuficienteException, CuentaInhabilitadaException {
        this.saldo -= montoARetirar;
    }

    protected void depositar(double montoADepositar) throws CuentaInhabilitadaException, MontoADepositarInvalidoException {
        if (this.cuentaHabilitada) {
            if (montoADepositar > 0)
                this.saldo += montoADepositar;
            else
                throw new MontoADepositarInvalidoException(this, montoADepositar);
        } else {
            throw new CuentaInhabilitadaException(this);
        }
    }

    protected abstract double comisionPorTransferencia();

    protected void retirarSinCobrarComision(double montoARetirar) throws SaldoInsuficienteException {
        if (this.cuentaHabilitada) {
            if (this.getSaldo() >= montoARetirar)
                this.retirar(montoARetirar); // Delego el retiro de dinero a la cuenta específica
            else
                throw new SaldoInsuficienteException(this);
        } else
            throw new CuentaInhabilitadaException(this);
    }

    protected void retirarCobrandoComision(double montoARetirar) throws SaldoInsuficienteException, CuentaInhabilitadaException {
        double comision = montoARetirar * this.comisionPorTransferencia();
        this.retirarSinCobrarComision(montoARetirar + comision);
    }

    public abstract void transferirSaldoA(CajaDeAhorro cuentaDestino, double montoATransferir) throws SaldoInsuficienteException, TransferenciaException;

    public abstract void transferirSaldoA(CuentaCorriente cuentaDestino, double montoATransferir) throws SaldoInsuficienteException, TransferenciaException;

    protected void transferirSinCobrarComision(double montoATransferir, Cuenta cuentaDestino) throws TransferenciaException {
        try {
            this.retirarSinCobrarComision(montoATransferir);
            cuentaDestino.depositar(montoATransferir);
        } catch(CuentaInhabilitadaException | SaldoInsuficienteException | MontoADepositarInvalidoException ex) {
            throw new TransferenciaException(this, cuentaDestino, montoATransferir, ex.getCause());
        }
    }

    protected void transferirCobrandoComision(double montoATransferir, Cuenta cuentaDestino) throws TransferenciaException {
        try {
            this.retirarCobrandoComision(montoATransferir);
            cuentaDestino.depositar(montoATransferir);
        } catch(CuentaInhabilitadaException | SaldoInsuficienteException | MontoADepositarInvalidoException ex) {
            throw new TransferenciaException(this, cuentaDestino, montoATransferir, ex.getCause());
        }
    }
}
