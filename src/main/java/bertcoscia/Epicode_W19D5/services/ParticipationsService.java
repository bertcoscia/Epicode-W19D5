package bertcoscia.Epicode_W19D5.services;

import bertcoscia.Epicode_W19D5.entities.Event;
import bertcoscia.Epicode_W19D5.entities.Participation;
import bertcoscia.Epicode_W19D5.entities.User;
import bertcoscia.Epicode_W19D5.exceptions.BadRequestException;
import bertcoscia.Epicode_W19D5.exceptions.NotFoundException;
import bertcoscia.Epicode_W19D5.payloads.NewParticipationsDTO;
import bertcoscia.Epicode_W19D5.repositories.ParticipationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipationsService {
    @Autowired
    ParticipationsRepository repository;

    @Autowired
    UsersService usersService;

    @Autowired
    EventsService eventsService;

    public Participation save(NewParticipationsDTO body) {
        Event eventFound = this.eventsService.findById(body.idEvent());
        User userFound = this.usersService.findById(body.idUser());
        if (this.repository.existsByEventAndUser(eventFound, userFound)) throw new BadRequestException(userFound.getEmail() + "already has a reservation for the event " + eventFound.getName());
        return this.repository.save(new Participation(eventFound, userFound));
    }

    public List<Participation> findAll() {
        return this.repository.findAll();
    }

    public Participation findById(UUID id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(UUID id) {
        Participation found = this.findById(id);
        this.repository.delete(found);
    }

}
