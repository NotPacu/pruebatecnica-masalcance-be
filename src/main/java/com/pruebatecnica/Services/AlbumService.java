package com.pruebatecnica.Services;

import com.pruebatecnica.Configuration.Enviroments;
import com.pruebatecnica.DTO.Album.AlbumDTO;
import com.pruebatecnica.DTO.Posts.PostsDTO;
import com.pruebatecnica.Enums.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Servicio para manejar operaciones relacionadas con álbumes.
 * Proporciona métodos para obtener todos los albumes y los álbumes de un usuario específico.
 * Incluye manejo de errores y registro de logs para las operaciones realizadas.
 */
@Service
public class AlbumService {

    @Autowired
    private Enviroments env;

    @Autowired
    private LogHandler logHandler;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Obtiene todos los álbumes.
     * @return Array de DTO de álbumes
     */
    public AlbumDTO[] getAlbums() {
        String url = env.getBaseUrl() + "/albums";

        try{
            // Realiza una solicitud GET a la URL especificada para obtener todos los albumes
            ResponseEntity<AlbumDTO[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    AlbumDTO[].class
            );

            HttpStatus status = response.getStatusCode();

            String bodyString = (response.getBody() != null) ? Arrays.toString(response.getBody()) : "null";
            logHandler.saveLog(url, LogType.GET_ALBUMS, String.valueOf(status.value()), bodyString);

            return response.getBody();

        } catch (HttpStatusCodeException ex) {
            // Captura errores con código de estado HTTP (4xx/5xx)
            String responseBody = ex.getResponseBodyAsString();
            int statusCode = ex.getRawStatusCode();

            logHandler.saveLog(url, LogType.GET_ALBUMS, String.valueOf(statusCode), responseBody);
            return new AlbumDTO[0];
        } catch (RestClientException ex) {
            // Captura otros errores de red,
            logHandler.saveLog(url, LogType.GET_ALBUMS, "ERROR", ex.getMessage());
            return new AlbumDTO[0];
        }
    }

    /**
     * Obtiene los álbumes de un usuario específico por su ID.
     * @param userId ID del usuario
     * @return Array de DTO de álbumes del usuario
     */
    public AlbumDTO[] getAlbumByUserId(String userId) {
        String url = env.getBaseUrl() + "/users/" + userId + "/albums";

        try {
            // Realiza una solicitud GET a la URL especificada para obtener los albumes de un usuario específico
            ResponseEntity<AlbumDTO[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    AlbumDTO[].class
            );

            HttpStatus status = response.getStatusCode();
            String bodyString = (response.getBody() != null) ? Arrays.toString(response.getBody()) : "null";

            logHandler.saveLog(url, LogType.GET_ALBUMS_BY_USER_ID, String.valueOf(status.value()), bodyString);
            return response.getBody();

        } catch (HttpStatusCodeException ex) {
            // Captura errores con código de estado HTTP (4xx/5xx)
            String responseBody = ex.getResponseBodyAsString();
            int statusCode = ex.getRawStatusCode();

            logHandler.saveLog(url, LogType.GET_ALBUMS_BY_USER_ID, String.valueOf(statusCode), responseBody);
            return new AlbumDTO[0];
        } catch (RestClientException ex) {
            // Captura otros errores de red,
            logHandler.saveLog(url, LogType.GET_ALBUMS_BY_USER_ID, "ERROR", ex.getMessage());
            return new AlbumDTO[0];
        }
    }
}
