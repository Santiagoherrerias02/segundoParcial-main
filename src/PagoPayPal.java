// Estrategia concreta - Pago con PayPal
public class PagoPayPal implements EstrategiaPago {
    private String email;

    public PagoPayPal(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("El email debe ser válido");
        }
        this.email = email;
    }

    @Override
    public double pagar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a pagar debe ser mayor a 0");
        }
        System.out.println("Pagando $" + monto + " con PayPal desde la cuenta: " + email);
        return monto;
    }
}
