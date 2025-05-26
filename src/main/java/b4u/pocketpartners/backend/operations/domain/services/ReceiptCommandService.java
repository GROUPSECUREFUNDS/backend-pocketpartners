package b4u.pocketpartners.backend.operations.domain.services;

import b4u.pocketpartners.backend.operations.domain.model.commands.CreateReceiptCommand;
import b4u.pocketpartners.backend.operations.domain.model.entities.Receipt;

public interface ReceiptCommandService {
    Receipt handle(CreateReceiptCommand command);
}
