package com.example.demo.utils;

import com.example.demo.model.entity.Post;
import java.util.ArrayList;
import java.util.List;

public class PostSorts {

    public static void sort(List<Post> posts) {
        if (posts == null || posts.size() <= 1) {
            return;
        }

        int mid = posts.size() / 2;

        List<Post> left = new ArrayList<>(posts.subList(0, mid));
        List<Post> right = new ArrayList<>(posts.subList(mid, posts.size()));

        sort(left);
        sort(right);

        merge(posts, left, right);
    }

    private static void merge(List<Post> posts, List<Post> left, List<Post> right) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getTitle().compareTo(right.get(j).getTitle()) <= 0) {
                posts.set(k++, left.get(i++));
            } else {
                posts.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) {
            posts.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            posts.set(k++, right.get(j++));
        }
    }
}