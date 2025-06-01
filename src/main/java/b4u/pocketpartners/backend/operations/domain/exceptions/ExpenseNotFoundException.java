package b4u.pocketpartners.backend.operations.domain.exceptions;

public class ExpenseNotFoundException extends RuntimeException {
    public ExpenseNotFoundException(Long expenseId) {
        super("Expense with ID " + expenseId + " not found.");
    }
}
