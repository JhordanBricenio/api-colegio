package com.codej.repository;

import com.codej.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITagRepository extends JpaRepository<Tag, UUID> {
}
