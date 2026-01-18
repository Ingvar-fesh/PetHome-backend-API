package fr.epita.pethome.config;

import fr.epita.pethome.datamodel.Topic;
import fr.epita.pethome.datamodel.User;
import fr.epita.pethome.repositories.TopicsRepository;
import fr.epita.pethome.repositories.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(UsersRepository usersRepository,
                                          TopicsRepository topicsRepository,
                                          PasswordEncoder passwordEncoder) {
        return args -> {
            if (usersRepository.findByUsername("admin_test").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin_test");
                admin.setPassword(passwordEncoder.encode("securepass"));
                admin.setEmail("admin@pethome.com");
                usersRepository.save(admin);
                System.out.println("Admin user created.");
            }

            List<String> defaultTopics = Arrays.asList("Health", "Training", "Nutrition", "General Chat");

            for (String topicName : defaultTopics) {
                if (topicsRepository.findByName(topicName).isEmpty()) {
                    topicsRepository.save(new Topic(topicName));
                    System.out.println("Topic created: " + topicName);
                }
            }
        };
    }
}