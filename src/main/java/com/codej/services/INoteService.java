package com.codej.services;

import com.codej.models.Course;
import com.codej.models.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface INoteService {
    public List<Note> findAll();
    public Note findById(Integer id);
    public ResponseEntity<?> save (Note note);

    public Note update(Note note, Integer id);
    public void delete(Integer id);
    public Page<Note> findAll(Pageable pageable);
}
