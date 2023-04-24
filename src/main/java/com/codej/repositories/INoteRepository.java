package com.codej.repositories;

import com.codej.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INoteRepository extends JpaRepository<Note, Integer> {
}
