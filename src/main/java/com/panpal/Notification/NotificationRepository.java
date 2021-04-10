package com.panpal.Notification;

import org.springframework.data.repository.CrudRepository;

import com.panpal.Posting.Posting;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {
    Notification findNotificationById(Integer id);

    Iterable<Notification> findByOrderByEmailAsc();

    Iterable<Notification> findByEmailOrderByDateAsc(String email);
}