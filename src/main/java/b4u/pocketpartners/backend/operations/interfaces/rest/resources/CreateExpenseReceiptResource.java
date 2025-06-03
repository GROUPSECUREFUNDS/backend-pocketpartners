package b4u.pocketpartners.backend.operations.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateExpenseReceiptResource(
        @NotNull
        Long expenseId,
        @NotNull
        String name,
        @NotNull
        BigDecimal amount,
        @NotNull
        LocalDate issueDate,
        String imagePath
) {
}
