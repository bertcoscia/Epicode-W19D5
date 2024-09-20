package bertcoscia.Epicode_W19D5.payloads;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewParticipationsDTO(
        @NotNull(message = "Event id required")
        UUID idEvent,
        @NotNull(message = "User id required")
        UUID idUser
) {
}
