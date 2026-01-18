package fr.epita.pethome.controllers;

import fr.epita.pethome.datamodel.LoginRequest;
import fr.epita.pethome.datamodel.User;
import fr.epita.pethome.security.JwtUtil;
import fr.epita.pethome.services.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UsersController(UsersService usersService,
                           AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil) {
        this.usersService = usersService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth")
    public String login(@RequestBody LoginRequest loginRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(loginRequest.getUsername());
        } else {
            throw new RuntimeException("Invalid Access");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(usersService.createUser(user));
    }

    @GetMapping
    public List<User> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        return usersService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody User userDetails) {
        try {
            return ResponseEntity.ok(usersService.updateUser(id, userDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
