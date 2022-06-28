package com.grc.banxico.efirma.controller;

/**
 * Controlador que expone los diferentes endpoints del servicio web para la verificación
 * del archivo CER - PEM
 * @author Gabino Reyes
 * @version 1.0
 * @since   2022-06-24
 */

import com.grc.banxico.efirma.dto.CheckSignRequestDto;
import com.grc.banxico.efirma.dto.CheckSignResponseDto;
import com.grc.banxico.efirma.service.ICheckSignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkSign")
public class EfirmaController {
    private static final Logger log = LoggerFactory.getLogger(EfirmaController.class);
    private final ICheckSignService _checkSignService;

    public EfirmaController(ICheckSignService iCheckSignService){
        this._checkSignService = iCheckSignService;
    }

    @GetMapping()
    public String get (){
        return "get works";
    }

    @PostMapping(path = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public CheckSignResponseDto validate(@RequestBody CheckSignRequestDto checkSignRequestDto)
    {
        log.info(checkSignRequestDto.toString());
        return _checkSignService.validateSign(checkSignRequestDto);
    }
}
