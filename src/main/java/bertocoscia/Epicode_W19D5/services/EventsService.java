package bertcoscia.Epicode_W19D5.services;

import bertcoscia.Epicode_W19D5.entities.Event;
import bertcoscia.Epicode_W19D5.entities.User;
import bertcoscia.Epicode_W19D5.exceptions.BadRequestException;
import bertcoscia.Epicode_W19D5.exceptions.NotFoundException;
import bertcoscia.Epicode_W19D5.exceptions.UnauthorizedException;
import bertcoscia.Epicode_W19D5.payloads.NewEventsDTO;
import bertcoscia.Epicode_W19D5.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventsService {
    @Autowired
    EventsRepository repository;

    @Autowired
    UsersService usersService;

    public Event save(NewEventsDTO body) {
        if (this.repository.existsByDateAndLocation(body.date(), body.location())) throw new BadRequestException("There is already a scheduled event at " + body.location() + " for the day " + body.date());
        User userFound = this.usersService.findById(body.idUser());
        Event newEvent = new Event(body.name(), body.date(), body.description(), body.location(), body.maxPax(), userFound);
        return this.repository.save(newEvent);
    }

    public List<Event> findAll() {
        return this.repository.findAll();
    }

    public Event findById(UUID id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Event findByIdAndUpdate(UUID idUser, UUID idEvent, Event body) {
        Event found = this.findById(idEvent);
        if (!found.getUser().getIdUser().equals(idUser)) {
            throw new UnauthorizedException("You do not have the permission to edit this event");
        } else {
        found.setDate(body.getDate());
        found.setName(body.getName());
        found.setLocation(body.getLocation());
        found.setDescription(body.getDescription());
        found.setMaxPax(body.getMaxPax());
        return this.repository.save(found);
        }
    }

    public void findByIdAndDelete(UUID id) {
        Event found = this.findById(id);
        this.repository.delete(found);
    }

    public List<Event> findByUserId(UUID id) {
        return this.repository.findByUserId(id).orElseThrow(() -> new NotFoundException("There are no events"));
    }

}
