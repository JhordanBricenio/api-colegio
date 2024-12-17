package com.codej.service;

import com.codej.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface IPostService {
    public List<Post> findAll();
    public Post findById(UUID id);
    public ResponseEntity<?> save (Post post);
    public Post update(Post post, UUID id);
    public void delete(UUID id);
    public Page<Post> findAll(Pageable pageable);
}
