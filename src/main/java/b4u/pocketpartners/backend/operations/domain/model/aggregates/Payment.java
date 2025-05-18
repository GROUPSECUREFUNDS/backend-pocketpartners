package b4u.pocketpartners.backend.operations.domain.model.aggregates;

import b4u.pocketpartners.backend.operations.domain.model.valueobjects.Amount;
import b4u.pocketpartners.backend.operations.domain.model.valueobjects.Description;
import b4u.pocketpartners.backend.operations.domain.model.valueobjects.DueDate;
import b4u.pocketpartners.backend.operations.domain.model.valueobjects.PaymentStatus;
import b4u.pocketpartners.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import b4u.pocketpartners.backend.users.domain.model.aggregates.UserInformation;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
public class Payment extends AuditableAbstractAggregateRoot<Payment> {

    @Getter
    @Embedded
    private Description description;

    @Getter
    @Embedded
    private Amount amount;

    private PaymentStatus status;

    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInformation userInformation;

    @Getter
    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

    public Payment() {}

    public Payment(String description, BigDecimal amount, UserInformation userInformation, Expense expense) {
        this.description = new Description(description);
        this.amount = new Amount(amount);
        this.status = PaymentStatus.PENDING;
        this.userInformation = userInformation;
        this.expense = expense;
    }

    public Payment UpdateInformation(String newDescription, BigDecimal newAmount){
        this.description = new Description(newDescription);
        this.amount = new Amount(newAmount);
        return this;
    }

    public void completePayment(){
        this.status = PaymentStatus.COMPLETED;
    }

    public String getDescription() {return description.getDescription();}

    public BigDecimal getAmount(){return amount.getAmount();}

    public String getStatus(){return this.status.name().toLowerCase();}

}

