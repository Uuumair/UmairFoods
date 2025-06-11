package com.umair.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.umair.dto.RestaurantDto;
import com.umair.model.Address;
import com.umair.model.ContactInformation;
import com.umair.model.Restaurant;
import com.umair.model.User;
import com.umair.repository.AddressRepository;
import com.umair.repository.RestaurantRepository;
import com.umair.repository.UserRepository;
import com.umair.request.CreateRestaurantRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class RestaurantServiceImpl implements RestaurantService {
 private static final Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

   
    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        // Creating Address entity
        Address address = new Address();
        address.setStreetAddress(req.getAddress().getStreetAddress());
        address.setCity(req.getAddress().getCity());
        address.setState(req.getAddress().getState());
        address.setPostalCode(req.getAddress().getPostalCode());
        address.setCountry(req.getAddress().getCountry());
        address = addressRepository.save(address);

        // Creating ContactInformation entity
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmail(req.getContactInformation().getEmail());
        contactInformation.setMobile(req.getContactInformation().getMobile());
        contactInformation.setTwitter(req.getContactInformation().getTwitter());
        contactInformation.setInstagram(req.getContactInformation().getInstagram());

        // Creating Restaurant entity
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(contactInformation);
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        restaurant.setOpen(true); // Default open status

        logger.info("Saving restaurant: {}", restaurant);
        return restaurantRepository.save(restaurant);
    }

    // Method to update a restaurant
    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        if (updatedRestaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if (updatedRestaurant.getDescription() != null) {
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        if (updatedRestaurant.getName() != null) {
            restaurant.setName(updatedRestaurant.getName());
        }
        if (updatedRestaurant.getOpeningHours() != null) {
            restaurant.setOpeningHours(updatedRestaurant.getOpeningHours());
        }
        // Update address and contact info if provided
        if (updatedRestaurant.getAddress() != null) {
            Address address = addressRepository.save(updatedRestaurant.getAddress());
            restaurant.setAddress(address);
        }
        if (updatedRestaurant.getContactInformation() != null) {
            ContactInformation contactInformation = updatedRestaurant.getContactInformation();
            restaurant.setContactInformation(contactInformation);
        }
        // Handle images update
        if (updatedRestaurant.getImages() != null) {
            restaurant.setImages(updatedRestaurant.getImages());
        }

        return restaurantRepository.save(restaurant);
    }

    // Method to delete a restaurant
    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    // Method to get all restaurants
    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    // Method to search restaurants
    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    // Method to find a restaurant by its ID
    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(id);
        if (opt.isEmpty()) {
            throw new Exception("Restaurant not found");
        }
        return opt.get();
    }

    // Method to get a restaurant by user ID
    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant == null) {
            throw new Exception("Restaurant not found by user id " + userId);
        }
        return restaurant;
    }

    // Method to add restaurant to favorites
    @Override
    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        boolean isFavorited = false;
        List<RestaurantDto> favourites = user.getFavoroiteItem();
        for (RestaurantDto favorite : favourites) {
            if (favorite.getId().equals(restaurantId)) {
                isFavorited = true;
                break;
            }
        }

        if (isFavorited) {
            favourites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        } else {
            favourites.add(dto);
        }

        userRepository.save(user);
        return dto;
    }

    // Method to toggle restaurant status (open/close)
    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}

