package com.panpal;

import org.springframework.data.repository.CrudRepository;

import com.panpal.Desk;
import com.panpal.Floor;

public interface DeskRepository extends CrudRepository<Desk, Integer> {
    Desk findDeskById(Integer id);

    Iterable<Desk> findByFloor(Floor floor);
}
