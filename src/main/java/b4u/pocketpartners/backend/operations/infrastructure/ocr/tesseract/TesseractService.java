package b4u.pocketpartners.backend.operations.infrastructure.ocr.tesseract;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TesseractService {
    String doOcrFromBufferedImage(BufferedImage image);
}
