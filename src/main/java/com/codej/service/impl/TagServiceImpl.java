package com.codej.service.impl;


import com.codej.model.Tag;
import com.codej.repository.IGenericRepository;
import com.codej.repository.ITagRepository;
import com.codej.service.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TagServiceImpl extends CRUDGenericImpl<Tag, UUID> implements ITagService {

    private final ITagRepository tagRepository;


    @Override
    protected IGenericRepository<Tag, UUID> getRepository() {
        return tagRepository;
    }
}
