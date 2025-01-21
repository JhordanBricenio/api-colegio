package com.codej.controller;


import com.codej.dto.PostDTO;
import com.codej.mapper.PostMapper;
import com.codej.model.Post;
import com.codej.service.IPostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.*;

@RestController
@RequestMapping(POST_BASE)
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class PostController {

    private final IPostService postService;
    private final PostMapper postMapper;


    @GetMapping
    public ResponseEntity< List<PostDTO>> findAll() throws Exception {
        return ResponseEntity.ok(postMapper.toPostDTOList(postService.findAll()));
    }
    @PostMapping
    public ResponseEntity<PostDTO> save(@Valid @RequestBody PostDTO postDTO) throws Exception {
        Post post= postMapper.toPostEntity(postDTO);
        Post savedPost = postService.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(postMapper.toPostDTO(savedPost));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<PostDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(postMapper.toPostDTO(postService.findById(id)));
    }

    @PutMapping(ID_IN_PATH)
    public ResponseEntity<PostDTO> update(@Valid @RequestBody PostDTO postDTO,@PathVariable UUID id) throws Exception {
        Post post = postMapper.toPostEntity(postDTO);
        Post updatedPost = postService.update(post, id);
        return ResponseEntity.ok(postMapper.toPostDTO(updatedPost));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        postService.delete(id);
        return  ResponseEntity.noContent().build();
    }



}
