package bertcoscia.Epicode_W19D5.payloads;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewParticipationsDTO(
        @NotNull(message = "Event id required")
        UUID idEvent
        /*@NotNull(message = "User id required")
        UUID idUser*/
        // Ho rimosso l'id dell'utente dal payload perché ho deciso che un utente può creare la partecipazione
        // solo per se stesso. Pertanto, l'id viene recuperato nel controller attraverso l'annotazione
        // @AuthenticationPrincipal
) {
}
