package com.pruebatecnica.Services;

import com.pruebatecnica.Configuration.Enviroments;
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
 * Servicio para manejar operaciones relacionadas con posts.
 * Proporciona metodos para obtener todos los posts.
 * Incluye manejo de errores y registro de logs para las operaciones realizadas.
 */
@Service
public class PostService {


    @Autowired
    private Enviroments env;

    @Autowired
    private LogHandler logHandler;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Obtiene todos los posts.
     * @return Array de DTO de posts
     */
    public PostsDTO[] getPosts() {
        String url = env.getBaseUrl() + "/posts";
        try {
            // Realiza una solicitud GET a la URL especificada para obtener todos los posts
            ResponseEntity<PostsDTO[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    PostsDTO[].class
            );

            HttpStatus status = response.getStatusCode();

            String bodyString = (response.getBody() != null) ? Arrays.toString(response.getBody()) : "null";
            logHandler.saveLog(url, LogType.GET_POSTS, String.valueOf(status.value()), bodyString);

            return response.getBody();
        }
        catch (HttpStatusCodeException ex) {
            // Captura errores con código de estado HTTP (4xx/5xx)
            String responseBody = ex.getResponseBodyAsString();
            int statusCode = ex.getRawStatusCode();

            logHandler.saveLog(url, LogType.GET_POSTS, String.valueOf(statusCode), responseBody);
            return new PostsDTO[0];
        } catch (RestClientException ex) {
            // Captura otros errores de red,
            logHandler.saveLog(url, LogType.GET_POSTS, "ERROR", ex.getMessage());
            return new PostsDTO[0];
        }
    }
}
