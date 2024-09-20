package bertcoscia.Epicode_W19D5.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "participation")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Participation {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private UUID idParticipation;

    @ManyToOne
    @JoinColumn(name = "id_event")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Participation(Event event, User user) {
        this.event = event;
        this.user = user;
    }
}
