package b4u.pocketpartners.backend.operations.domain.services;

import b4u.pocketpartners.backend.operations.domain.model.commands.CreateReceiptForPaymentCommand;
import b4u.pocketpartners.backend.operations.domain.model.entities.Receipt;

public interface ReceiptCommandService {
    Receipt handle(CreateReceiptForPaymentCommand command);
}
