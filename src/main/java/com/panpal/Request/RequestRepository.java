package com.panpal.Request;

import org.springframework.data.repository.CrudRepository;

import com.panpal.Request.Request;
import com.panpal.Mail.Mail;

public interface RequestRepository extends CrudRepository<Request, Integer> {
    Request findRequestById(Integer id);

    Iterable<Request> findByOrderBySubmissionDateAsc();

    Iterable<Request> findByEmailOrderBySubmissionDateAsc(String email);
}
