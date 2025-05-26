package b4u.pocketpartners.backend.operations.interfaces.rest.transform;

import b4u.pocketpartners.backend.operations.domain.model.entities.Receipt;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.ReceiptResource;

public class ReceiptResourceFromEntityAssembler {
    public static ReceiptResource toResourceFromEntity(Receipt receipt){
        return new ReceiptResource(
                receipt.getId(),
                receipt.getName(),
                receipt.getAmount().amount(),
                receipt.getIssueDate(),
                receipt.getImagePath()
        );
    }
}
