package bertcoscia.Epicode_W19D5.services;

import bertcoscia.Epicode_W19D5.entities.User;
import bertcoscia.Epicode_W19D5.exceptions.UnauthorizedException;
import bertcoscia.Epicode_W19D5.payloads.LoginDTO;
import bertcoscia.Epicode_W19D5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsersService usersService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        User found = this.usersService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            return this.jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Wrong email and/or password");
        }
    }


}
