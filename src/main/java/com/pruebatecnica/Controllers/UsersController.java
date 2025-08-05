package com.pruebatecnica.Controllers;

import com.pruebatecnica.Configuration.Enviroments;
import com.pruebatecnica.DTO.Album.AlbumDTO;
import com.pruebatecnica.DTO.Posts.PostsDTO;
import com.pruebatecnica.DTO.Users.UsersDTO;
import com.pruebatecnica.Services.AlbumService;
import com.pruebatecnica.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private Enviroments env;

    @Autowired
    private UsersService usersService;

    @Autowired
    private AlbumService albumService;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Obtiene todos los usuarios
     * @return useriId
     */
    @GetMapping("/users")
    public ResponseEntity<UsersDTO[]> getUsers() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }

    /**
     * Obtiene los posts de un usuario por su ID
     * @param id useriId
     */
    @GetMapping("/users/{id}/posts")
    public ResponseEntity<PostsDTO[]> getUserPosts(@PathVariable String id) {
        return ResponseEntity.ok(usersService.getUsersPosts(id));
    }

    /**
     * Obtiene los albums de un usuario por su ID
     * @param id useriId
     */
    @GetMapping("users/{id}/albums")
    public ResponseEntity<AlbumDTO[]> getUserAlbums(@PathVariable String id) {
        return ResponseEntity.ok(albumService.getAlbumByUserId(id));
    }

    /**
     * Simula errores al obtener los posts de un usuario con IDs no válidos.
     * @return
     */
    @GetMapping("/users/simularErrores")
    public ResponseEntity<Void> simulateErrors() {
        usersService.getUsersPosts("2323");
        usersService.getUsersPosts("undatosuperrealista");
        usersService.getUsersPosts("valoresRarorsssss");
        usersService.getUsersPosts("??????sdID=");
        usersService.getUsersPosts("\n\t");
        usersService.getUsersPosts("\0\0");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
