package com.panpal.Subscription;

import org.springframework.data.repository.CrudRepository;

import com.panpal.Subscription.Subscription;
import com.panpal.Topic.Topic;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    Subscription findSubscriptionById(Integer id);

    Iterable<Subscription> findByOrderByEmailAsc();

    Iterable<Subscription> findByEmailOrderByTopicAsc(String email);

    Iterable<Subscription> findByTopicOrderByEmailAsc(Topic topic);
}
