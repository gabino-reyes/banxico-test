package com.grc.banxico.efirma.repository.model;

/**
 * Clase que representa la abstracción de la tabla certificado_operador en B.D.
 * @author Gabino Reyes
 * @version 1.0
 * @since   2022-06-24
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "certificado_operador")
public class CertificadoOperador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "numero_serie")
    private String numeroSerie;

    @Column(name = "nombre_comun")
    private String nombreComun;

    @Column(name = "certificado_pem")
    private String certificadoPem;

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getCertificadoPem() {
        return certificadoPem;
    }

    public void setCertificadoPem(String certificadoPem) {
        this.certificadoPem = certificadoPem;
    }

    @Override
    public String toString() {
        return "CertificadoOperador{" +
                "numeroSerie='" + numeroSerie + '\'' +
                ", nombreComun='" + nombreComun + '\'' +
                ", certificadoPem='" + certificadoPem + '\'' +
                '}';
    }
}
