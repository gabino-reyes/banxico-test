package com.grc.banxico.efirma.service.impl;

/**
 * Clase de servicio que implementa los métodos necesarios para realizar la
 * verificación de la efirma
 * del archivo CER - PEM
 * @author Gabino Reyes
 * @version 1.0
 * @since   2022-06-24
 */

import com.grc.banxico.efirma.dto.CheckSignRequestDto;
import com.grc.banxico.efirma.dto.CheckSignResponseDto;
import com.grc.banxico.efirma.repository.CertificadoOperadorRepository;
import com.grc.banxico.efirma.repository.model.CertificadoOperador;
import com.grc.banxico.efirma.service.ICheckSignService;
import com.grc.banxico.efirma.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Optional;

@Service
public class CheckSignServiceImpl implements ICheckSignService {

    private static final Logger log = LoggerFactory.getLogger(CheckSignServiceImpl.class);
    private final CertificadoOperadorRepository _certificadoOperadorRepository;

    public CheckSignServiceImpl(CertificadoOperadorRepository certificadoOperadorRepository){
        this._certificadoOperadorRepository = certificadoOperadorRepository;
    }

    @Override
    public CertificadoOperador getCertificado(CheckSignRequestDto checkSignRequestDto) {
        Optional<CertificadoOperador> certificadoOperadorOptional = _certificadoOperadorRepository.findCertificadoBySerialAndNombreComun(
                checkSignRequestDto.getSerie(), checkSignRequestDto.getCn()
        );
        //CertificateFactory cf = CertificateFactory.getInstance("X.509");
        //Certificate cert = cf.generateCertificate(certificadoOperadorOptional.get().getCertificadoPem());
        return certificadoOperadorOptional.orElse(null);
    }

    @Override
    public CheckSignResponseDto validateSign(CheckSignRequestDto checkSignRequestDto) {
        CheckSignResponseDto signResponse = new CheckSignResponseDto();
        if(!validateDataInput(checkSignRequestDto)){
            signResponse.setResult("400|Parámetros insuficientes ó incorrectos");
            signResponse.setDateTimeValidate(DateUtil.getDateValidate());
            signResponse.setSign("");
            return signResponse;
        }

        signResponse.setResult("0|Ok");
        signResponse.setDateTimeValidate(DateUtil.getDateValidate());
        signResponse.setSign("8994839ijfisdfk");


        //log.info(""+certificadoOperadorOptional.isPresent());
        //log.info(certificadoOperadorOptional.toString());
        /*if(!certificadoOperadorOptional.isPresent()){
            signResponse.setResult("404| No se encontró información");
            signResponse.setSign("");
        }
        /*List<CertificadoOperador> demo = _certificadoOperadorRepository.findAll();
        for (CertificadoOperador item : demo) {
            log.info(item.toString());
        }*/
        return signResponse;
    }

    private boolean validateDataInput(CheckSignRequestDto input){
        return input != null && !input.getCn().isEmpty() && !input.getSerie().isEmpty() && !input.getText().isEmpty() && !input.getSign().isEmpty();
    }
}
