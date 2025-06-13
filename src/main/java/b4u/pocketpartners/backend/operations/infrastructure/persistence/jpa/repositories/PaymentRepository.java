package b4u.pocketpartners.backend.operations.infrastructure.persistence.jpa.repositories;

import b4u.pocketpartners.backend.groups.domain.model.valueobjects.GroupRole;
import b4u.pocketpartners.backend.operations.domain.model.aggregates.Payment;
import b4u.pocketpartners.backend.operations.domain.model.valueobjects.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByUserInformationId(Long userInformationId);
    List<Payment> findAllByExpenseId(Long expenseId);
    List<Payment> findAllByUserInformationIdAndStatus(Long userInformationId, PaymentStatus status);
    Optional<Payment> findByUserInformationIdAndExpenseId(Long userInformationId, Long expenseId);

    /**
     * Obtiene pagos de gastos donde el usuario es administrador del grupo relacionado.
     */
    @Query("""
    SELECT p 
    FROM Payment p 
    JOIN p.expense e 
    JOIN e.group g 
    JOIN GroupMember gm ON gm.group.id = g.id 
    WHERE gm.userInformation.id = :userInformationId 
      AND gm.role = :role AND p.userInformation.id != :userInformationId
""")    List<Payment> findIncomingPaymentsByUserInformation(@Param("userInformationId") Long userInformationId, @Param("role") GroupRole role);

}
