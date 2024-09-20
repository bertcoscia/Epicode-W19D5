package bertcoscia.Epicode_W19D5.services;

import bertcoscia.Epicode_W19D5.entities.Event;
import bertcoscia.Epicode_W19D5.entities.Participation;
import bertcoscia.Epicode_W19D5.entities.User;
import bertcoscia.Epicode_W19D5.exceptions.BadRequestException;
import bertcoscia.Epicode_W19D5.exceptions.NotFoundException;
import bertcoscia.Epicode_W19D5.exceptions.UnauthorizedException;
import bertcoscia.Epicode_W19D5.payloads.NewParticipationsDTO;
import bertcoscia.Epicode_W19D5.payloads.NewParticipationsRespDTO;
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

    public NewParticipationsRespDTO save(NewParticipationsDTO body, UUID idUser) {
        Event eventFound = this.eventsService.findById(body.idEvent());
        User userFound = this.usersService.findById(idUser);
        if (this.repository.existsByEventAndUser(eventFound, userFound)) throw new BadRequestException(userFound.getEmail() + " has already a reservation for the event " + eventFound.getName());
        if (eventFound.getNumParticipants() == eventFound.getMaxPax()) throw new BadRequestException("The event " + eventFound.getName() + " is already sold out.");
        Participation newParticipation = new Participation(eventFound, userFound);
        this.repository.save(newParticipation);
        eventFound.setNumParticipants(eventFound.getNumParticipants() + 1);
        this.eventsService.findByIdAndUpdate(eventFound.getUser().getIdUser(), eventFound.getIdEvent(), eventFound);
        return new NewParticipationsRespDTO(newParticipation.getIdParticipation());
    }

    public List<Participation> findAll() {
        return this.repository.findAll();
    }

    public Participation findById(UUID id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(UUID id) {
        Participation participationFound = this.findById(id);
        Event eventFound = this.eventsService.findById(participationFound.getEvent().getIdEvent());
        eventFound.setNumParticipants(eventFound.getNumParticipants() - 1);
        this.repository.delete(participationFound);
        this.eventsService.findByIdAndUpdate(eventFound.getUser().getIdUser(), eventFound.getIdEvent(), eventFound);
    }

    public List<Participation> findByUserIdUser(UUID id) {
        return this.repository.findByUserIdUser(id).orElseThrow(() -> new NotFoundException("You have no participations scheduled"));
    }

    public List<Participation> findByEventIdEvent(UUID idEvent, UUID idUser) {
        Event found = this.eventsService.findById(idEvent);
        if (!found.getUser().getIdUser().equals(idUser)) throw new UnauthorizedException("You do not have permission to complete the requested action.");
        return this.repository.findByEventIdEvent(idEvent).orElseThrow(() -> new NotFoundException("There are no participations for the selected event"));
    }

}
