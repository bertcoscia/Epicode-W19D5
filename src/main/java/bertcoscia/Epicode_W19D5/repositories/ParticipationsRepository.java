package bertcoscia.Epicode_W19D5.repositories;

import bertcoscia.Epicode_W19D5.entities.Event;
import bertcoscia.Epicode_W19D5.entities.Participation;
import bertcoscia.Epicode_W19D5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParticipationsRepository extends JpaRepository<Participation, UUID> {

    boolean existsByEventAndUser(Event event, User user);

    Optional<List<Participation>> findByUserIdUser(UUID id);

    Optional<List<Participation>> findByEventIdEvent(UUID id);
}
