package com.grc.banxico.efirma.service.impl;

/**
 * Clase de servicio que contiene e implementa los métodos que contienen la lógica de negocio para la verificación de la firma
 * @author Gabino Reyes
 * @version 1.0
 * @since   2022-06-24
 */

import com.grc.banxico.efirma.dto.CheckSignRequestDto;
import com.grc.banxico.efirma.dto.CheckSignResponseDto;
import com.grc.banxico.efirma.repository.CertificadoOperadorRepository;
import com.grc.banxico.efirma.repository.model.CertificadoOperador;
import com.grc.banxico.efirma.service.ICheckSignService;
import com.grc.banxico.efirma.service.ISignatureBouncyService;
import com.grc.banxico.efirma.util.DateUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Optional;

@Service
public class CheckSignServiceImpl implements ICheckSignService {

    private static final Logger log = LoggerFactory.getLogger(CheckSignServiceImpl.class);
    private final CertificadoOperadorRepository _certificadoOperadorRepository;
    private final ISignatureBouncyService _bouncyService;

    public CheckSignServiceImpl(CertificadoOperadorRepository certificadoOperadorRepository, ISignatureBouncyService bouncyService){
        this._certificadoOperadorRepository = certificadoOperadorRepository;
        this._bouncyService = bouncyService;
    }

    @Override
    public CertificadoOperador getCertificado(CheckSignRequestDto checkSignRequestDto) {
        Optional<CertificadoOperador> certificadoOperadorOptional = _certificadoOperadorRepository.findCertificadoBySerialAndNombreComun(
                checkSignRequestDto.getSerie(), checkSignRequestDto.getCn()
        );
        return certificadoOperadorOptional.orElse(null);
    }

    @Override
    public CheckSignResponseDto validateSign(CheckSignRequestDto checkSignRequestDto) {
        CheckSignResponseDto signResponse = new CheckSignResponseDto();
        X509Certificate certificate;
        boolean isVerifySign;
        signResponse.setDateTimeValidate(DateUtil.getDateValidate());

        if(!validateDataInput(checkSignRequestDto)){
            signResponse.setResult("400|Parámetros insuficientes ó incorrectos");
            return signResponse;
        }

        CertificadoOperador certificadoOperador = this.getCertificado(checkSignRequestDto);
        //log.info(certificadoOperador.toString());
        if(certificadoOperador == null){
            signResponse.setResult("404|No se encontró certificado");
            return signResponse;
        }

        try {
            certificate = _bouncyService.loadCertificate(certificadoOperador.getCertificadoPem());
        } catch (CertificateException e) {
            signResponse.setResult("500|Error al obtener el certificado");
            return signResponse;
        } catch (NoSuchProviderException e) {
            signResponse.setResult("500|Error al obtener el provider para el certificado");
            return signResponse;
        }

        String originalMessage = checkSignRequestDto.getText() + checkSignRequestDto.getCn() + checkSignRequestDto.getSerie();

        try {
             isVerifySign = _bouncyService.verifySignRSAPKCS1(certificate.getPublicKey(), originalMessage, checkSignRequestDto.getSign());
        } catch (Exception  e) {
            signResponse.setResult("500|Error al verificar la firma");
            log.error("validateSign: " + e.getMessage());
            e.printStackTrace();
            return signResponse;
        }

        if(isVerifySign){
            signResponse.setResult("0|Ok");
        }else{
            signResponse.setResult("608|Firma inválida");
        }
        return signResponse;
    }

    @Override
    public CheckSignResponseDto signResult(CheckSignRequestDto checkSignRequestDto, CheckSignResponseDto checkSignResponseDto) {
        String pathPrivateKey = "src/main/resources/data/usuarios/" + checkSignRequestDto.getCn().replaceAll("\\s", "") + "/" + checkSignRequestDto.getCn().replaceAll("\\s", "") + ".cve";
        try {
            File phraseFile = new File("src/main/resources/data/usuarios/" + checkSignRequestDto.getCn().replaceAll("\\s", "") + "/frase.txt");
            String passphrase = new String(Files.readAllBytes(phraseFile.toPath()), Charset.defaultCharset());
            PrivateKey privateKey = _bouncyService.getPrivateKey(new File(pathPrivateKey), passphrase);
            byte[] sign = _bouncyService.signRSAPKCS1(privateKey,checkSignResponseDto.getResult() + "|" + checkSignResponseDto.getDateTimeValidate());
            checkSignResponseDto.setSign(Base64.encodeBase64String(sign));
        } catch (Exception e) {
            checkSignResponseDto.setSign(null);
            log.error("Error al firmar el resultado error: " + e.getMessage());
            e.printStackTrace();
        }
        log.info(checkSignResponseDto.toString());
        return checkSignResponseDto;
    }

    private boolean validateDataInput(CheckSignRequestDto input){
        return input != null && !input.getCn().isEmpty() && !input.getSerie().isEmpty() && !input.getText().isEmpty() && !input.getSign().isEmpty();
    }
}
