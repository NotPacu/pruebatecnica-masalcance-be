package com.pruebatecnica.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;


/**
 * Clase para configurar variables de entorno.
 */
@Configuration
@ConfigurationProperties(prefix = "remote.api")
@Data
public class Enviroments {

    /**
     * URL base de la API remota.
     */
    private String baseUrl;

}
