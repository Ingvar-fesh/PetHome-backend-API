package fr.epita.pethome.config;

import fr.epita.pethome.datamodel.*;
import fr.epita.pethome.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    public CommandLineRunner initDatabase(UsersRepository usersRepository,
                                          TopicsRepository topicsRepository,
                                          PetTypeRepository petTypeRepository,
                                          PetRepository petRepository,
                                          PostsRepository postsRepository,
                                          CommentsRepository commentsRepository,
                                          PlaceRepository placeRepository,
                                          PlaceTypeRepository placeTypeRepository,
                                          PasswordEncoder passwordEncoder) {
        return args -> {

            if (placeTypeRepository.count() == 0) {
                // Using generic Google Map marker icons for testing
                placeTypeRepository.save(new PlaceType("VET"));
                placeTypeRepository.save(new PlaceType("PARK"));
                placeTypeRepository.save(new PlaceType("SHOP"));
                System.out.println("Place Types created.");
            }

            PlaceType vetType = placeTypeRepository.findAll().
                    stream().filter(t -> t.getName().equals("VET")).findFirst().get();
            PlaceType parkType = placeTypeRepository.findAll().
                    stream().filter(t -> t.getName().equals("PARK")).findFirst().get();
            PlaceType shopType = placeTypeRepository.findAll().
                    stream().filter(t -> t.getName().equals("SHOP")).findFirst().get();

            if (placeRepository.count() == 0) {
                // Place 1: A Vet in Paris (Near Eiffel Tower)
                Location loc1 = new Location(48.8584, 2.2945);
                Place p1 = new Place("Paris Vet Clinic", "5 Avenue Anatole France, Paris", vetType, loc1);
                p1.setDescription("24/7 Emergency Vet Services");
                p1.setValidated(true); // Important: Must be true to show on map
                placeRepository.save(p1);

                // Place 2: A Park (Tuileries Garden)
                Location loc2 = new Location(48.8635, 2.3275);
                Place p2 = new Place("Jardin des Tuileries", "Place de la Concorde, Paris", parkType, loc2);
                p2.setDescription("Great park for walking dogs on leash.");
                p2.setValidated(true);
                placeRepository.save(p2);

                // Place 3: A Pet Shop (Le Marais)
                Location loc3 = new Location(48.8570, 2.3522);
                Place p3 = new Place("Le Marais Pet Shop", "Rue de Rivoli, Paris", shopType, loc3);
                p3.setDescription("Premium food and toys.");
                p3.setValidated(true);
                placeRepository.save(p3);

                System.out.println("Sample Places created in Paris.");
            }


            // Create Default Topics
            List<String> defaultTopics = Arrays.asList("Health", "Training", "Nutrition", "General Chat");
            for (String topicName : defaultTopics) {
                if (topicsRepository.findByName(topicName).isEmpty()) {
                    topicsRepository.save(new Topic(topicName));
                }
            }
            Topic healthTopic = topicsRepository.findByName("Health").get();
            Topic trainingTopic = topicsRepository.findByName("Training").get();

            // Create Pet Types
            List<String> petTypes = Arrays.asList("Dog", "Cat", "Bird", "Reptile");
            for (String typeName : petTypes) {
                if (petTypeRepository.findAll().stream().noneMatch(t -> t.getName().equals(typeName))) {
                    petTypeRepository.save(new PetType(typeName));
                }
            }
            PetType dogType = petTypeRepository.findAll().stream().filter(t -> t.getName().equals("Dog")).findFirst().get();
            PetType catType = petTypeRepository.findAll().stream().filter(t -> t.getName().equals("Cat")).findFirst().get();

            // Create Main User (Admin)
            User admin;
            if (usersRepository.findByUsername("admin_test").isEmpty()) {
                admin = new User();
                admin.setUsername("admin_test");
                admin.setPassword(passwordEncoder.encode("securepass"));
                admin.setEmail("admin@pethome.com");
                admin = usersRepository.save(admin);
                System.out.println("User 'admin_test' created.");
            } else {
                admin = usersRepository.findByUsername("admin_test").get();
            }

            // Create Secondary User (Commenter)
            User bob;
            if (usersRepository.findByUsername("commenter_bob").isEmpty()) {
                bob = new User();
                bob.setUsername("commenter_bob");
                bob.setPassword(passwordEncoder.encode("bobpass"));
                bob.setEmail("bob@example.com");
                bob = usersRepository.save(bob);
                System.out.println("User 'commenter_bob' created.");
            } else {
                bob = usersRepository.findByUsername("commenter_bob").get();
            }

            // Create Pets for Admin
            if (petRepository.findByOwnerId(admin.getId()).isEmpty()) {
                petRepository.save(new Pet("Rex", null, admin, dogType));
                petRepository.save(new Pet("Whiskers", null, admin, catType));
                System.out.println("Pets created for admin.");
            }

            // Create Posts for Admin
            if (postsRepository.findByAuthorId(admin.getId()).isEmpty()) {
                Post post1 = new Post();
                post1.setTitle("Best diet for older dogs?");
                post1.setBody("My dog Rex is turning 10 soon. Any advice on nutrition?");
                post1.setAuthor(admin);
                post1.setTopic(healthTopic);
                postsRepository.save(post1);

                Post post2 = new Post();
                post2.setTitle("How to stop cat scratching furniture?");
                post2.setBody("Whiskers is destroying my sofa! Help!");
                post2.setAuthor(admin);
                post2.setTopic(trainingTopic);
                postsRepository.save(post2);

                System.out.println("Posts created for admin.");

                // Create Comments from Bob on Admin's posts
                Comment c1 = new Comment();
                c1.setText("Try switching to a senior dog food blend, high in fiber.");
                c1.setAuthor(bob);
                c1.setPost(post1);
                commentsRepository.save(c1);

                Comment c2 = new Comment();
                c2.setText("Get a scratching post and use catnip spray on it!");
                c2.setAuthor(bob);
                c2.setPost(post2);
                commentsRepository.save(c2);

                System.out.println("Comments created from Bob.");
            }
        };
    }
}