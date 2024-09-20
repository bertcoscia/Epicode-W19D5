package bertcoscia.Epicode_W19D5.payloads;

import jakarta.validation.constraints.*;

public record NewUsersDTO(
        @NotEmpty(message = "Name require")
        String name,
        @NotEmpty(message = "Cognome required")
        String surname,
        @NotNull(message = "Email required")
        @Email(message = "Insert a valid email")
        String email,
        @NotEmpty(message = "Password required")
        String password,
        @NotEmpty(message = "Role required")
        String role
) {
}
