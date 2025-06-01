package b4u.pocketpartners.backend.operations.interfaces.rest.transform;

import b4u.pocketpartners.backend.operations.domain.model.entities.OcrReceipt;
import b4u.pocketpartners.backend.operations.domain.model.entities.Receipt;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.ReceiptOcrResource;
import b4u.pocketpartners.backend.operations.interfaces.rest.resources.ReceiptResource;

public class ReceiptResourceFromEntityAssembler {
    public static ReceiptOcrResource toResourceFromEntity(OcrReceipt receipt) {
        return new ReceiptOcrResource(
                receipt.getId(),
                receipt.getName(),
                receipt.getAmount().amount(),
                receipt.getIssueDate(),
                receipt.getImagePath(),
                receipt.getOcrData().dataFields()
        );
    }

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
