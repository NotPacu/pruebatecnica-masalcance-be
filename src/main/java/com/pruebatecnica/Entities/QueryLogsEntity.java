package com.pruebatecnica.Entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "QUERY_LOGS")
@Entity
public class QueryLogsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "query_logs_id")
    private Long queryLogsId;

    @Column(name = "query_type", nullable = false, length = 255)
    private String queryType;

    @Column(name = "route", nullable = false, length = 255)
    private String route;

    @Column(name = "response", columnDefinition = "TEXT")
    private String response;

    @Column(name = "status_code", nullable = false, length = 10)
    private String statusCode;

    @Column(name = "query_date")
    private Date queryDate;

    @Column(name = "active")
    private boolean active = true;
}
