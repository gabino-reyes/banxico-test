package com.grc.banxico.efirma.dto;

/**
 * Objeto de transferencia de salida de datos para la validación de un archivo pem - cer
 * @author Gabino Reyes
 * @version 1.0
 */

public class CheckSignResponseDto {
    private String result;
    private String dateTimeValidate;
    private String sign;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDateTimeValidate() {
        return dateTimeValidate;
    }

    public void setDateTimeValidate(String dateTimeValidate) {
        this.dateTimeValidate = dateTimeValidate;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "CheckSignResponseDto{" +
                "result='" + result + '\'' +
                ", dateTime='" + dateTimeValidate + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
