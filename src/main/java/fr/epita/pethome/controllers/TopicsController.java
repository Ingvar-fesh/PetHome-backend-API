package fr.epita.pethome.controllers;

import fr.epita.pethome.datamodel.Topic;
import fr.epita.pethome.repositories.TopicsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicsController {

    private final TopicsRepository topicsRepository;

    public TopicsController(TopicsRepository topicsRepository) {
        this.topicsRepository = topicsRepository;
    }

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicsRepository.findAll();
    }


    @PostMapping
    public ResponseEntity<?> createTopic(@RequestBody Topic topic) {
        if (topic.getName() == null || topic.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Topic name cannot be empty");
        }

        if (topicsRepository.findByName(topic.getName()).isPresent()) {
            return ResponseEntity.badRequest().body("Topic '" + topic.getName() + "' already exists");
        }

        Topic savedTopic = topicsRepository.save(topic);
        return ResponseEntity.ok(savedTopic);
    }
}