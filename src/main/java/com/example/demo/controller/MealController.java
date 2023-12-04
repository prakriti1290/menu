package com.example.demo.controller;

import com.example.demo.model.Meal;
import com.example.demo.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/meals")
public class MealController {
    @Autowired
    private MealRepository mealRepository;

    @GetMapping("/{id}")
    public Meal getMeal(@PathVariable String id) {
        return mealRepository.findById(id).orElse(null);
    }

    @GetMapping("/")
    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    @GetMapping("/breakfast")
    public List<Meal> getBreakfastMeals() {
        return mealRepository.findByType("breakfast");
    }

    @GetMapping("/dinner")
    public List<Meal> getDinnerMeals() {
        return mealRepository.findByType("dinner");
    }

    @PostMapping("/")
    public ResponseEntity<String> createMeal(@RequestBody Meal mealRequest) {
        try {
            Meal meal = new Meal();

            // Set the meal type based on your logic
            // For example, you can check a request parameter or field in the JSON data
            if (mealRequest.getType() != null) {
                if (mealRequest.getType().equalsIgnoreCase("breakfast")) {
                    meal.setType("breakfast");
                } else if (mealRequest.getType().equalsIgnoreCase("dinner")) {
                    meal.setType("dinner");
                } else {
                    return new ResponseEntity<>("Invalid meal type.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Meal type is required.", HttpStatus.BAD_REQUEST);
            }

            // Set other fields such as name, description, and image URL
            meal.setName(mealRequest.getName());
            meal.setDescription(mealRequest.getDescription());
            meal.setPrice(mealRequest.getPrice());
            meal.setImageUrl(mealRequest.getImageUrl());

            Meal savedMeal = mealRepository.save(meal);
            return new ResponseEntity<>("Meal created with ID: " + savedMeal.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create meal: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Add other endpoints for CRUD operations
}

