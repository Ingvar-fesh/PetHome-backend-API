package fr.epita.pethome.controllers;

import fr.epita.pethome.datamodel.Place;
import fr.epita.pethome.datamodel.dto.PlaceRequest;
import fr.epita.pethome.datamodel.dto.PlaceResponseDTO;
import fr.epita.pethome.services.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    /**
     * Public Endpoint: Get all validated places for the map.
     * Accessible by everyone (if SecurityConfig permits GET /api/places).
     */
    @GetMapping
    public List<PlaceResponseDTO> getAllPlaces() {
        return placeService.getAllValidatedPlaces();
    }

    /**
     * Protected Endpoint: Suggest a new place.
     * Requires valid JWT Token (checked by SecurityConfig), but we don't store the user.
     */
    @PostMapping
    public ResponseEntity<Place> addPlace(@RequestBody PlaceRequest request) {
        return ResponseEntity.ok(placeService.createPlace(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlace(@PathVariable("id") Integer id, @RequestBody PlaceRequest request) {
        try {
            return ResponseEntity.ok(placeService.updatePlace(id, request));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable("id") Integer id) {
        try {
            placeService.deletePlace(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<PlaceResponseDTO> searchPlacesByType(@RequestParam("type") String typeName) {
        return placeService.getPlacesByType(typeName);
    }

    @GetMapping("/nearby")
    public List<PlaceResponseDTO> searchPlacesNearby(
            @RequestParam("lat") Double latitude,
            @RequestParam("lng") Double longitude,
            @RequestParam(value = "radius", defaultValue = "10") Double radiusKm) {

        return placeService.getPlacesNearby(latitude, longitude, radiusKm);
    }


}