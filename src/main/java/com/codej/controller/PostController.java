package com.codej.controller;

import com.codej.constants.ApiConstants;
import com.codej.model.Post;
import com.codej.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(ApiConstants.Post.BASE)
@RequiredArgsConstructor
public class PostController {

    private final IPostService blogService;

    @GetMapping
    public List<Post> findAll() {
        return blogService.findAll();
    }

    @GetMapping(ApiConstants.Post.PAGE)
    public Page<Post> index(@PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 9);
        return blogService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Post post) {
        return blogService.save(post);
    }

    @GetMapping(ApiConstants.Post.IN_ID_PATH)
    public Post findById(@PathVariable UUID id) {
        return blogService.findById(id);
    }

    @PutMapping(ApiConstants.Post.IN_ID_PATH)
    public Post update(@RequestBody Post post, @PathVariable UUID id) {
        return blogService.update(post, id);
    }
    @DeleteMapping("/blog/{id}")
    public void delete(@PathVariable UUID id){
        blogService.delete(id);
    }
}
