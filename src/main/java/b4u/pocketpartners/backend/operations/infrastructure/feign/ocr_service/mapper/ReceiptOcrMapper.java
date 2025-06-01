package b4u.pocketpartners.backend.operations.infrastructure.feign.ocr_service.mapper;

import b4u.pocketpartners.backend.operations.domain.model.entities.OcrReceipt;
import b4u.pocketpartners.backend.operations.domain.model.valueobjects.Amount;
import b4u.pocketpartners.backend.operations.domain.model.valueobjects.OcrData;
import b4u.pocketpartners.backend.operations.infrastructure.feign.ocr_service.dto.ReceiptOcrRequest;
import b4u.pocketpartners.backend.operations.infrastructure.feign.ocr_service.dto.ReceiptOcrResponse;
import org.springframework.stereotype.Component;

public class ReceiptOcrMapper {
    static public OcrReceipt mapToEntity(ReceiptOcrResponse response){
        return new OcrReceipt(
                response.name(),
                response.receiptNumber(),
                new Amount(response.amount()),
                response.issueDate(),
                response.imagePath(),
                new OcrData(response.dataFields())
        );
    }
    static public ReceiptOcrRequest mapToRequest(String imagePath) {
        return new ReceiptOcrRequest(imagePath);
    }
}
