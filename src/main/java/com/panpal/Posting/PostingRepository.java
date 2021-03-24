package com.panpal.Posting;

import org.springframework.data.repository.CrudRepository;

import com.panpal.Posting.Posting;
import com.panpal.Topic.Topic;

public interface PostingRepository extends CrudRepository<Posting, Integer> {
    Posting findPostingById(Integer id);

    Iterable<Posting> findByOrderByEmailAsc();

    Iterable<Posting> findByTopicOrderByDateAsc(Topic topic);

    Iterable<Posting> findByEmailOrderByDateAsc(String email);
}
