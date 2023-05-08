package com.codej.controller;

import com.codej.models.Blog;
import com.codej.services.IBlogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@AllArgsConstructor
public class BlogRestController {

    private final IBlogService blogService;

    @GetMapping("/blog")
    public List<Blog> findAll() {
        return blogService.findAll();
    }

    @GetMapping("/blog/page/{page}")
    public Page<Blog> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 9);
        return blogService.findAll(pageable);
    }

    @PostMapping("/blog")
    public ResponseEntity<?> save(@RequestBody Blog blog) {
        return blogService.save(blog);
    }

    @GetMapping("/blog/{id}")
    public Blog findById(@PathVariable Integer id) {
        return blogService.findById(id);
    }

    @PutMapping("/blog/{id}")
    public Blog update(@RequestBody Blog blog, @PathVariable Integer id) {
        return blogService.update(blog, id);
    }
    @DeleteMapping("/blog/{id}")
    public void delete(@PathVariable Integer id){
        blogService.delete(id);
    }
}
