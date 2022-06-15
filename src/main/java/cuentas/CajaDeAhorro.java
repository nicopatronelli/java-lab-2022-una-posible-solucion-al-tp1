package cuentas;

import excepciones.CuentaInhabilitadaException;
import excepciones.MontoADepositarInvalidoException;
import excepciones.SaldoInsuficienteException;

public class CajaDeAhorro extends Cuenta {
    public CajaDeAhorro(double saldo, String nroCuenta, String titular, boolean cuentaHabilitada) {
        super(saldo, nroCuenta, titular, cuentaHabilitada);
    }

    @Override
    protected double comisionPorTransferencia() {
        return 0.015;
    }

    @Override
    public void transferirSaldoA(CajaDeAhorro cuentaDestino, double montoATransferir) throws CuentaInhabilitadaException, SaldoInsuficienteException, MontoADepositarInvalidoException {
        super.transferirSinCobrarComision(cuentaDestino, montoATransferir);
    }

    @Override
    public void transferirSaldoA(CuentaCorriente cuentaDestino, double montoATransferir) throws CuentaInhabilitadaException, SaldoInsuficienteException, MontoADepositarInvalidoException {
        if (cuentaDestino.mismoTitular(this))
            super.transferirSinCobrarComision(cuentaDestino, montoATransferir);
        else
            super.transferirCobrandoComision(cuentaDestino, montoATransferir);
    }
}
