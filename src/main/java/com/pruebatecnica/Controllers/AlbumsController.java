package com.pruebatecnica.Controllers;

import com.pruebatecnica.DTO.Album.AlbumDTO;
import com.pruebatecnica.Services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AlbumsController {

    @Autowired
    private AlbumService albumService;

    /**
     * Obtiene todos los albums
     * @return DTO de los albums
     */
    @GetMapping("/albums")
    public ResponseEntity<AlbumDTO[]> getAlbums() {
        return ResponseEntity.ok(albumService.getAlbums()); // Placeholder for actual implementation
    }

}
