package com.codej.services;

import com.codej.models.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITagService {
    public List<Tag> findAll();
    public Tag findById(Integer id);
    public Tag save (Tag tag);
    public Tag update(Tag tag, Integer id);
    public void delete(Integer id);
    public Page<Tag> findAll(Pageable pageable);
}
