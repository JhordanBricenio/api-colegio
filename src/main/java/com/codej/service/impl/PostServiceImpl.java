package com.codej.service.impl;

import com.codej.model.Post;
import com.codej.repository.IPostRepository;
import com.codej.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {

    private final IPostRepository blogRepository;

    @Override
    public List<Post> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Post findById(UUID id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<?> save(Post post) {
        Map<String, Object> response = new HashMap<>();
        Post postNew = null;
        try {
            postNew = blogRepository.save(post);
        }catch (Exception e){
            response.put("mensaje", "Error al crear el blog");
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        response.put("mensaje", "El blog ha sido creado con éxito");
        response.put("proyecto", postNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Override
    public Post update(Post post, UUID id) {
        Post postUpdate = findById(post.getId());
        postUpdate.setTitle(post.getTitle());
        postUpdate.setDate(post.getDate());
        //postUpdate.setAuthor(post.getAuthor());
        postUpdate.setContent(post.getContent());
        return blogRepository.save(post);
    }

    @Override
    public void delete(UUID id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }
}
