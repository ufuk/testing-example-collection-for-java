package com.ufukuzun.testingexamples.repository.jpa;

import com.ufukuzun.testingexamples.entity.jpa.BlogPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BlogPostRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Test
    public void listNotDeletedBlogPostsOrderedByCreatedDateDescendingly() {
        // TODO: complete test
        List<BlogPost> resultList = blogPostRepository.findAllDeletedFalseByOrderByCreatedDateDesc();
    }

}