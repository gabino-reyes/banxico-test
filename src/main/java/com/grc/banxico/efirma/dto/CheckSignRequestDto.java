package com.grc.banxico.efirma.dto;

/**
 * Objeto de transferencia de entrada de datos para la validación de un archivo pem - cer
 * @author Gabino Reyes
 * @version 1.0
 */

public class CheckSignRequestDto {

    private String text;
    private String cn;
    private String serie;
    private String sign;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "CheckSignDto{" +
                "text='" + text + '\'' +
                ", cn='" + cn + '\'' +
                ", serie='" + serie + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
