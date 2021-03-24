package com.panpal.Floor;

import org.springframework.data.repository.CrudRepository;

import com.panpal.Building.Building;

public interface FloorRepository extends CrudRepository<Floor, Integer> {
    Floor findFloorById(Integer id);

    Iterable<Floor> findByBuilding(Building building);

    Iterable<Floor> findByBuildingOrderByFloorNumberAsc(Building building);
}
