package b4u.pocketpartners.backend.operations.application.internal.queryservices;

import b4u.pocketpartners.backend.operations.domain.exceptions.PaymentNotFoundException;
import b4u.pocketpartners.backend.operations.domain.exceptions.ReceiptImageProcessingException;
import b4u.pocketpartners.backend.operations.domain.exceptions.ReceiptNotFoundException;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.domain.model.entities.Receipt;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetAllReceiptsByPaymentIdQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetPaymentByIdQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetReceiptByIdQuery;
import b4u.pocketpartners.backend.operations.domain.model.queries.GetReceiptTextByIdQuery;
import b4u.pocketpartners.backend.operations.domain.services.PaymentQueryService;
import b4u.pocketpartners.backend.operations.domain.services.ReceiptQueryService;
import b4u.pocketpartners.backend.operations.infrastructure.ocr.tesseract.TesseractService;
import b4u.pocketpartners.backend.operations.infrastructure.persistence.jpa.repositories.ReceiptRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.text.html.Option;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReceiptQueryServiceImpl implements ReceiptQueryService {
    private ReceiptRepository receiptRepository;
    private PaymentQueryService paymentQueryService;
    private TesseractService tesseractService;

    @Override
    public List<Receipt> handle(GetAllReceiptsByPaymentIdQuery query) {
        Optional<Payment> payment = paymentQueryService.handle(new GetPaymentByIdQuery(query.paymentId()));

        if(payment.isEmpty())
            throw new PaymentNotFoundException(query.paymentId());

        return receiptRepository.findByPayment(payment.get());
    }

    @Override
    public Optional<Receipt> handle(GetReceiptByIdQuery query) {
        return receiptRepository.findById(query.receiptId());
    }

    @Override
    public String handle(GetReceiptTextByIdQuery query) throws IOException {
        Receipt receipt = receiptRepository.findById(query.receiptId())
                .orElseThrow(()-> new ReceiptNotFoundException(query.receiptId()));

        //Solo se debe cambiar el url en caso de cambiar el system file
        URL urlReceiptImage = new URL("http://localhost:8080/api/v1/images/"+receipt.getImagePath());
        BufferedImage receiptImage = ImageIO.read(urlReceiptImage);

        try{
            String textReceipt = tesseractService.doOcrFromBufferedImage(receiptImage);
            return textReceipt;
        }catch (Exception ex){
            throw new ReceiptImageProcessingException("the text cannot be extracted from this receipt");
        }
    }


}
