package com.codej.repository;

import com.codej.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INoteRepository extends JpaRepository<Note, Integer> {
}
