package com.codej.service.impl;

import com.codej.dto.TagDTO;
import com.codej.exceptions.ResourceNotFoundException;
import com.codej.mapper.TagMapper;
import com.codej.model.Tag;
import com.codej.repository.ITagRepository;
import com.codej.service.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements ITagService {

    private final ITagRepository tagRepository;
    private final TagMapper tagMapper;


    @Override
    public List<TagDTO> findAll() {
        return tagRepository.findAll().stream().map(tagMapper::toTagDTO).toList();
    }

    @Override
    public TagDTO findById(UUID id) {
        return tagRepository.findById(id)
                .map(tagMapper::toTagDTO)
                .orElseThrow(()-> new ResourceNotFoundException(Tag.class.getName(), id));
    }

    @Override
    public TagDTO save(TagDTO tagDTO) {
        Tag tag= tagMapper.toTagEntity(tagDTO);
        return tagMapper.toTagDTO(tagRepository.save(tag));
    }

    @Override
    public TagDTO update(TagDTO tagDTO, UUID id) {
        return tagRepository.findById(id).map(extistinnTag ->{
            Tag tag= tagMapper.toTagEntity(tagDTO);
            tag.setId(extistinnTag.getId());
            return tagMapper.toTagDTO(tagRepository.save(tag));
        }).orElseThrow(()-> new ResourceNotFoundException(Tag.class.getName(), id));
    }

    @Override
    public void delete(UUID id) {
        tagRepository.deleteById(id);
    }


}
