CREATE DATABASE PruebaTecnica
go
Use PruebaTecnica
-- DROP TABLE QUERY_LOGS
-- Tabla para alcenar logs

CREATE TABLE QUERY_LOGS(
	query_logs_id int identity(1,1) primary key,
	query_type varchar(255) not null,
	route varchar(255) not null,
	status_code varchar(10) not null,
	response text,
	query_date datetime,
	active bit not null,
)