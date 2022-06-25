package com.grc.banxico.efirma.service.impl;

import com.grc.banxico.efirma.dto.CheckSignRequestDto;
import com.grc.banxico.efirma.dto.CheckSignResponseDto;
import com.grc.banxico.efirma.repository.CertificadoOperadorRepository;
import com.grc.banxico.efirma.repository.model.CertificadoOperador;
import com.grc.banxico.efirma.service.ICheckSignService;
import com.grc.banxico.efirma.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckSignServiceImpl implements ICheckSignService {

    private static final Logger log = LoggerFactory.getLogger(CheckSignServiceImpl.class);
    private final CertificadoOperadorRepository _certificadoOperadorRepository;

    public CheckSignServiceImpl(CertificadoOperadorRepository certificadoOperadorRepository){
        this._certificadoOperadorRepository = certificadoOperadorRepository;
    }
    @Override
    public CheckSignResponseDto validateSign(CheckSignRequestDto checkSignRequestDto) {
        CheckSignResponseDto signResponse = new CheckSignResponseDto();
        signResponse.setResult("0|Ok");
        signResponse.setDateTimeValidate(DateUtil.getDateValidate());
        signResponse.setSign("8994839ijfisdfk");

        List<CertificadoOperador> demo = _certificadoOperadorRepository.findAll();
        for (CertificadoOperador item : demo) {
            log.info(item.toString());
        }
        return signResponse;
    }
}
