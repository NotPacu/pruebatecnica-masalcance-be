package com.pruebatecnica.DTO.Logs;

import com.pruebatecnica.Entities.QueryLogsEntity;
import lombok.Data;

@Data
public class QueryLogDTO {
    String id;
    String query_type;
    String route;
    String status_code;
    String response;
    String query_date;

    public void fromEntityToDTO(QueryLogsEntity entity){
        id = entity.getQueryLogsId().toString();
        query_type = entity.getQueryType();
        route = entity.getRoute();
        status_code = entity.getStatusCode();
        response = entity.getResponse();
        query_date = entity.getQueryDate().toString();
    }
}
