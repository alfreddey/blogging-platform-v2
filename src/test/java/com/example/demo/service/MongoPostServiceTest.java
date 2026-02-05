package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.cache.Cache;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.entity.Post;
import com.example.demo.repository.interfaces.PostRepository;
import com.example.demo.utils.PostSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MongoPostServiceTest {

    private PostRepository postRepository;
    private Cache<Post> postCache;
    private PostSearch postSearch;
    private MongoPostService postService;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        postCache = mock(Cache.class);
        postSearch = mock(PostSearch.class);
        postService = new MongoPostService(postRepository, postCache, postSearch);
    }

    @Test
    void getById_ShouldReturnFromCache_WhenExists() {
        Post mockPost = new Post();

        mockPost.setId("1");
        mockPost.setTitle("Title");

        when(postCache.getById("1")).thenReturn(mockPost);

        Post result = postService.getById("1");

        assertEquals(mockPost, result);
        verifyNoInteractions(postRepository);
    }

    @Test
    void getById_ShouldFetchFromRepo_WhenCacheMiss() {
        Post mockPost = new Post();

        mockPost.setId("1");
        mockPost.setTitle("Title");

        when(postCache.getById("1")).thenReturn(null);
        when(postRepository.getById("1")).thenReturn(mockPost);

        Post result = postService.getById("1");

        assertEquals(mockPost, result);
        verify(postCache).put(mockPost);
    }

    @Test
    void getById_ShouldThrowException_WhenNotFoundInRepo() {
        when(postCache.getById("1")).thenReturn(null);
        when(postRepository.getById("1")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> postService.getById("1"));
    }

    @Test
    void create_ShouldSaveToRepoAndCache() {
        Post newPost = new Post();

        newPost.setId("1");
        newPost.setTitle("Title");

        postService.create(newPost);

        verify(postRepository).insert(newPost);
        verify(postCache).put(newPost);
    }

    @Test
    void search_ShouldReturnPost_WhenFound() {
        Post mockPost = new Post();

        mockPost.setId("3");
        mockPost.setTitle("Search Result term");

        when(postSearch.search("term")).thenReturn(mockPost);

        Post result = postService.search("term");

        assertEquals(mockPost, result);
    }

    @Test
    void search_ShouldThrowException_WhenNoMatch() {
        when(postSearch.search("none")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> postService.search("none"));
    }
}