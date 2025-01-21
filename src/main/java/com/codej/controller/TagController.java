package com.codej.controller;


import com.codej.dto.TagDTO;
import com.codej.mapper.TagMapper;
import com.codej.model.Tag;
import com.codej.service.ITagService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.ID_IN_PATH;
import static com.codej.constants.ApiConstants.TAG_BASE;

@RestController
@RequestMapping(TAG_BASE)
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class TagController {

    private final ITagService serviceService;
    private final TagMapper tagMapper;


    @GetMapping
    public ResponseEntity< List<TagDTO>> findAll() throws Exception {
        tagMapper.toTagDTOList(serviceService.findAll());
        return ResponseEntity.ok(tagMapper.toTagDTOList(serviceService.findAll()));
    }
    @PostMapping
    public ResponseEntity<TagDTO> save(@Valid @RequestBody TagDTO tagDTO) throws Exception {
        Tag tag= tagMapper.toTagEntity(tagDTO);
        Tag savedTag = serviceService.save(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(tagMapper.toTagDTO(savedTag));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<TagDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(tagMapper.toTagDTO(serviceService.findById(id)));
    }

    @PutMapping(ID_IN_PATH)
    public ResponseEntity<TagDTO> update(@Valid @RequestBody TagDTO tagDTO,@PathVariable UUID id) throws Exception {
        Tag tag = tagMapper.toTagEntity(tagDTO);
        Tag updatedTag = serviceService.update(tag, id);
        return ResponseEntity.ok(tagMapper.toTagDTO(updatedTag));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        serviceService.delete(id);
        return  ResponseEntity.noContent().build();
    }



}
