package com.codej.repositories;

import com.codej.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITagRepository extends JpaRepository<Tag, Integer> {
}
