package com.ufukuzun.testingexamples.service;

import com.ufukuzun.testingexamples.dto.CreateBlogPostRequest;
import com.ufukuzun.testingexamples.dto.ListBlogPostsResponse;
import com.ufukuzun.testingexamples.repository.jpa.BlogPostRepository;
import org.joda.time.DateTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogPostService.class)
public class BlogPostServiceTest {

    @Autowired
    private BlogPostService blogPostService;

    @MockBean
    private BlogPostRepository blogPostRepository;

    @Test
    public void saveNewBlogPost() {
        blogPostService.saveNewBlogPost(new CreateBlogPostRequest());

        DateTimeUtils.setCurrentMillisSystem();
    }

    @Test
    public void listAllBlogPosts() {
        ListBlogPostsResponse listBlogPostsResponse = blogPostService.listAllBlogPosts();
    }

}