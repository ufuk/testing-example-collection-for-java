package com.ufukuzun.testingexamples.repository.jpa;

import com.ufukuzun.testingexamples.entity.jpa.BlogPost;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BlogPostRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Test
    public void listNotDeletedBlogPostsOrderedByCreatedDateDescendingly() {
        BlogPost blogPost1 = new BlogPost();
        blogPost1.setTitle("Unit Testing");
        blogPost1.setContent("Do not push any production code without unit tests.");
        blogPost1.setAuthor("John Doe");
        blogPost1.setCreatedDate(new DateTime(2014, 1, 1, 0, 0).toDate());

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setTitle("Spring Boot Testing Improvements");
        blogPost2.setContent("New @SpringBootTest and other new annotations those come within Spring Boot 1.4.0.");
        blogPost2.setAuthor("Richard Roe");
        blogPost2.setDeleted(true);
        blogPost2.setCreatedDate(new DateTime(2003, 4, 8, 1, 26).toDate());

        BlogPost blogPost3 = new BlogPost();
        blogPost3.setTitle("Test-Driven Development by Example");
        blogPost3.setContent("New @SpringBootTest and other new annotations those come within Spring Boot 1.4.0.");
        blogPost3.setAuthor("Richard Roe");
        blogPost3.setCreatedDate(new DateTime(2014, 10, 11, 9, 5).toDate());

        testEntityManager.persist(blogPost1);
        testEntityManager.persist(blogPost2);
        testEntityManager.persist(blogPost3);

        testEntityManager.flush();
        testEntityManager.clear();

        List<BlogPost> resultList = blogPostRepository.findByDeletedFalseOrderByCreatedDateDesc();

        assertThat(resultList, hasSize(2));
        assertThat(resultList.get(0).getId(), notNullValue());
        assertThat(resultList.get(0).getTitle(), equalTo("Test-Driven Development by Example"));
        assertThat(resultList.get(0).getContent(), equalTo("New @SpringBootTest and other new annotations those come within Spring Boot 1.4.0."));
        assertThat(resultList.get(0).getAuthor(), equalTo("Richard Roe"));
        assertThat(resultList.get(0).getCreatedDate(), comparesEqualTo(new DateTime(2014, 10, 11, 9, 5).toDate()));
        assertThat(resultList.get(1).getId(), notNullValue());
        assertThat(resultList.get(1).getTitle(), equalTo("Unit Testing"));
        assertThat(resultList.get(1).getContent(), equalTo("Do not push any production code without unit tests."));
        assertThat(resultList.get(1).getAuthor(), equalTo("John Doe"));
        assertThat(resultList.get(1).getCreatedDate(), comparesEqualTo(new DateTime(2014, 1, 1, 0, 0).toDate()));
    }

}