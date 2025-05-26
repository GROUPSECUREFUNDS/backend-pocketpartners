package b4u.pocketpartners.backend.operations.domain.exceptions;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(Long paymentId) {
        super("Payment with id:" + paymentId+ " not found");
    }
}
