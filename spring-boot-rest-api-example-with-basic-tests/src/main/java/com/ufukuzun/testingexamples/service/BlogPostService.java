package com.ufukuzun.testingexamples.service;

import com.ufukuzun.testingexamples.dto.BlogPostDto;
import com.ufukuzun.testingexamples.dto.CreateBlogPostRequest;
import com.ufukuzun.testingexamples.dto.ListBlogPostsResponse;
import com.ufukuzun.testingexamples.entity.jpa.BlogPost;
import com.ufukuzun.testingexamples.repository.jpa.BlogPostRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    public void saveNewBlogPost(CreateBlogPostRequest createBlogPostRequest) {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(createBlogPostRequest.getTitle());
        blogPost.setAuthor(createBlogPostRequest.getAuthor());
        blogPost.setContent(createBlogPostRequest.getContent());
        blogPost.setCreatedDate(DateTime.now().toDate());
        blogPostRepository.save(blogPost);
    }

    public ListBlogPostsResponse listAllBlogPosts() {
        ListBlogPostsResponse listBlogPostsResponse = new ListBlogPostsResponse();

        List<BlogPost> blogPosts = blogPostRepository.findAllDeletedFalseByOrderByCreatedDateDesc();

        blogPosts.forEach(each -> {
            BlogPostDto blogPostDto = new BlogPostDto();
            blogPostDto.setTitle(each.getTitle());
            blogPostDto.setContent(each.getContent());
            blogPostDto.setAuthor(each.getAuthor());
            blogPostDto.setCreatedDate(each.getCreatedDate());
            listBlogPostsResponse.getBlogPosts().add(blogPostDto);
        });

        return listBlogPostsResponse;
    }

}
