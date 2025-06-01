package b4u.pocketpartners.backend.operations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Map;

@Embeddable
public record OcrData(
        Map<String,Object> dataFields
) {
}
