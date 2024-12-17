package com.codej.controller;

import com.codej.model.Note;
import com.codej.service.INoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class NoteRestController {

    private final INoteService noteService;


    @GetMapping("/note")
    public List<Note> findAll() {
        return noteService.findAll();
    }

    @PostMapping("/note")
    public ResponseEntity<?> save(@RequestBody Note note) {
        return noteService.save(note);
    }
    @GetMapping("/note/{id}")
    public Note findById(@PathVariable Integer id) {
        return noteService.findById(id);
    }
    @PutMapping("/note/{id}")
    public Note update(@RequestBody Note note,@PathVariable Integer id) {
        return noteService.update(note, id);
    }
    @DeleteMapping("/note/{id}")
    public void delete(@PathVariable Integer id){
        noteService.delete(id);
    }
}
