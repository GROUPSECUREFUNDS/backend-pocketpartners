package b4u.pocketpartners.backend.operations.domain.services;

import b4u.pocketpartners.backend.operations.domain.model.entities.ExpenseReceipt;
import b4u.pocketpartners.backend.operations.domain.model.entities.PaymentReceipt;
import b4u.pocketpartners.backend.operations.domain.model.entities.Receipt;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetAllReceiptsByExpenseIdQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetAllReceiptsByPaymentIdQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetReceiptByIdQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetReceiptTextByIdQuery;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

public interface ReceiptQueryService {
    List<PaymentReceipt> handle(GetAllReceiptsByPaymentIdQuery query);
    Optional<Receipt> handle(GetReceiptByIdQuery query);
    String handle(GetReceiptTextByIdQuery query) throws IOException;
    List<ExpenseReceipt> handle(GetAllReceiptsByExpenseIdQuery query);
}
