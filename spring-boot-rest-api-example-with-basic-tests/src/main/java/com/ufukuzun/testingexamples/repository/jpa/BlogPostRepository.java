package com.ufukuzun.testingexamples.repository.jpa;

import com.ufukuzun.testingexamples.entity.jpa.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    List<BlogPost> findByDeletedFalseOrderByCreatedDateDesc();

}
