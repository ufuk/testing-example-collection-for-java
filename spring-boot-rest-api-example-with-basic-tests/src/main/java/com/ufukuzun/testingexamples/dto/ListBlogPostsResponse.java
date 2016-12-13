package com.ufukuzun.testingexamples.dto;

import java.util.ArrayList;
import java.util.List;

public class ListBlogPostsResponse {

    private List<BlogPostDto> blogPosts = new ArrayList<>();

    public List<BlogPostDto> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(List<BlogPostDto> blogPosts) {
        this.blogPosts = blogPosts;
    }

}
