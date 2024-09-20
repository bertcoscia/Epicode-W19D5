package bertcoscia.Epicode_W19D5.controllers;

import bertcoscia.Epicode_W19D5.entities.Event;
import bertcoscia.Epicode_W19D5.entities.User;
import bertcoscia.Epicode_W19D5.exceptions.BadRequestException;
import bertcoscia.Epicode_W19D5.payloads.NewEventsDTO;
import bertcoscia.Epicode_W19D5.payloads.NewEventsRespDTO;
import bertcoscia.Epicode_W19D5.services.EventsService;
import bertcoscia.Epicode_W19D5.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventsController {
    @Autowired
    EventsService service;

    @Autowired
    UsersService usersService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('EVENT_ORGANISER')")
    public NewEventsRespDTO save(@RequestBody @Validated NewEventsDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException(messages);
        } else {
            return new NewEventsRespDTO(this.service.save(body).getIdEvent());
        }
    }

    @GetMapping
    public Event findById(UUID id) {
        return this.service.findById(id);
    }

    @GetMapping("/my-events")
    @PreAuthorize("hasAuthority('EVENT_ORGANISER')")
    public List<Event> findMyEvents(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return this.service.findByUserId(currentAuthenticatedUser.getIdUser());
    }

    @PutMapping("/{idEvent}")
    @PreAuthorize("hasAuthority('EVENT_ORGANISER')")
    public Event findByIdAndUpdate(UUID idEvent, @AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody @Validated Event body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException(messages);
        }
            return this.service.findByIdAndUpdate(currentAuthenticatedUser.getIdUser(), idEvent, body);
    }


}
