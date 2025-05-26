package b4u.pocketpartners.backend.operations.infrastructure.persistence.jpa.repositories;

import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.domain.model.entities.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    public List<Receipt> findByPayment(Payment payment);
    public Optional<Receipt> findById(Long receiptId);
}
