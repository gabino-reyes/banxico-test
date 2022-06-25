package com.grc.banxico.efirma.repository;

import com.grc.banxico.efirma.repository.model.CertificadoOperador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificadoOperadorRepository extends JpaRepository<CertificadoOperador, Long> {

}
