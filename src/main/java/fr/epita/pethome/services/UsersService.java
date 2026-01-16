package fr.epita.pethome.services;

import fr.epita.pethome.datamodel.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User createUser(User user) {
        return usersRepository.save(user);
    }

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return usersRepository.findById(id);
    }

    public User updateUser(Integer id, User userDetails) {
        return usersRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            if(userDetails.getPassword() != null) {
                user.setPassword(userDetails.getPassword());
            }
            return usersRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void deleteUser(Integer id) {
        usersRepository.deleteById(id);
    }
}