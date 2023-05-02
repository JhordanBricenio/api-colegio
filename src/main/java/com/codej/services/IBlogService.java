package com.codej.services;

import com.codej.models.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBlogService {
    public List<Blog> findAll();
    public Blog findById(Integer id);
    public ResponseEntity<?> save (Blog blog);
    public Blog update(Blog blog, Integer id);
    public void delete(Integer id);
    public Page<Blog> findAll(Pageable pageable);
}
