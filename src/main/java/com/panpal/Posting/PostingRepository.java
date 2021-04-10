package com.panpal.Posting;

import org.springframework.data.repository.CrudRepository;

import com.panpal.Topic.Topic;

public interface PostingRepository extends CrudRepository<Posting, Integer> {
    Posting findPostingById(Integer id);

    Iterable<Posting> findByOrderByEmailAsc();

    Iterable<Posting> findByTopicOrderByDateDesc(Topic topic);

    Iterable<Posting> findTop50ByTopicOrderByDateDesc(Topic topic);

    Iterable<Posting> findByEmailOrderByDateDesc(String email);
}
