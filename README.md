Crear base de datos

CREATE ROLE banxico WITH PASSWORD 'banxico' LOGIN;
CREATE DATABASE efirma ENCODING 'UTF8' OWNER banxico;

-- jdbc:postgresql://localhost:5432/efirma

create table certificado_operador(
numero_serie varchar(100),
nombre_comun varchar(100),
certificado_pem varchar(100)
);

select *
from certificado_operador;