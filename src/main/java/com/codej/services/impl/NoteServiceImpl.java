package com.codej.services.impl;

import com.codej.models.Note;
import com.codej.services.INoteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements INoteService {


    @Override
    public List<Note> findAll() {
        return null;
    }

    @Override
    public Note findById(Integer id) {
        return null;
    }

    @Override
    public Note save(Note note) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Page<Note> findAll(Pageable pageable) {
        return null;
    }
}
