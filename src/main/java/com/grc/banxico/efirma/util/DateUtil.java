package com.grc.banxico.efirma.util;

/**
 * Clase que define métodos estáticos para el manejo de fechas, para reutilizar en la aplicación en la verificación de la firma
 * @author Gabino Reyes
 * @version 1.0
 * @since   2022-06-24
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getDateValidate() {
        return new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date());
    }
}
