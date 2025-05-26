package b4u.pocketpartners.backend.operations.application.internal.queryservices;

import b4u.pocketpartners.backend.operations.domain.exceptions.PaymentNotFoundException;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.domain.model.entities.Receipt;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetAllReceiptsByPaymentIdQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetPaymentByIdQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetReceiptByIdQuery;
import b4u.pocketpartners.backend.operations.domain.services.PaymentQueryService;
import b4u.pocketpartners.backend.operations.domain.services.ReceiptQueryService;
import b4u.pocketpartners.backend.operations.infrastructure.persistence.jpa.repositories.ReceiptRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReceiptQueryServiceImpl implements ReceiptQueryService {
    private ReceiptRepository receiptRepository;
    private PaymentQueryService paymentQueryService;

    @Override
    public List<Receipt> handle(GetAllReceiptsByPaymentIdQuery query) {
        Optional<Payment> payment = paymentQueryService.handle(new GetPaymentByIdQuery(query.paymentId()));

        if(payment.isEmpty())
            throw new PaymentNotFoundException(query.paymentId());

        return receiptRepository.findByPayment(payment.get());
    }

    @Override
    public Optional<Receipt> handle(GetReceiptByIdQuery query) {
        return receiptRepository.findById(query.receiptId());
    }
}
