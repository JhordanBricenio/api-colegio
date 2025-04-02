package com.codej.service;


import com.codej.model.Post;

import java.util.UUID;

public interface IPostService extends ICRUDService<Post, UUID> {
    Post save(Post post) throws  Exception;
}
