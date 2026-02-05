package com.example.demo.cache;

import com.example.demo.model.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCacheEntry {
    private Post post;
    private long expiryTime;
}
