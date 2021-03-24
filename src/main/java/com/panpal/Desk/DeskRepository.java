package com.panpal.Desk;

import org.springframework.data.repository.CrudRepository;

import com.panpal.Floor.Floor;

public interface DeskRepository extends CrudRepository<Desk, Integer> {
    Desk findDeskById(Integer id);

    Iterable<Desk> findByFloor(Floor floor);

    Iterable<Desk> findByFloorOrderByDeskNumberAsc(Floor floor);
}
