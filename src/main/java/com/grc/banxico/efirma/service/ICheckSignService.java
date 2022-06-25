package com.grc.banxico.efirma.service;

import com.grc.banxico.efirma.dto.CheckSignRequestDto;
import com.grc.banxico.efirma.dto.CheckSignResponseDto;

public interface ICheckSignService {
    CheckSignResponseDto validateSign(CheckSignRequestDto checkSignRequestDto);
}
