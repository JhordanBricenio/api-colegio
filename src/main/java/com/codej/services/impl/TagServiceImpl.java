package com.codej.services.impl;

import com.codej.models.Tag;
import com.codej.repositories.ITagRepository;
import com.codej.services.ITagService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagServiceImpl implements ITagService {

    private final ITagRepository tagRepository;


    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findById(Integer id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag update(Tag tag, Integer id) {
        return tagRepository.save(tag);
    }

    @Override
    public void delete(Integer id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Page<Tag> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }
}
