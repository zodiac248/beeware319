package com.panpal.Mail;

import org.springframework.data.repository.CrudRepository;

import com.panpal.Mail.Mail;
import com.panpal.Request.Request;

public interface MailRepository extends CrudRepository<Mail, Integer> {
    Mail findMailById(Integer id);
    
    Iterable<Mail> findByOrderByDateDesc();
    
    Iterable<Mail> findByEmailOrderByDateDesc(String email);
    
    Iterable<Mail> findByStatusOrderByDateDesc(String status);
    
    Iterable<Mail> findByEmailAndStatusOrderByDateDesc(String email, String status);
    
    Mail findByRequest(Request request);
}
