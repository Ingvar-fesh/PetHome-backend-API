package fr.epita.pethome.services;

import fr.epita.pethome.datamodel.Location;
import fr.epita.pethome.datamodel.Place;
import fr.epita.pethome.datamodel.PlaceType;
import fr.epita.pethome.datamodel.dto.PlaceRequest;
import fr.epita.pethome.datamodel.dto.PlaceResponseDTO;
import fr.epita.pethome.repositories.PlaceRepository;
import fr.epita.pethome.repositories.PlaceTypeRepository;
import fr.epita.pethome.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceTypeRepository placeTypeRepository;

    public PlaceService(PlaceRepository placeRepository,
                        PlaceTypeRepository placeTypeRepository) {
        this.placeRepository = placeRepository;
        this.placeTypeRepository = placeTypeRepository;
    }

    @Transactional
    public Place createPlace(PlaceRequest request) {
        PlaceType type = placeTypeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new RuntimeException("Place Type not found"));

        Location location = new Location(request.getLatitude(), request.getLongitude());

        Place place = new Place(
                request.getName(),
                request.getAddress(),
                type,
                location
        );
        place.setDescription(request.getDescription());
        place.setValidated(true);

        return placeRepository.save(place);
    }

    @Transactional
    public Place updatePlace(Integer id, PlaceRequest request) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Place not found"));

        if (request.getName() != null) place.setName(request.getName());
        if (request.getAddress() != null) place.setAddress(request.getAddress());
        if (request.getDescription() != null) place.setDescription(request.getDescription());

        if (request.getTypeId() != null) {
            PlaceType type = placeTypeRepository.findById(request.getTypeId())
                    .orElseThrow(() -> new RuntimeException("Place Type not found"));
            place.setType(type);
        }

        if (request.getLatitude() != null) place.getLocation().setLatitude(request.getLatitude());
        if (request.getLongitude() != null) place.getLocation().setLongitude(request.getLongitude());

        return placeRepository.save(place);
    }

    public void deletePlace(Integer id) {
        if (!placeRepository.existsById(id)) {
            throw new RuntimeException("Place not found");
        }
        placeRepository.deleteById(id);
    }

    public List<PlaceResponseDTO> getAllValidatedPlaces() {
        return placeRepository.findByIsValidatedTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PlaceResponseDTO> getPlacesByType(String typeName) {
        return placeRepository.findByType_NameAndIsValidatedTrue(typeName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PlaceResponseDTO> getPlacesNearby(Double userLat, Double userLng, Double radiusKm) {
        List<Place> allPlaces = placeRepository.findByIsValidatedTrue();

        return allPlaces.stream()
                .filter(place -> {
                    double distance = calculateDistance(
                            userLat, userLng,
                            place.getLocation().getLatitude(),
                            place.getLocation().getLongitude()
                    );
                    return distance <= radiusKm;
                })
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371; // Radius in KM

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    private PlaceResponseDTO convertToDTO(Place place) {
        return new PlaceResponseDTO(
                place.getId(),
                place.getName(),
                place.getAddress(),
                place.getDescription(),
                place.getLocation().getLatitude(),
                place.getLocation().getLongitude()
        );
    }
}
