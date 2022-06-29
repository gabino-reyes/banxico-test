# Verificación de firma BANXICO-Evaluación

Aplicación para realizar la verificación de datos firmados mediante criptografía, para la integridad de datos.

## Descripción

Este proyecto se encarga de verificar la firma de un texto ingresado por el usuario, se realiza la firma en el front y en el
back se verifica y viceversa el resultado del back se firma y verifica en el front.

## Entorno de desarrollo
Este proyecto fue probado y desarrollado utilizando:

* JDK 1.8.0_172
* Maven 3.8.2
* PostgrestSQL 14.2
* docker 4.9.1
* git 2.36.0
* IntelliJ IDEA 2022.1.3 (Ultimate Edition)
* Postman 9.19.0
* Google Chrome 102.0.5005.115
* macOS Monterrey 12.4

## Pre-Requisitos
Para probar la aplicación se debe tener instalado

* JDK 1.8.0_172
* Maven 3.8.2
* PostgrestSQL 14.2
* Tener libre los puertos 8080, 80, 5432
* Tener configurada variables de entorno JAVA_HOME MVN_HOME
* Tener un explorador web que soporte HTML5, Javascript
* Tener conexión a internet
* La aplicación corre en localhost (consideración)

## ¿Cómo usarlo?

A continuación se describe una serie de pasos, para poder hacer uso de la aplicación.

Todos los recursos están disponibles dentro del directorio principal del proyecto, según se vaya requiriendo,
se especificara la ruta del recurso a usar.

### Base de datos

Se proporciona el script para instalar la base de datos de la aplicación.
El script se encuentra en la ruta ../efirma/src/main/resources/sql/banxico.sql, se debe ejecutar respetando íntegramente los nombres
de base, tabla, columnas, usuarios, contraseñas y datos de la tabla.

1. Se debera crear base de datos
2. Crear role
3. Conectarse con el rol creado
4. Crear tabla
5. Ingresar información a la tabla
6. Generar sentencia select para verificar el contenido de la tabla (opcional)
7. jdbc:postgresql://localhost:5432/efirma (consideración: url connection string)

### Despliegue Backend

EL backend es una serie de micro-servicios expuestos para la verificación de la firma.
Para ejecutarlo se deberá realizar lo siguiente, en una terminal de comandos abierta en la ruta ../efirma/pom.xml

1. Compilar y ejecutar el proyecto, no se debe cerrar la terminal ni matar el proceso que se quede activo, cuando se despliegan los servicios.
```shell
mvn clean install
java -jar target/medicamentos-doc-dev-1.1.7.jar --server.port=8080
```
2. Si el proyecto se despliega correctamente, se verá un mensaje (con la fecha y hora en la que se levante el servicio):
```shell
Inicio de validaciones EFirma Banxico = Tue Jun 28 20:54:23 CDT 2022
```
3. Los servicios estarán expuestos en la ruta: http://localhost:8080/banxico
   1. Solo existe un método expuesto: /api/checkSign/validate de tipo POST

### Despliegue Frontend

El frontend es la interfaz de usuario que permite proporcionar los datos necesarios para la verificación de la firma y mostrar el resultado

1. Abrir con un explorador web el archivo ../efirma/src/main/resources/templates/index.html
2. Eso es todo, con los servicios arriba ya se puede hacer uso de la página

## Solución de problemas
    TODO

## Limitaciones del sistema
    TODO

## Licencia
    TODO

## Equipo:

* Gabino Reyes

## Contacto

* email - greyesc.014@gmail.com


## Referencias

Se hace referencia de los links que sirvieron de inspiración de características o corrección de errores

* https://mkyong.com/spring-boot/spring-boot-spring-data-jpa-postgresql/
* https://www.baeldung.com/spring-data-jpa-query
* https://www.baeldung.com/java-bouncy-castle
* https://docs.hidglobal.com/auth-service/docs/buildingapps/javascript/read-different-certificate-key-file-formats-with-javascript.htm
* https://github.com/digitalbazaar/forge
* https://github.com/digitalbazaar/forge#rsa
* https://getbootstrap.com/docs/4.0/getting-started/introduction/
* https://www.npmjs.com/package/node-forge
* https://www.bouncycastle.org/documentation.html
* https://stackoverflow.com/questions/9739121/convert-a-pem-formatted-string-to-a-java-security-cert-x509certificate
* https://stackoverflow.com/questions/26888397/exception-in-thread-main-java-lang-verifyerror-class-org-bouncycastle-asn1-as/26996797#26996797
* https://www.bouncycastle.org/fips-java/BCFipsIn100.pdf
* https://www.tabnine.com/code/java/classes/org.bouncycastle.openssl.PEMEncryptedKeyPair

Por si se ocupa
* https://stackoverflow.com/questions/3820897/jpa-entity-without-id
* https://stackoverflow.com/questions/4386030/how-to-use-blob-datatype-in-postgres



