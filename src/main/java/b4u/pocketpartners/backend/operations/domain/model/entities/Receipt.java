package b4u.pocketpartners.backend.operations.domain.model.entities;

import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.domain.model.valueobjects.Amount;
import b4u.pocketpartners.backend.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Receipt extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Amount amount;
    private String name;
    private LocalDate issueDate;
    private String imagePath;
    private Boolean isActive = true;

    public Receipt(){};

    public Receipt(String name,Amount amount, LocalDate issueDate, String imagePath){
        this.name = name;
        this.amount = amount;
        this.issueDate = issueDate;
        this.imagePath = imagePath;
    };

}
