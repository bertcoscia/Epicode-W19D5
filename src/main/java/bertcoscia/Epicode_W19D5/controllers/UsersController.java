package bertcoscia.Epicode_W19D5.controllers;

import bertcoscia.Epicode_W19D5.entities.User;
import bertcoscia.Epicode_W19D5.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersService service;

    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody User body) {
        return this.service.findByIdAndUpdate(currentAuthenticatedUser.getIdUser(), body);
    }

    @GetMapping
    public List<User> findAll() {
        return this.service.findAll();
    }

    @GetMapping("/{idUser}")
    public User findById(@PathVariable UUID idUser) {
        return this.service.findById(idUser);
    }



}
