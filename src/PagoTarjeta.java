// Estrategia concreta - Pago con tarjeta
public class PagoTarjeta implements EstrategiaPago {
    private String numeroTarjeta;

    public PagoTarjeta(String numeroTarjeta) {
        if (numeroTarjeta == null || numeroTarjeta.length() < 4) {
            throw new IllegalArgumentException("El número de tarjeta debe tener al menos 4 dígitos");
        }
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public double pagar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a pagar debe ser mayor a 0");
        }
        System.out.println("Pagando $" + monto + " con tarjeta terminada en " +
                          numeroTarjeta.substring(numeroTarjeta.length() - 4));
        return monto;
    }
}
