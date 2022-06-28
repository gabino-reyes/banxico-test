package com.grc.banxico.efirma.service;

import com.grc.banxico.efirma.dto.CheckSignRequestDto;
import com.grc.banxico.efirma.dto.CheckSignResponseDto;
import com.grc.banxico.efirma.repository.model.CertificadoOperador;

public interface ICheckSignService {

    CertificadoOperador getCertificado(CheckSignRequestDto checkSignRequestDto);
    CheckSignResponseDto validateSign(CheckSignRequestDto checkSignRequestDto);
}
