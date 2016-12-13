package com.ufukuzun.testingexamples.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufukuzun.testingexamples.dto.BlogPostDto;
import com.ufukuzun.testingexamples.dto.CreateBlogPostRequest;
import com.ufukuzun.testingexamples.dto.ListBlogPostsResponse;
import com.ufukuzun.testingexamples.service.BlogPostService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BlogPostController.class)
public class BlogPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogPostService blogPostService;

    @Test
    public void createNewBlogPost() throws Exception {
        CreateBlogPostRequest createBlogPostRequest = new CreateBlogPostRequest();
        createBlogPostRequest.setTitle("Unit Testing");
        createBlogPostRequest.setContent("Do not push any production code without unit tests.");
        createBlogPostRequest.setAuthor("John Doe");

        mockMvc.perform(post("/post/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(createBlogPostRequest)))
                .andExpect(status().isCreated());

        ArgumentCaptor<CreateBlogPostRequest> requestDtoArgumentCaptor = ArgumentCaptor.forClass(CreateBlogPostRequest.class);
        verify(blogPostService).saveNewBlogPost(requestDtoArgumentCaptor.capture());
        CreateBlogPostRequest capturedRequestDto = requestDtoArgumentCaptor.getValue();
        assertThat(capturedRequestDto.getTitle(), equalTo("Unit Testing"));
        assertThat(capturedRequestDto.getContent(), equalTo("Do not push any production code without unit tests."));
        assertThat(capturedRequestDto.getAuthor(), equalTo("John Doe"));

        verifyNoMoreInteractions(blogPostService);
    }

    @Test
    public void listAllBlogPosts() throws Exception {
        BlogPostDto blogPostDto1 = new BlogPostDto();
        blogPostDto1.setTitle("Spring Boot Testing Improvements");
        blogPostDto1.setContent("New @SpringBootTest and other new annotations those come within Spring Boot 1.4.0.");
        blogPostDto1.setAuthor("Richard Roe");
        blogPostDto1.setCreatedDate(new DateTime(2016, 12, 14, 0, 0).toDate());

        BlogPostDto blogPostDto2 = new BlogPostDto();
        blogPostDto2.setTitle("Unit Testing");
        blogPostDto2.setContent("Do not push any production code without unit tests.");
        blogPostDto2.setAuthor("John Doe");
        blogPostDto2.setCreatedDate(new DateTime(2016, 11, 15, 0, 0).toDate());

        ListBlogPostsResponse listBlogPostsResponse = new ListBlogPostsResponse();
        listBlogPostsResponse.getBlogPosts().addAll(Arrays.asList(blogPostDto1, blogPostDto2));

        when(blogPostService.listAllBlogPosts()).thenReturn(listBlogPostsResponse);

        mockMvc.perform(get("/post/list-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.blogPosts.*", hasSize(2)))
                .andExpect(jsonPath("$.blogPosts[0].title", equalTo("Spring Boot Testing Improvements")))
                .andExpect(jsonPath("$.blogPosts[0].content", equalTo("New @SpringBootTest and other new annotations those come within Spring Boot 1.4.0.")))
                .andExpect(jsonPath("$.blogPosts[0].author", equalTo("Richard Roe")))
                .andExpect(jsonPath("$.blogPosts[0].createdDate", equalTo(new DateTime(2016, 12, 14, 0, 0).getMillis())))
                .andExpect(jsonPath("$.blogPosts[1].title", equalTo("Unit Testing")))
                .andExpect(jsonPath("$.blogPosts[1].content", equalTo("Do not push any production code without unit tests.")))
                .andExpect(jsonPath("$.blogPosts[1].author", equalTo("John Doe")))
                .andExpect(jsonPath("$.blogPosts[1].createdDate", equalTo(new DateTime(2016, 11, 15, 0, 0).getMillis())));

        verify(blogPostService).listAllBlogPosts();
    }

}