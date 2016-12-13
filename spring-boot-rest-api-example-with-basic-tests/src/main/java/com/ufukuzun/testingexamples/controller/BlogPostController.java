package com.ufukuzun.testingexamples.controller;

import com.ufukuzun.testingexamples.dto.CreateBlogPostRequest;
import com.ufukuzun.testingexamples.dto.ListBlogPostsResponse;
import com.ufukuzun.testingexamples.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewBlogPost(CreateBlogPostRequest createBlogPostRequest) {
        blogPostService.saveNewBlogPost(createBlogPostRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/list-all")
    public ResponseEntity<ListBlogPostsResponse> listAllBlogPosts() {
        ListBlogPostsResponse listBlogPostsResponse = blogPostService.listAllBlogPosts();
        return new ResponseEntity<>(listBlogPostsResponse, HttpStatus.CREATED);
    }

}
