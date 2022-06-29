package com.grc.banxico.efirma.service;

/**
 * Interfaz de servicio que define los métodos necesarios para realizar la verificación de la firma
 * @author Gabino Reyes
 * @version 1.0
 * @since   2022-06-24
 */

import com.grc.banxico.efirma.dto.CheckSignRequestDto;
import com.grc.banxico.efirma.dto.CheckSignResponseDto;
import com.grc.banxico.efirma.repository.model.CertificadoOperador;

public interface ICheckSignService {

    CertificadoOperador getCertificado(CheckSignRequestDto checkSignRequestDto);
    CheckSignResponseDto validateSign(CheckSignRequestDto checkSignRequestDto);
}
