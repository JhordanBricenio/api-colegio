package com.codej.repository;

import com.codej.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITagRepository extends IGenericRepository<Tag, UUID> {

    Tag findByName(String name);
}
