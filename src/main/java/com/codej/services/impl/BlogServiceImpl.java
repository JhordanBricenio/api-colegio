package com.codej.services.impl;

import com.codej.models.Blog;
import com.codej.repositories.IBlogRepository;
import com.codej.services.IBlogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements IBlogService {

    private final IBlogRepository blogRepository;

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Blog findById(Integer id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<?> save(Blog blog) {
        Map<String, Object> response = new HashMap<>();
        Blog blogNew = null;
        try {
            blogNew = blogRepository.save(blog);
        }catch (Exception e){
            response.put("mensaje", "Error al crear el blog");
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        response.put("mensaje", "El blog ha sido creado con éxito");
        response.put("proyecto", blogNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Override
    public Blog update(Blog blog, Integer id) {
        Blog blogUpdate = findById(blog.getId());
        blogUpdate.setTitle(blog.getTitle());
        blogUpdate.setDate(blog.getDate());
        blogUpdate.setAuthor(blog.getAuthor());
        blogUpdate.setContent(blog.getContent());
        return blogRepository.save(blog);
    }

    @Override
    public void delete(Integer id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }
}
