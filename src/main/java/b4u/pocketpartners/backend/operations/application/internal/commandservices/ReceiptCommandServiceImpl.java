package b4u.pocketpartners.backend.operations.application.internal.commandservices;


import b4u.pocketpartners.backend.groups.domain.exceptions.PaymentNotFoundException;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.domain.model.commands.CreateReceiptForPaymentCommand;
import b4u.pocketpartners.backend.operations.domain.model.entities.Receipt;
import b4u.pocketpartners.backend.operations.domain.model.valueobjects.Amount;
import b4u.pocketpartners.backend.operations.domain.services.ReceiptCommandService;
import b4u.pocketpartners.backend.operations.infrastructure.persistence.jpa.repositories.PaymentRepository;
import b4u.pocketpartners.backend.operations.infrastructure.persistence.jpa.repositories.ReceiptRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReceiptCommandServiceImpl implements ReceiptCommandService {
    private ReceiptRepository receiptRepository;
    private PaymentRepository paymentRepository;

    @Override
    public Receipt handle(CreateReceiptForPaymentCommand command) {
        Payment payment = paymentRepository.findById(command.paymentId())
                .orElseThrow(()-> new PaymentNotFoundException(command.paymentId()));

        Receipt receipt = new Receipt(
                command.name(),
                new Amount(command.amount()),
                command.issueDate(),
                command.imagePath());

        // Validate that the receipt amount does not exceed the payment amount
        if(payment.getAmount().compareTo(receipt.getAmount().amount()) < 0) {
            throw new IllegalArgumentException("Receipt amount cannot exceed payment amount." +
                    " Payment amount is: " + payment.getAmount() +
                    ", Receipt amount is: " + receipt.getAmount().getAmount());
        }

        //Validate that sum all receipts for this payment does not exceed the payment amount
        List<Receipt> receipts = receiptRepository.findByPayment(payment);
        Amount totalReceiptsAmount = receipts.stream()
                .map(Receipt::getAmount)
                .reduce(new Amount(), Amount::add);

        Amount remainingAmount = payment.getAmountAsObject().subtract(totalReceiptsAmount);
        if (remainingAmount.isLessThan(receipt.getAmount())) {
            throw new IllegalArgumentException("Total receipts amount cannot exceed payment amount."+
                    " Remaining amount for this payment is: " + remainingAmount.getAmount());
        }

        receipt.assignToPayment(payment);
        receiptRepository.save(receipt);
        return receipt;
    }
}
