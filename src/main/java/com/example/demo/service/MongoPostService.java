package com.example.demo.service;

import com.example.demo.cache.Cache;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.entity.Post;
import com.example.demo.repository.interfaces.PostRepository;
import com.example.demo.service.interfaces.PostService;
import com.example.demo.utils.PostSearch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoPostService implements PostService {
    private final PostRepository postRepository;
    private final Cache<Post> postCache;
    private final PostSearch postSearch;

    public MongoPostService(PostRepository postRepository, Cache<Post> postCache, PostSearch postSearch) {
        this.postCache = postCache;
        this.postRepository = postRepository;
        this.postSearch = postSearch;
    }

    @Override
    public List<Post> getAll(int page, int size) {
        var cacheResult = postCache.getAll();

        if (cacheResult != null && !cacheResult.isEmpty() && cacheResult.size() >= size) {
            return cacheResult;
        }

        var posts = postRepository.getAll(page, size);
        posts.forEach(postCache::put);

        return posts;
    }

    @Override
    public Post getById(String postId) {
        var cacheResult = postCache.getById(postId);

        if (cacheResult != null) {
            return cacheResult;
        }

        var post = postRepository.getById(postId);

        if (post == null) {
            throw new ResourceNotFoundException("Post with id: " + postId + " not found");
        }

        postCache.put(post);

        return post;
    }

    @Override
    public Post create(Post post) {
        post = postRepository.insert(post);
        postCache.put(post);

        return post;
    }

    @Override
    public boolean delete(String postId) {
        postCache.remove(postId);
        return postRepository.delete(postId);
    }

    @Override
    public Post updatePostContent(String id, String content) {
        var post = postRepository.updatePostContent(id, content);
        postCache.put(id, post);

        return post;
    }

    // TODO: SEARCH DB IF NOT IN CACHE
    @Override
    public Post search(String searchTerm) {
        var post = postSearch.search(searchTerm);

        if (post == null) {
            throw new ResourceNotFoundException("Post not found with search term: " + searchTerm);
        }

        return post;
    }
}
