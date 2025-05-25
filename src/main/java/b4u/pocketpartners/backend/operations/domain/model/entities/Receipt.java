package b4u.pocketpartners.backend.operations.domain.model.entities;

import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.domain.model.valueobjects.Amount;
import b4u.pocketpartners.backend.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Receipt extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Amount amount;
    private String name;
    private LocalDate date;
    private String imageId;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
