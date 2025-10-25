package com.codej.service.impl;


import com.codej.model.Setting;
import com.codej.model.Tag;
import com.codej.repository.IGenericRepository;
import com.codej.repository.ISettingRepository;
import com.codej.repository.ITagRepository;
import com.codej.service.ISettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class PostServiceImpl extends CRUDGenericImpl<Setting, UUID> implements ISettingService {

    private final ISettingRepository postRepository;


    @Override
    protected IGenericRepository<Setting, UUID> getRepository() {
        return postRepository;
    }

}
