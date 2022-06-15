package cuentas;

import excepciones.CuentaInhabilitadaException;
import excepciones.MontoADepositarInvalidoException;
import excepciones.SaldoInsuficienteException;

public class CuentaCorriente extends Cuenta {
    private double saldoDescubierto;

    public CuentaCorriente(double saldo, double saldoDescubierto, String nroCuenta, String titular, boolean cuentaHabilitada) {
        super(saldo, nroCuenta, titular, cuentaHabilitada);
        this.saldoDescubierto = saldoDescubierto;
    }

    @Override
    public double getSaldo() {
        return super.getSaldo() + this.saldoDescubierto;
    }

    @Override
    protected void retirar(double montoARetirar) {
//      En este punto se que la CC tiene saldo suficiente total para el retiro
        if (this.saldo >= montoARetirar)
            super.retirar(montoARetirar);
        else {
            // Uso saldo descubierto
            double montoARetirarDelSaldoDescubierto = montoARetirar - this.saldo;
            this.saldoDescubierto -= montoARetirarDelSaldoDescubierto;
            System.out.println("Se han debitado $" + montoARetirarDelSaldoDescubierto + " pertenecientes al saldo descubierto.");
            this.saldo = 0;
        }
    }

    @Override
    protected double comisionPorTransferencia() {
        return 0.03;
    }

    @Override
    public void transferirSaldoA(CuentaCorriente cuentaDestino, double montoATransferir) throws CuentaInhabilitadaException, SaldoInsuficienteException, MontoADepositarInvalidoException {
        super.transferirSinCobrarComision(cuentaDestino, montoATransferir);
    }

    @Override
    public void transferirSaldoA(CajaDeAhorro cuentaDestino, double montoATransferir) throws CuentaInhabilitadaException, SaldoInsuficienteException, MontoADepositarInvalidoException{
        if (cuentaDestino.mismoTitular(this))
            super.transferirSinCobrarComision(cuentaDestino, montoATransferir);
        else
            super.transferirCobrandoComision(cuentaDestino, montoATransferir);
    }
}
