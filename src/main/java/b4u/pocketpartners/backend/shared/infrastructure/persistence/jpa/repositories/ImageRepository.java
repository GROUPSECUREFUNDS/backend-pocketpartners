package b4u.pocketpartners.backend.shared.infrastructure.persistence.jpa.repositories;

import b4u.pocketpartners.backend.shared.domain.model.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
}
