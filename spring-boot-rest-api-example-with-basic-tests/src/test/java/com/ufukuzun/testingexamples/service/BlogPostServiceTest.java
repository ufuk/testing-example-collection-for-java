package com.ufukuzun.testingexamples.service;

import com.ufukuzun.testingexamples.dto.BlogPostDto;
import com.ufukuzun.testingexamples.dto.CreateBlogPostRequest;
import com.ufukuzun.testingexamples.dto.ListBlogPostsResponse;
import com.ufukuzun.testingexamples.entity.jpa.BlogPost;
import com.ufukuzun.testingexamples.repository.jpa.BlogPostRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogPostService.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BlogPostServiceTest {

    @Autowired
    private BlogPostService blogPostService;

    @MockBean
    private BlogPostRepository blogPostRepository;

    @Test
    public void saveNewBlogPost() {
        // Set/freeze current time to 14/12/2016 00:00
        DateTimeUtils.setCurrentMillisFixed(new DateTime(2016, 12, 14, 0, 0).getMillis());

        CreateBlogPostRequest createBlogPostRequest = new CreateBlogPostRequest();
        createBlogPostRequest.setTitle("Unit Testing");
        createBlogPostRequest.setContent("Do not push any production code without unit tests.");
        createBlogPostRequest.setAuthor("John Doe");

        blogPostService.saveNewBlogPost(createBlogPostRequest);

        ArgumentCaptor<BlogPost> blogPostArgumentCaptor = ArgumentCaptor.forClass(BlogPost.class);
        verify(blogPostRepository).save(blogPostArgumentCaptor.capture());
        BlogPost savedBlogPost = blogPostArgumentCaptor.getValue();
        assertThat(savedBlogPost.getCreatedDate(), equalTo(new DateTime(2016, 12, 14, 0, 0).toDate()));
        assertThat(savedBlogPost.getId(), nullValue());
        assertThat(savedBlogPost.getTitle(), equalTo("Unit Testing"));
        assertThat(savedBlogPost.getContent(), equalTo("Do not push any production code without unit tests."));
        assertThat(savedBlogPost.getAuthor(), equalTo("John Doe"));
        assertThat(savedBlogPost.isDeleted(), equalTo(false));

        verifyNoMoreInteractions(blogPostRepository);

        // Release/unfreeze time at the end of the test
        DateTimeUtils.setCurrentMillisSystem();
    }

    @Test
    public void listAllBlogPosts() {
        BlogPost blogPost1 = new BlogPost();
        blogPost1.setId(13L);
        blogPost1.setTitle("Unit Testing");
        blogPost1.setContent("Do not push any production code without unit tests.");
        blogPost1.setAuthor("John Doe");
        blogPost1.setCreatedDate(new DateTime(2014, 1, 1, 0, 0).toDate());

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setId(9L);
        blogPost2.setTitle("Spring Boot Testing Improvements");
        blogPost2.setContent("New @SpringBootTest and other new annotations those come within Spring Boot 1.4.0.");
        blogPost2.setAuthor("Richard Roe");
        blogPost2.setCreatedDate(new DateTime(2003, 4, 8, 1, 26).toDate());

        when(blogPostRepository.findByDeletedFalseOrderByCreatedDateDesc()).thenReturn(Arrays.asList(blogPost1, blogPost2));

        ListBlogPostsResponse listBlogPostsResponse = blogPostService.listAllBlogPosts();

        List<BlogPostDto> blogPostDtos = listBlogPostsResponse.getBlogPosts();
        assertThat(blogPostDtos, hasSize(2));
        assertThat(blogPostDtos.get(0).getId(), equalTo(13L));
        assertThat(blogPostDtos.get(0).getTitle(), equalTo("Unit Testing"));
        assertThat(blogPostDtos.get(0).getContent(), equalTo("Do not push any production code without unit tests."));
        assertThat(blogPostDtos.get(0).getAuthor(), equalTo("John Doe"));
        assertThat(blogPostDtos.get(0).getCreatedDate(), equalTo(new DateTime(2014, 1, 1, 0, 0).toDate()));
        assertThat(blogPostDtos.get(1).getId(), equalTo(9L));
        assertThat(blogPostDtos.get(1).getTitle(), equalTo("Spring Boot Testing Improvements"));
        assertThat(blogPostDtos.get(1).getContent(), equalTo("New @SpringBootTest and other new annotations those come within Spring Boot 1.4.0."));
        assertThat(blogPostDtos.get(1).getAuthor(), equalTo("Richard Roe"));
        assertThat(blogPostDtos.get(1).getCreatedDate(), equalTo(new DateTime(2003, 4, 8, 1, 26).toDate()));

        verify(blogPostRepository).findByDeletedFalseOrderByCreatedDateDesc();

        verifyNoMoreInteractions(blogPostRepository);
    }

}