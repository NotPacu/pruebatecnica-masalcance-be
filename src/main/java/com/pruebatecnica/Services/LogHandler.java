package com.pruebatecnica.Services;

import com.pruebatecnica.DTO.Logs.QueryLogDTO;
import com.pruebatecnica.Entities.QueryLogsEntity;
import com.pruebatecnica.Enums.LogType;
import com.pruebatecnica.Respositories.QueryLogRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class LogHandler {

    @Autowired
    private QueryLogRespository queryLogRespository;

    /**
     * Guarda un log en la base de datos.
     * @param url la ruta de la consulta
     * @param logType el tipo de log según Enum LogType
     * @param StatusCode el código de estado HTTP de la respuesta
     * @param response  el cuerpo de la respuesta de la consulta
     */
    public void saveLog(String url , LogType logType, String StatusCode, String response) {

        QueryLogsEntity logEntry = new QueryLogsEntity();
        logEntry.setQueryDate( new Date());
        logEntry.setRoute(url);
        logEntry.setQueryType(logType.toString());
        logEntry.setResponse(response);
        logEntry.setStatusCode(StatusCode);

        queryLogRespository.save(logEntry);
    }

    /**
     * Obtiene todos los logs activos.
     * @return Array de DTO de logs
     */
    public QueryLogDTO[] getAllLogs() {
        List<QueryLogsEntity> logs = queryLogRespository.findAllByActiveTrue();
        QueryLogDTO[] logDTOs = new QueryLogDTO[logs.size()];

        for (int i = 0; i < logs.size(); i++) {
            logDTOs[i] = new QueryLogDTO();
            logDTOs[i].fromEntityToDTO(logs.get(i));
        }

        return logDTOs;
    }

    /**
     * Actualiza un log existente por su ID.
     * @param id ID del log a actualizar
     * @param queryLogDTO DTO con los nuevos datos del log
     * @return QueryLogDTO actualizado
     * @throws ParseException si hay un error al parsear la fecha
     */
    public QueryLogDTO updateLog(String id , QueryLogDTO queryLogDTO) throws ParseException {
        QueryLogsEntity logEntry = queryLogRespository.findById(Long.parseLong(id)).orElse(null);

        if (logEntry != null) {
            logEntry.setQueryType(queryLogDTO.getQuery_type());
            logEntry.setRoute(queryLogDTO.getRoute());
            logEntry.setStatusCode(queryLogDTO.getStatus_code());
            logEntry.setResponse(queryLogDTO.getResponse());

            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date parsedDate = formato.parse(queryLogDTO.getQuery_date());
            logEntry.setQueryDate(parsedDate);

            queryLogRespository.save(logEntry);
            return queryLogDTO;
        }else{
            throw new RuntimeException("Log not found with id: " + id);
        }
    }

    /**
     * Elimina un log por su ID aunque
     * Marca el log como inactivo en lugar de eliminarlo
     * físicamente de la base de datos.
     * @param id ID del log a eliminar / desactivar
     */
    public void deleteLog(String id) {
        QueryLogsEntity logEntry = queryLogRespository.findById(Long.parseLong(id)).orElse(null);
        if (logEntry != null) {
            logEntry.setActive(false);
            queryLogRespository.save(logEntry);
        } else {
            throw new RuntimeException("Log not found with id: " + id);
        }
    }

}
