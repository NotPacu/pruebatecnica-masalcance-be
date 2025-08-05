package com.pruebatecnica.Services;

import com.pruebatecnica.Configuration.Enviroments;
import com.pruebatecnica.DTO.Album.AlbumDTO;
import com.pruebatecnica.DTO.Posts.PostsDTO;
import com.pruebatecnica.DTO.Users.UsersDTO;
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
import java.util.Objects;

/**
 * Servicio para manejar operaciones relacionadas con usuarios.
 * Proporciona metodos para obtener todos los usuarios y los posts de un usuario específico.
 * Incluye manejo de errores y registro de logs para las operaciones realizadas.
 */
@Service
public class UsersService {


    @Autowired
    private Enviroments env;

    @Autowired
    private LogHandler logHandler;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Obtiene todos los posts de un Usuario.
     * @param id ID del usuario
     * @return DTO de los álbumes del usuario
     */
    public PostsDTO[] getUsersPosts(String id) {

        String url = env.getBaseUrl() + "/users/" + id + "/posts";
        try{
            ResponseEntity<PostsDTO[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    PostsDTO[].class
            );

            HttpStatus status = response.getStatusCode();

            String bodyString = (response.getBody() != null) ? Arrays.toString(response.getBody()) : "null";
            logHandler.saveLog(url, LogType.GET_POSTS_BY_USER_ID, String.valueOf(status.value()), bodyString);

            return response.getBody();

        } catch (HttpStatusCodeException ex) {
            // Captura errores con código de estado HTTP (4xx/5xx)
            String responseBody = ex.getResponseBodyAsString();
            int statusCode = ex.getRawStatusCode();

            logHandler.saveLog(url, LogType.GET_POSTS_BY_USER_ID, String.valueOf(statusCode), responseBody);
            return new PostsDTO[0];
        } catch (RestClientException ex) {
            // Captura otros errores de red,
            logHandler.saveLog(url, LogType.GET_POSTS_BY_USER_ID, "ERROR", ex.getMessage());
            return new PostsDTO[0];
        }
    }

    /**
     * Obtiene todos los usuarios.
     * @return DTO de los usuarios
     */
    public UsersDTO[] getAllUsers() {
        String url = env.getBaseUrl() + "/users";
        try{

            ResponseEntity<UsersDTO[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    UsersDTO[].class
            );

            HttpStatus status = response.getStatusCode();

            String bodyString = (response.getBody() != null) ? Arrays.toString(response.getBody()) : "null";
            logHandler.saveLog(url, LogType.GET_USERS, String.valueOf(status.value()), bodyString);

            return response.getBody();
        } catch (
        HttpStatusCodeException ex) {
            // Captura errores con código de estado HTTP (4xx/5xx)
            String responseBody = ex.getResponseBodyAsString();
            int statusCode = ex.getRawStatusCode();

            logHandler.saveLog(url, LogType.GET_USERS, String.valueOf(statusCode), responseBody);
            return new UsersDTO[0];
        } catch (
        RestClientException ex) {
            // Captura otros errores de red,
            logHandler.saveLog(url, LogType.GET_USERS, "ERROR", ex.getMessage());
            return new UsersDTO[0];
        }
    }
}
