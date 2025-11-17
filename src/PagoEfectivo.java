// Estrategia concreta - Pago en efectivo
public class PagoEfectivo implements EstrategiaPago {
    private static final double DESCUENTO = 0.05;

    @Override
    public double pagar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a pagar debe ser mayor a 0");
        }
        double montoConDescuento = monto * (1 - DESCUENTO);
        System.out.println("Pagando $" + monto + " en efectivo. Se aplicó " + (DESCUENTO * 100) + "% descuento.");
        System.out.println("Total a pagar: $" + montoConDescuento);
        return montoConDescuento;
    }
}
