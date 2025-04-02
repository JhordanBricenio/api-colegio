package com.codej.service.impl;


import com.codej.model.Post;
import com.codej.model.Tag;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IPostRepository;
import com.codej.repository.ITagRepository;
import com.codej.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class PostServiceImpl extends CRUDGenericImpl<Post, UUID> implements IPostService {

    private final IPostRepository postRepository;
    private final ITagRepository tagRepository;


    @Override
    protected IGenericRepository<Post, UUID> getRepository() {
        return postRepository;
    }

    @Override
    public Post save(Post post) throws Exception {
        List<Tag> tagsSet = new ArrayList<>();
        for (Tag tag : post.getTags()) {
            Tag tagExist = tagRepository.findByName(tag.getName());
            if (tagExist != null) {
                tagsSet.add(tagExist);
            } else {
                tagsSet.add(tag);
            }
        }
        post.setTags(tagsSet);
        return postRepository.save(post);
    }
}
