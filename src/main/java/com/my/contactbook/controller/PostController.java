package com.my.contactbook.controller;

import com.my.contactbook.dto.PostDTO;
import com.my.contactbook.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO post) {
        PostDTO dto = postService.createPost(post);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/update")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO post) {
        PostDTO dto = postService.updatePost(post);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}