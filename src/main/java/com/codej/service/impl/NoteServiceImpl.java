package com.codej.service.impl;

import com.codej.model.Note;
import com.codej.repository.INoteRepository;
import com.codej.service.INoteService;
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
public class NoteServiceImpl implements INoteService {

    private final INoteRepository noteRepository;


    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    @Override
    public Note findById(Integer id) {
        return noteRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<?> save(Note note) {
        Map<String, Object> response = new HashMap<>();
        Note noteNew = null;
        try {
            noteNew = noteRepository.save(note);
        }catch (Exception e){
            response.put("mensaje", "Error al crear la nota");
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        response.put("mensaje", "la nota ha sido creada con éxito");
        response.put("proyecto", noteNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Override
    public Note update(Note note, Integer id) {
        Note noteUpdate = findById(note.getId());
        noteUpdate.setName(note.getName());
        noteUpdate.setDescription(note.getDescription());
        noteUpdate.setNote(note.getNote());
        noteUpdate.setDate(note.getDate());
        return noteRepository.save(note);
    }

    @Override
    public void delete(Integer id) {
        noteRepository.deleteById(id);
    }

    @Override
    public Page<Note> findAll(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }
}
