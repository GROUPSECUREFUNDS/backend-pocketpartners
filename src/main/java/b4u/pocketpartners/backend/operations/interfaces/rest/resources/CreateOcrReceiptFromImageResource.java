package b4u.pocketpartners.backend.operations.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateOcrReceiptFromImageResource(
        @NotNull
        String imageId
) {
}
