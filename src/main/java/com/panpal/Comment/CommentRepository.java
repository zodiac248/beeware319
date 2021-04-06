package com.panpal.Comment;

import org.springframework.data.repository.CrudRepository;

import com.panpal.Comment.Comment;
import com.panpal.Posting.Posting;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Comment findCommentById(Integer id);

    Iterable<Comment> findByOrderByEmailAsc();

    Iterable<Comment> findByPostingOrderByDateDesc(Posting posting);
}
