package b4u.pocketpartners.backend.operations.interfaces.rest.transform;

import b4u.pocketpartners.backend.operations.domain.model.commands.CreateReceiptCommand;
import b4u.pocketpartners.backend.operations.domain.model.entities.Receipt;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.CreateReceiptResource;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.ReceiptResource;

public class CreateReceiptCommandFromResourceAssembler {
    public static CreateReceiptCommand toCommandFromResource(CreateReceiptResource receipt){
        return new CreateReceiptCommand(
                receipt.name(),
                receipt.amount(),
                receipt.issueDate(),
                receipt.imagePath(),
                receipt.paymentId()
        );
    }
}
