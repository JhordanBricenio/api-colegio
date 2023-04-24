package com.codej.services;

import com.codej.models.Course;
import com.codej.models.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface INoteService {
    public List<Note> findAll();
    public Note findById(Integer id);
    public Note save (Note note);
    public void delete(Integer id);
    public Page<Note> findAll(Pageable pageable);
}
