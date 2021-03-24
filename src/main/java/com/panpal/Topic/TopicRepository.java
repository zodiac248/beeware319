package com.panpal.Topic;

import org.springframework.data.repository.CrudRepository;

import com.panpal.Topic.Topic;

public interface TopicRepository extends CrudRepository<Topic, Integer> {
    Topic findTopicById(Integer id);

    Iterable<Topic> findByOrderByNameAsc();
}
