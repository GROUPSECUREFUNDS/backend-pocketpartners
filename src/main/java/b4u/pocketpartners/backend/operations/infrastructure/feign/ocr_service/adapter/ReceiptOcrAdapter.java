package b4u.pocketpartners.backend.operations.infrastructure.feign.ocr_service.adapter;

import b4u.pocketpartners.backend.operations.domain.model.entities.OcrReceipt;
import b4u.pocketpartners.backend.operations.domain.ports.out.ReceiptOcrPort;
import b4u.pocketpartners.backend.operations.infrastructure.feign.ocr_service.client.ReceiptOcrClient;
import b4u.pocketpartners.backend.operations.infrastructure.feign.ocr_service.mapper.ReceiptOcrMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReceiptOcrAdapter implements ReceiptOcrPort {
    private final  ReceiptOcrClient ocrServiceClient;

    @Override
    public OcrReceipt processReceiptImage(String imagePath) {
        var request = ReceiptOcrMapper.mapToRequest(imagePath);
        try {
            var response = ocrServiceClient.ocrReceiptForImagePath(request);
            return ReceiptOcrMapper.mapToEntity(response);
        } catch (Exception e) {
            // Handle exceptions appropriately, e.g., log the error or rethrow a custom exception
            throw new RuntimeException("Failed to process receipt image for OCR", e);
        }
    }
}
