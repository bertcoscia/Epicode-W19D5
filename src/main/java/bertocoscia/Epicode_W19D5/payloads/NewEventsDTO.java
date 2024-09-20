package bertcoscia.Epicode_W19D5.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record NewEventsDTO(
        @NotEmpty(message = "Name required")
        String name,
        @NotNull(message = "Date required")
        LocalDate date,
        @NotEmpty(message = "Description required")
        String description,
        @NotEmpty(message = "Location required")
        String location,
        @NotNull(message = "Max number of participants required")
        int maxPax,
        @NotEmpty(message = "Organiser id required")
        UUID idUser
) {
}
