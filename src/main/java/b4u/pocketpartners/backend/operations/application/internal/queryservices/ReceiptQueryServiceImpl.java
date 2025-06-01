package b4u.pocketpartners.backend.operations.application.internal.queryservices;

import b4u.pocketpartners.backend.operations.domain.exceptions.PaymentNotFoundException;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.domain.model.entities.ExpenseReceipt;
import b4u.pocketpartners.backend.operations.domain.model.entities.PaymentReceipt;
import b4u.pocketpartners.backend.operations.domain.model.entities.Receipt;
import b4u.pocketpartners.backend.operations.domain.model.queries.*;
import b4u.pocketpartners.backend.operations.domain.services.ExpenseQueryService;
import b4u.pocketpartners.backend.operations.domain.services.PaymentQueryService;
import b4u.pocketpartners.backend.operations.domain.services.ReceiptQueryService;
import b4u.pocketpartners.backend.operations.infrastructure.persistence.jpa.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReceiptQueryServiceImpl implements ReceiptQueryService {
    private ReceiptRepository receiptRepository;
    private ExpenseQueryService expenseQueryService;
    private PaymentQueryService paymentQueryService;

    @Override
    public List<PaymentReceipt> handle(GetAllReceiptsByPaymentIdQuery query) {
        Optional<Payment> payment = paymentQueryService.handle(new GetPaymentByIdQuery(query.paymentId()));

        if(payment.isEmpty())
            throw new PaymentNotFoundException(query.paymentId());

        return payment.get().getReceipts();
    }

    @Override
    public Optional<Receipt> handle(GetReceiptByIdQuery query) {
        return receiptRepository.findById(query.receiptId());
    }

    @Override
    public List<ExpenseReceipt> handle(GetAllReceiptsByExpenseIdQuery query) {
        var queryExpense = new GetExpenseByIdQuery(query.expenseId());
        var expense = expenseQueryService.handle(queryExpense)
                .orElseThrow(() -> new PaymentNotFoundException(query.expenseId()));

        return expense.getReceipts();
    }

}
