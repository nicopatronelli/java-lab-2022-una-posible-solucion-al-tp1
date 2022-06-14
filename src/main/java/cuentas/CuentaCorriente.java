package cuentas;

import excepciones.CuentaInhabilitadaException;
import excepciones.SaldoInsuficienteException;
import excepciones.TransferenciaException;

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
    protected void retirar(double montoARetirar) throws SaldoInsuficienteException, CuentaInhabilitadaException {
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
    public void transferirSaldoA(CuentaCorriente cuentaDestino, double montoATransferir) throws SaldoInsuficienteException, TransferenciaException {
        super.transferirSinCobrarComision(montoATransferir, cuentaDestino);
    }

    @Override
    public void transferirSaldoA(CajaDeAhorro cuentaDestino, double montoATransferir) throws SaldoInsuficienteException, TransferenciaException {
        if (cuentaDestino.mismoTitular(this))
            super.transferirSinCobrarComision(montoATransferir, cuentaDestino);
        else
            super.transferirCobrandoComision(montoATransferir, cuentaDestino);
    }
}
