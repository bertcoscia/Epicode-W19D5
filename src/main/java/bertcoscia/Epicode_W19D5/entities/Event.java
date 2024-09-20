package bertcoscia.Epicode_W19D5.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "events")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Event {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private UUID idEvent;
    private String name;
    private LocalDate date;
    private String description;
    private String location;
    private int maxPax;
    private int numParticipants;
    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "id_organizer")
    private User user;

    public Event(String name, LocalDate date, String description, String location, int maxPax, User user) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.location = location;
        this.maxPax = maxPax;
        this.user = user;
        this.numParticipants = 0;
    }
}
