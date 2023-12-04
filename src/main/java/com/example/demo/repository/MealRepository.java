package com.example.demo.repository;

import com.example.demo.model.Meal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends MongoRepository<Meal, String> {

    List<Meal> findByType(String type);
}

