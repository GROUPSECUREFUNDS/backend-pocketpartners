package b4u.pocketpartners.backend.operations.infrastructure.ocr.tesseract.services;

import b4u.pocketpartners.backend.operations.infrastructure.ocr.tesseract.TesseractService;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class TesseractServiceImpl implements TesseractService {
    private Tesseract tesseract;
    private String dataPath;
    private List<String> languages;

    public TesseractServiceImpl() throws IOException {
        tesseract = new Tesseract();
        languages = List.of("spa","eng");
        dataPath = "b4u/pocketpartners/backend/operations/infrastructure/ocr/tesseract/tessdata";

        tesseract.setDatapath(dataPath);
        tesseract.setLanguage(String.join("+",languages));
        tesseract.setPageSegMode(1);
    }

    @Override
    public String doOcrFromBufferedImage(BufferedImage bufferedImage) {
        try {
            if (bufferedImage == null) {
                throw new RuntimeException("El archivo no es una imagen válida");
            }
            return tesseract.doOCR(bufferedImage);

        }catch (TesseractException e) {
            throw new RuntimeException("Error en OCR: " + e.getMessage());
        }

    }
}
