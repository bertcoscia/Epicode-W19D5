package bertcoscia.Epicode_W19D5.repositories;

import bertcoscia.Epicode_W19D5.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventsRepository extends JpaRepository<Event, UUID> {

    boolean existsByName(String name);

    boolean existsByDateAndLocation(LocalDate date, String Location);

    Optional<List<Event>> findByUserId(UUID id);
}
