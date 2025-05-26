package b4u.pocketpartners.backend.operations.interfaces.rest.resources;


import java.math.BigDecimal;
import java.time.LocalDate;

public record ReceiptResource(
        Long id,
        String name,
        BigDecimal amount,
        LocalDate issueDate,
        String imagePath
) {
}
