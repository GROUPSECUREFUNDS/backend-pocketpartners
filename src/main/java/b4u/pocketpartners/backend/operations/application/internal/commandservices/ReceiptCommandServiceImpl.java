package b4u.pocketpartners.backend.operations.application.internal.commandservices;


import b4u.pocketpartners.backend.groups.domain.exceptions.PaymentNotFoundException;
import b4u.pocketpartners.backend.operations.domain.exceptions.ExpenseNotFoundException;
import b4u.pocketpartners.backend.operations.domain.exceptions.ReceiptNotFoundException;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Expense;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.domain.model.commands.*;
import b4u.pocketpartners.backend.operations.domain.model.entities.ExpenseReceipt;
import b4u.pocketpartners.backend.operations.domain.model.entities.OcrReceipt;
import b4u.pocketpartners.backend.operations.domain.model.entities.PaymentReceipt;
import b4u.pocketpartners.backend.operations.domain.model.entities.Receipt;
import b4u.pocketpartners.backend.operations.domain.model.valueobjects.Amount;
import b4u.pocketpartners.backend.operations.domain.ports.out.ReceiptOcrPort;
import b4u.pocketpartners.backend.operations.domain.services.ReceiptCommandService;
import b4u.pocketpartners.backend.operations.infrastructure.persistence.jpa.repositories.ExpenseRepository;
import b4u.pocketpartners.backend.operations.infrastructure.persistence.jpa.repositories.PaymentRepository;
import b4u.pocketpartners.backend.operations.infrastructure.persistence.jpa.repositories.ReceiptRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReceiptCommandServiceImpl implements ReceiptCommandService {
    private ReceiptRepository receiptRepository;
    private ExpenseRepository expenseRepository;
    private PaymentRepository paymentRepository;
    private ReceiptOcrPort receiptOcrPort;

    @Override
    public Receipt handle(CreateReceiptForPaymentCommand command) {
        Payment payment = paymentRepository.findById(command.paymentId())
                .orElseThrow(()-> new PaymentNotFoundException(command.paymentId()));

        Receipt receipt = new PaymentReceipt(
                command.name(),
                new Amount(command.amount()),
                command.issueDate(),
                command.imagePath(),
                payment
        );

        // Validate that the receipt amount does not exceed the payment amount
        if(payment.getAmount().compareTo(receipt.getAmount().amount()) < 0) {
            throw new IllegalArgumentException("Receipt amount cannot exceed payment amount." +
                    " Payment amount is: " + payment.getAmount() +
                    ", Receipt amount is: " + receipt.getAmount().getAmount());
        }

        //Validate that sum all receipts for this payment does not exceed the payment amount
        List<PaymentReceipt> receipts = payment.getReceipts();
        Amount totalReceiptsAmount = receipts.stream()
                .map(Receipt::getAmount)
                .reduce(new Amount(), Amount::add);

        Amount remainingAmount = payment.getAmountAsObject().subtract(totalReceiptsAmount);
        if (remainingAmount.isLessThan(receipt.getAmount())) {
            throw new IllegalArgumentException("Total receipts amount cannot exceed payment amount."+
                    " Remaining amount for this payment is: " + remainingAmount.getAmount());
        }

        receiptRepository.save(receipt);
        return receipt;
    }

    @Override
    public Receipt handle(CreateReceiptForExpenseCommand command) {
        Expense expense = expenseRepository.findById(command.expenseId())
                .orElseThrow(()-> new ExpenseNotFoundException(command.expenseId()));

        ExpenseReceipt receipt = new ExpenseReceipt(
                command.name(),
                new Amount(command.amount()),
                command.issueDate(),
                command.imagePath(),
                expense
        );

        // Validate that the receipt amount does not exceed the payment amount
        if(expense.getAmount().compareTo(receipt.getAmount().amount()) < 0) {
            throw new IllegalArgumentException("Receipt amount cannot exceed payment amount." +
                    " Expense amount is: " + expense.getAmount() +
                    ", Receipt amount is: " + receipt.getAmount().getAmount());
        }

        //Validate that sum all receipts for this payment does not exceed the payment amount
        List<ExpenseReceipt> receipts = expense.getReceipts();
        Amount totalReceiptsAmount = receipts.stream()
                .map(Receipt::getAmount)
                .reduce(new Amount(), Amount::add);

        Amount remainingAmount = expense.getAmountAsObject().subtract(totalReceiptsAmount);
        if (remainingAmount.isLessThan(receipt.getAmount())) {
            throw new IllegalArgumentException("Total receipts amount cannot exceed expense amount."+
                    " Remaining amount for this expense is: " + remainingAmount.getAmount());
        }

        receiptRepository.save(receipt);
        return receipt;
    }

    @Override
    public OcrReceipt handle(CreateOcrReceiptFromReceiptCommand command) {
        var receipt = receiptRepository.findById(command.originalReceiptId())
                .orElseThrow(()-> new ReceiptNotFoundException(command.originalReceiptId()));

        if(receipt.getImagePath()==null) {
            throw new IllegalArgumentException("Receipt image path is not set for OCR processing.");
        }

        if(receipt.getOcrReceipt() != null) {
            return receipt.getOcrReceipt(); // Return existing OCR receipt if it already exists
        }

        OcrReceipt ocrReceipt = receiptOcrPort.processReceiptImage(receipt.getImagePath());
        ocrReceipt.assignToOriginalReceipt(receipt);
        receiptRepository.save(ocrReceipt);
        return ocrReceipt;
    }

    @Override
    public OcrReceipt handle(CreateOcrReceiptFromImageCommand command) {
        return receiptOcrPort.processReceiptImage(command.imageUrl());
    }

    @Override
    public void handle(DeleteReceiptCommand command) {
        Receipt receipt = receiptRepository.findById(command.receiptId())
                .orElseThrow(() -> new ReceiptNotFoundException(command.receiptId()));

        receiptRepository.delete(receipt);
    }
}
