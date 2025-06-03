package b4u.pocketpartners.backend.operations.domain.model.entities;

import b4u.pocketpartners.backend.operations.domain.model.valueobjects.Amount;
import b4u.pocketpartners.backend.operations.domain.model.valueobjects.OcrData;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Getter
@Setter
public class OcrReceipt extends Receipt{
    @Embedded
    private OcrData ocrData;

    @OneToOne
    @JoinColumn(name = "receipt_id")
    private Receipt originalReceipt;

    public OcrReceipt() {}
    public OcrReceipt(
            String name,
            String receiptNumber,
            Amount amount,
            LocalDate issueDate,
            String imagePath,
            OcrData ocrData
    ) {
        super(name, amount, issueDate, imagePath,receiptNumber);
        this.ocrData = ocrData;
    }


    public void assignToOriginalReceipt(Receipt originalReceipt) {
        this.originalReceipt = originalReceipt;
    }
}
