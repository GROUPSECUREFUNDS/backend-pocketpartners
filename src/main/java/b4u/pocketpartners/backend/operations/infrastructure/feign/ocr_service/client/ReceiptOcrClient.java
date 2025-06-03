package b4u.pocketpartners.backend.operations.infrastructure.feign.ocr_service.client;

import b4u.pocketpartners.backend.operations.infrastructure.feign.ocr_service.dto.ReceiptOcrRequest;
import b4u.pocketpartners.backend.operations.infrastructure.feign.ocr_service.dto.ReceiptOcrResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "receipt-ocr-client", url = "${feign.ocr.service.url}")
public interface ReceiptOcrClient {

    @PostMapping("/receipt-ocr")
    ReceiptOcrResponse ocrReceiptForImagePath(ReceiptOcrRequest request);
}
