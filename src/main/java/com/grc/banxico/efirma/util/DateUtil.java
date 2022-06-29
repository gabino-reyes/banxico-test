package com.grc.banxico.efirma.util;

/**
 * Clase que define métodos estáticos para el manejo de fechas, para reutilizar en la aplicación en la verificación de la firma
 * @author Gabino Reyes
 * @version 1.0
 * @since   2022-06-24
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class DateUtil {

    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
    public static String getDateValidate() {
        return new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date());
    }

    // TODO Renombrar o generar clase Util general
    public static Properties loadProperties(String fileProperties){
        Properties properties = null;
        try {
            InputStream input = new FileInputStream(fileProperties);
            properties = new Properties();
            properties.load(input);
        } catch (IOException ex) {
            log.error("No se puede encontrar el archivo de configuraciones.");
            ex.printStackTrace();
        }
        return properties;
    }
}
