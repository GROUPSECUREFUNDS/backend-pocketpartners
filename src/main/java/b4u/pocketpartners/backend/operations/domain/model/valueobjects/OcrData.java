package b4u.pocketpartners.backend.operations.domain.model.valueobjects;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.Type;

import java.util.Map;

@Embeddable
public record OcrData(
        @Type(JsonType.class)
        @Column(columnDefinition = "json")
        Map<String,Object> dataFields
) {

}
