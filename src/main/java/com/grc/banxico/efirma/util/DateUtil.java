package com.grc.banxico.efirma.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getDateValidate() {
        return new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date());
    }
}
