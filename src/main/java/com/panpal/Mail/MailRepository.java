package com.panpal.Mail;

import org.springframework.data.repository.CrudRepository;

import com.panpal.Mail.Mail;
import com.panpal.Request.Request;

public interface MailRepository extends CrudRepository<Mail, Integer> {
    Mail findMailById(Integer id);
    
    Iterable<Mail> findByOrderByDateAsc();
    
    Iterable<Mail> findByEmailOrderByDateAsc(String email);
}
