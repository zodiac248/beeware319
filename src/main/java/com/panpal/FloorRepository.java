package com.panpal;

import org.springframework.data.repository.CrudRepository;

import com.panpal.Floor;
import com.panpal.Building;

public interface FloorRepository extends CrudRepository<Floor, Integer> {
    Floor findFloorById(Integer id);

    Iterable<Floor> findByBuilding(Building building);
}
