package com.pruebatecnica.Controllers;

import com.pruebatecnica.DTO.Logs.QueryLogDTO;
import com.pruebatecnica.Services.LogHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api")
public class LogsController {

    @Autowired
    private LogHandler logHandler;

    @GetMapping("/logs")
    public ResponseEntity<QueryLogDTO[]> getLogs(){
        return ResponseEntity.ok(logHandler.getAllLogs());
    }

    @PutMapping("/logs/{id}")
    public ResponseEntity<QueryLogDTO> clearLogs(@PathVariable String id , @RequestBody QueryLogDTO queryLogDTO){

        try{
            return ResponseEntity.ok(logHandler.updateLog(id, queryLogDTO));
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina un log por su ID.
     * @param id ID del log a eliminar
     * @return ResponseEntity con estado NO_CONTENT 204 si la eliminación fue exitosa
     */
    @DeleteMapping("/logs/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable String id) {
        logHandler.deleteLog(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
