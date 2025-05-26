package b4u.pocketpartners.backend.operations.domain.model.commands;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateReceiptCommand(
        String name,
        BigDecimal amount,
        LocalDate issueDate,
        String imagePath,
        Long paymentId ) {
}
