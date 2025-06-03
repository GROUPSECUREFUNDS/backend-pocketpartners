package b4u.pocketpartners.backend.operations.domain.services;

import b4u.pocketpartners.backend.operations.domain.model.commands.*;
import b4u.pocketpartners.backend.operations.domain.model.entities.OcrReceipt;
import b4u.pocketpartners.backend.operations.domain.model.entities.Receipt;

public interface ReceiptCommandService {
    Receipt handle(CreateReceiptForPaymentCommand command);
    Receipt handle(CreateReceiptForExpenseCommand command);
    OcrReceipt handle(CreateOcrReceiptFromReceiptCommand command);
    OcrReceipt handle(CreateOcrReceiptFromImageCommand command);
    void handle(DeleteReceiptCommand command);
}
