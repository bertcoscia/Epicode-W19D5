package bertcoscia.Epicode_W19D5.controllers;

import bertcoscia.Epicode_W19D5.exceptions.BadRequestException;
import bertcoscia.Epicode_W19D5.payloads.LoginDTO;
import bertcoscia.Epicode_W19D5.payloads.LoginRespDTO;
import bertcoscia.Epicode_W19D5.payloads.NewUsersDTO;
import bertcoscia.Epicode_W19D5.payloads.NewUsersRespDTO;
import bertcoscia.Epicode_W19D5.services.AuthService;
import bertcoscia.Epicode_W19D5.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService service;

    @Autowired
    UsersService usersService;

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody @Validated LoginDTO body) {
        return new LoginRespDTO(this.service.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUsersRespDTO save(@RequestBody @Validated NewUsersDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException(messages);
        } else {
            return new NewUsersRespDTO(this.usersService.save(body).getIdUser());
        }
    }
}