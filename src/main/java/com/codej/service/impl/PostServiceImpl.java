package com.codej.service.impl;


import com.codej.model.Post;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IPostRepository;
import com.codej.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PostServiceImpl extends CRUDGenericImpl<Post, UUID> implements IPostService {

    private final IPostRepository postRepository;


    @Override
    protected IGenericRepository<Post, UUID> getRepository() {
        return postRepository;
    }
}
