package bertcoscia.Epicode_W19D5.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Could not find record with id: " + id);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
