package bertcoscia.Epicode_W19D5.payloads;

import jakarta.validation.constraints.NotEmpty;

public record LoginDTO(
        @NotEmpty(message = "Email required")
        String email,
        @NotEmpty(message = "Password required")
        String password
) {
}