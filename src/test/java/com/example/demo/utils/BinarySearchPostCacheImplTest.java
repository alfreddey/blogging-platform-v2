package com.example.demo.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.cache.Cache;
import com.example.demo.model.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class BinarySearchPostCacheImplTest {

    private Cache<Post> postCache;
    private BinarySearchPostCacheImpl searchService;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        postCache = mock(Cache.class);
        searchService = new BinarySearchPostCacheImpl(postCache);
    }

    @Test
    void search_ShouldReturnCorrectPost_WhenTitleExists() {
        var post1 = new Post();
        post1.setId("1");
        post1.setTitle("Spring Boot");

        var post2 = new Post();
        post2.setId("2");
        post2.setTitle("Algorithms");

        var post3 = new Post();
        post3.setId("3");
        post3.setTitle("Java Guide");

        List<Post> posts = new ArrayList<>(List.of(
                post2,
                post1,
                post3
        ));
        when(postCache.getAll()).thenReturn(posts);

        Post result = searchService.search("Java Guide");

        assertNotNull(result);
        assertEquals("3", result.getId());
        assertEquals("Java Guide", result.getTitle());
    }

    @Test
    void search_ShouldReturnNull_WhenTitleDoesNotExist() {
        var post1 = new Post();
        post1.setId("1");
        post1.setTitle("Spring Boot");

        when(postCache.getAll()).thenReturn(new ArrayList<>(List.of(post1)));

        Post result = searchService.search("Z");

        assertNull(result);
    }

    @Test
    void search_ShouldHandleEmptyCache() {
        when(postCache.getAll()).thenReturn(new ArrayList<>());

        Post result = searchService.search("Any Title");

        assertNull(result);
    }

    @Test
    void search_ShouldReturnFirstMatch_WhenDuplicateTitlesExist() {
        var post1 = new Post();
        post1.setId("1");
        post1.setTitle("Spring Boot");

        var post2 = new Post();
        post2.setId("2");
        post2.setTitle("Algorithms");

        List<Post> posts = new ArrayList<>(List.of(post2, post1));

        when(postCache.getAll()).thenReturn(posts);

        Post result = searchService.search("Spring Boot");

        assertNotNull(result, "Search result should not be null");
        assertEquals("Spring Boot", result.getTitle());
    }
}