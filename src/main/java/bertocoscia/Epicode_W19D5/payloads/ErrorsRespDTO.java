package bertcoscia.Epicode_W19D5.payloads;

import java.time.LocalDateTime;

public record ErrorsRespDTO(String message, LocalDateTime timestamp) {
}
