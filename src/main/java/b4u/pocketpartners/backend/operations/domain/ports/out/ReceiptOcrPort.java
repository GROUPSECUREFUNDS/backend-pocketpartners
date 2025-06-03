package b4u.pocketpartners.backend.operations.domain.ports.out;

import b4u.pocketpartners.backend.operations.domain.model.entities.OcrReceipt;

public interface ReceiptOcrPort {
    OcrReceipt processReceiptImage(String imagePath);
}
