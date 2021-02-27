package com.panpal;

import org.springframework.data.repository.CrudRepository;

import com.panpal.Building;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BuildingRepository extends CrudRepository<Building, Integer> {
    Building findBuildingById(Integer id);
}
