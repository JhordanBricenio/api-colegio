package com.codej.repository;

import com.codej.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPostRepository extends JpaRepository<Post, UUID> {
}
