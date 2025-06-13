package b4u.pocketpartners.backend.operations.interfaces.rest.transform;

import b4u.pocketpartners.backend.operations.domain.model.commands.CreateReceiptForExpenseCommand;
import b4u.pocketpartners.backend.operations.domain.model.commands.CreateReceiptForPaymentCommand;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.CreateExpenseReceiptResource;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.CreatePaymentReceiptResource;

public class CreateReceiptCommandFromResourceAssembler {
    public static CreateReceiptForPaymentCommand toCommandFromResource(CreatePaymentReceiptResource receipt) {
        return new CreateReceiptForPaymentCommand(
                receipt.name(),
                receipt.receiptNumber(),
                receipt.amount(),
                receipt.issueDate(),
                receipt.imagePath(),
                receipt.paymentId()
        );
    }

    public static CreateReceiptForExpenseCommand toCommandFromResource(CreateExpenseReceiptResource receipt) {
        return new CreateReceiptForExpenseCommand(
                receipt.name(),
                receipt.receiptNumber(),
                receipt.amount(),
                receipt.issueDate(),
                receipt.imagePath(),
                receipt.expenseId()
        );
    }
}
