package com.my.contactbook.service;

import com.my.contactbook.dto.PostDTO;
import com.my.contactbook.entity.PostEntity;
import com.my.contactbook.mapper.PostMapper;
import com.my.contactbook.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    public List<PostDTO> getAllActive() {
        List<PostEntity> list = postRepository.findAll();
        List<PostEntity> activeList = new ArrayList<>();
        for (PostEntity post: list){
            if(!post.isDeleted()){
                activeList.add(post);
            }
        }
        return postMapper.toListDto(activeList);
    }

    public PostDTO getById(long postId){
        PostEntity entity = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Error: Not found post"));
        return postMapper.convertToDto(entity);
    }

    public List<PostDTO> getByCreatedUser(String username){
        List<PostEntity> list = postRepository.findByCreatedBy(username);
        return postMapper.toListDto(list);
    }

    public PostDTO createPost(PostDTO dto){
        PostEntity entity = postMapper.convertToEntity(dto);
        entity.setDeleted(false);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setPostDate(LocalDateTime.now());
        return postMapper.convertToDto(postRepository.save(entity));
    }

    public PostDTO updatePost(PostDTO dto){
        PostEntity entity = postMapper.convertToEntity(dto);
        entity.setLastUpdatedDate(LocalDateTime.now());
        return postMapper.convertToDto(postRepository.save(entity));
    }

}
