package b4u.pocketpartners.backend.operations.domain.services;

import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.domain.model.queries.*;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentQueryService {
    List<Payment> handle(GetAllPaymentsQuery query);
    Optional<Payment> handle(GetPaymentByIdQuery query);
    List<Payment> handle(GetAllPaymentsByUserInformationIdQuery query);
    List<Payment> handle(GetAllPaymentsByExpenseIdQuery query);
    Optional<Payment> handle(GetPaymentByUserInformationIdAndExpenseId query);
    List<Payment> handle(GetAllPaymentsByUserIdAndStatusQuery query);

    List<Payment> handle(GetIncomingPaymentsByUserInformationIdQuery query);
}
