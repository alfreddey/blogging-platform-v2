package com.example.demo.cache;

import com.example.demo.model.entity.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MapBasedPostCache implements Cache<Post> {
    private final Map<String, PostCacheEntry> cache = new HashMap<>();
    private final int TTL_MILLIS = 3600000;

    @Override
    public void put(Post item) {
        put(item.getId(), item);
    }

    @Override
    public void put(String id, Post item) {
        var expiryTime = System.currentTimeMillis() + TTL_MILLIS;
        cache.put(id, new PostCacheEntry(item, expiryTime));
    }

    @Override
    public Post getById(String postId) {
        var postCacheEntry = cache.get(postId);

        if (postCacheEntry == null) {
            return null;
        }

        if (postCacheEntry.getExpiryTime() <= System.currentTimeMillis()) {
            cache.remove(postId);
        }

        return postCacheEntry.getPost();
    }

    @Override
    public List<Post> getAll() {
        cache.values().removeIf(cacheEntry -> cacheEntry.getExpiryTime() <= System.currentTimeMillis());
        return new ArrayList<>(cache.values().stream().map(PostCacheEntry::getPost).toList());
    }

    @Override
    public void remove(String postId) {
        cache.remove(postId);
    }
}
