package com.my.contactbook.controller;

import com.my.contactbook.dto.PostDTO;
import com.my.contactbook.dto.SubjectDTO;
import com.my.contactbook.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("")
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

    @GetMapping("")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<PostDTO>> getAll() {
        List<PostDTO> dto = postService.getAllActive();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
        //@PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<PostDTO> getById(@PathVariable("id") long id) {
        PostDTO dto = postService.getById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
