package cuentas;

public class CajaDeAhorro extends Cuenta {
    public CajaDeAhorro(double saldo, String nroCuenta, String titular, boolean cuentaHabilitada) {
        super(saldo, nroCuenta, titular, cuentaHabilitada);
    }

    @Override
    protected double comisionPorTransferencia() {
        return 0.015;
    }

    @Override
    public void transferirSaldoA(CajaDeAhorro cuentaDestino, double montoATransferir) {
        super.transferirSinCobrarComision(montoATransferir, cuentaDestino);
    }

    @Override
    public void transferirSaldoA(CuentaCorriente cuentaDestino, double montoATransferir) {
        if (cuentaDestino.mismoTitular(this))
            super.transferirSinCobrarComision(montoATransferir, cuentaDestino);
        else
            super.transferirCobrandoComision(montoATransferir, cuentaDestino);
    }
}
