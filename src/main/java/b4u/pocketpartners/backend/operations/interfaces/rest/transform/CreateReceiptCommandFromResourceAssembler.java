package b4u.pocketpartners.backend.operations.interfaces.rest.transform;

import b4u.pocketpartners.backend.operations.domain.model.commands.CreateReceiptForPaymentCommand;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.CreateReceiptResource;

public class CreateReceiptCommandFromResourceAssembler {
    public static CreateReceiptForPaymentCommand toCommandFromResource(CreateReceiptResource receipt){
        return new CreateReceiptForPaymentCommand(
                receipt.name(),
                receipt.amount(),
                receipt.issueDate(),
                receipt.imagePath(),
                receipt.paymentId()
        );
    }
}
