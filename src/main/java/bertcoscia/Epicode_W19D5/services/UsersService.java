package bertcoscia.Epicode_W19D5.services;

import bertcoscia.Epicode_W19D5.entities.User;
import bertcoscia.Epicode_W19D5.enums.Role;
import bertcoscia.Epicode_W19D5.exceptions.BadRequestException;
import bertcoscia.Epicode_W19D5.exceptions.NotFoundException;
import bertcoscia.Epicode_W19D5.payloads.NewUsersDTO;
import bertcoscia.Epicode_W19D5.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    UsersRepository repository;

    @Autowired
    PasswordEncoder bcrypt;

    public User save(NewUsersDTO body) {
        if (this.repository.existsByEmail(body.email())) throw new BadRequestException("Email " + body.email() + " already used");
        Role newUserRole;
        if (body.role().equalsIgnoreCase("user")) {
            newUserRole = Role.USER;
        } else if (body.role().equalsIgnoreCase("event_organiser")) {
            newUserRole = Role.EVENT_ORGANISER;
        } else {
            throw new BadRequestException("Value of 'role' is not valid, choose one between USER and EVENT_ORGANISER");
        }
        User newUser = new User(body.name(), body.surname(), body.email(), bcrypt.encode(body.password()), newUserRole);
        return this.repository.save(newUser);
    }

    public List<User> findAll() {
        return this.repository.findAll();
    }

    public User findById(UUID id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByEmail(String email) {
        return this.repository.findByEmail(email).orElseThrow(() -> new NotFoundException("Could not find record with email: " + email));
    }

    public User findByIdAndUpdate(UUID id, User body) {
        this.repository.findByEmail(body.getEmail()).ifPresent(
                user -> {
                    throw new BadRequestException("Email " + body.getEmail() + " already used");
                }
        );
        User found = this.findById(id);
        found.setEmail(body.getEmail());
        found.setName(body.getName());
        found.setSurname(body.getSurname());
        return this.repository.save(found);
    }
}
