package bertcoscia.Epicode_W19D5.controllers;

import bertcoscia.Epicode_W19D5.entities.Participation;
import bertcoscia.Epicode_W19D5.entities.User;
import bertcoscia.Epicode_W19D5.exceptions.BadRequestException;
import bertcoscia.Epicode_W19D5.payloads.NewParticipationsDTO;
import bertcoscia.Epicode_W19D5.payloads.NewParticipationsRespDTO;
import bertcoscia.Epicode_W19D5.services.ParticipationsService;
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
@RequestMapping("/participations")
public class ParticipationsController {
    @Autowired
    ParticipationsService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewParticipationsRespDTO save(@RequestBody @Validated NewParticipationsDTO body, @AuthenticationPrincipal User currentAuthenticatedUser, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException(messages);
        } else {
            return new NewParticipationsRespDTO(this.service.save(body, currentAuthenticatedUser.getIdUser()).idParticipation());
        }
    }

    @GetMapping("/my-participations")
    public List<Participation> findByUserIdUser(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return this.service.findByUserIdUser(currentAuthenticatedUser.getIdUser());
    }

    @DeleteMapping("/{idParticipation}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority({'USER', 'EVENT_ORGANISER'})")
    public void deleteParticipation(@PathVariable UUID idParticipation) {
        this.service.findByIdAndDelete(idParticipation);
    }

    @GetMapping("/{idEvent}")
    @PreAuthorize("hasAuthority('EVENT_ORGANISER')")
    public List<Participation> findByEventIdEvent(@PathVariable UUID idEvent, @AuthenticationPrincipal User currentAuthenticatedUser) {
        return this.service.findByEventIdEvent(idEvent, currentAuthenticatedUser.getIdUser());
    }
}
