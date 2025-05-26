package b4u.pocketpartners.backend.operations.infrastructure.ocr.tesseract.services;

import b4u.pocketpartners.backend.operations.infrastructure.ocr.tesseract.TesseractService;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class TesseractServiceImpl implements TesseractService {
    private Tesseract tesseract;
    private String dataPath;
    private List<String> languages;

    public TesseractServiceImpl() throws IOException, URISyntaxException {
        tesseract = new Tesseract();
        languages = List.of("spa","eng");
        dataPath = new File(getClass().getClassLoader().getResource("tessdata").toURI()).getAbsolutePath();

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
