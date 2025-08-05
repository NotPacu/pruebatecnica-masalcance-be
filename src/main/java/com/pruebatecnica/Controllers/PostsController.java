package com.pruebatecnica.Controllers;


import com.pruebatecnica.Configuration.Enviroments;
import com.pruebatecnica.DTO.Posts.PostsDTO;
import com.pruebatecnica.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class PostsController {

    @Autowired
    private Enviroments env;

    @Autowired
    private PostService postService;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Obtiene todos los posts
     * @return DTO de los posts
     */
    @GetMapping("/Posts")
    public ResponseEntity<PostsDTO[]> getPosts() {
        return ResponseEntity.ok(postService.getPosts());
    }
}
